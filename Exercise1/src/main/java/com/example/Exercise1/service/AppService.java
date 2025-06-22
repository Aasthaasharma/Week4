package com.example.Exercise1.service;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class AppService {
    private static final Logger logger = LoggerFactory.getLogger(AppService.class);

    public Map<String, Map<String, String>> scrapeData() {
        WebDriver driver=null;
        Map<String, Map<String, String>> result = new LinkedHashMap<>();
        try {
            // Set up WebDriver
            driver = new ChromeDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            driver.get("https://clarivate.com/");
            driver.manage().window().maximize();

            // Close cookie consent banner
            closeCookieBanner(driver, wait);

            // Get nav bar links

            List<WebElement> links = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//*[@id='menu-main-menu']/li")));
            // Traverse through each link
            for (WebElement link : links) {
                try {
                    String linkText = link.getText().trim();
                    if (linkText.equals("Contact us") || linkText.equals("Insights")) {
                        continue;
                    }
                    logger.info("Processing link: {}", linkText);

                    // Click the link to load the content
                    link.click();
                    Map<String, String> innerMap = new LinkedHashMap<>();

                    List<WebElement> parentDiv = link.findElements(By.xpath(".//*[contains(@id, 'menu-item-')]/a[@role='tab']"));
                    for (WebElement parent : parentDiv) {
                        try {
                            String heading = "";
                            String content = "";
                            try {
                                heading = parent.findElement(By.xpath("./div/span[1]")).getText();
                                content = parent.findElement(By.xpath("./div/span[2]")).getText();

                            } catch (TimeoutException te) {
                                logger.warn("Timeout waiting for heading/content under link: {}", linkText);
                                continue;
                            }
                            if (!heading.isEmpty() && !content.isEmpty()) {
                                innerMap.put(heading, content);
                                logger.info("Added submenu: {} -> {}", heading, content);
                            }
                        } catch (StaleElementReferenceException | NoSuchElementException e) {
                            logger.warn("Skipped element due to exception: {}", e.getClass().getSimpleName());
                        }
                    }  if (!innerMap.isEmpty()) {
                        result.put(linkText, innerMap);
                        logger.info("Added data for link: {}", linkText);
                    }
                } catch (Exception e) {
                    logger.error("Error processing link: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
           logger.error("Unhandled error: {}" ,e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
                logger.info("WebDriver closed.");
            }
        }
        return result;
    }
    private void closeCookieBanner(WebDriver driver, WebDriverWait wait) {
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='onetrust-close-btn-container']/button")));
            cookieButton.click();
            logger.info("Cookie banner closed.");
        } catch (TimeoutException | NoSuchElementException ignored) {
            logger.info("No cookie banner present.");
        }
    }

}