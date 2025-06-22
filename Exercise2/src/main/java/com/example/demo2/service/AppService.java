package com.example.demo2.service;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
@Service
public class AppService {

    private static final Logger logger = LoggerFactory.getLogger(AppService.class);
    public String runCode() {
        WebDriver driver = null;
        try {
            logger.info("Initializing ChromeDriver...");
            driver = new ChromeDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            driver.manage().window().maximize();

            logger.info("Navigating to LeetCode...");
            driver.get("http://www.leetcode.com/");

            // Scroll to bottom
            logger.info("Scrolling to bottom of page...");
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

            // Switch to iframe
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[contains(@src, '/playground')]")));

            // Click Java button
            logger.info("Selecting Java language...");
            WebElement javaButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"app\"]//button[normalize-space(text())='Java']")));
            javaButton.click();


            String code = "public class Hello {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        System.out.println(\"Hello World!\");";


            enterCode(wait, driver, code);

            // Click Run
            WebElement runButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'run-code-btn')]")));
            runButton.click();

            // Wait for output
            WebElement output = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='output']")));
           String result= output.getText().trim();
            logger.info("Code output retrieved: {}", result);
           return result;

        } catch (TimeoutException e)
        {
            logger.error("Timeout while locating an element: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error execution: {}", e.getMessage(), e);
        } finally {
            if (driver != null) {
                driver.quit();
                logger.info("Closed ChromeDriver.");
            }
        }
        return "Failed to retrieve output";
    }


    private void enterCode(WebDriverWait wait, WebDriver driver, String code) {
        try {
            WebElement editor = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class, 'ReactCodeMirror')]")));
            Actions act=new Actions(driver);

                 act.moveToElement(editor)
                    .click()
                    .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                    .sendKeys(Keys.BACK_SPACE)
                    .sendKeys(code)
                    .perform();
            logger.info("Code entered into editor.");
        } catch (Exception e) {
            logger.error("Error while entering code: {}", e.getMessage(), e);
            throw e;
        }
    }
}