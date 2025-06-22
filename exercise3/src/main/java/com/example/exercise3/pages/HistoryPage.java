package com.example.exercise3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class HistoryPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(HistoryPage.class);

    public HistoryPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openHistoryTab() {
        WebElement historyTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@id='MainContent_liHistories']//a[text()[normalize-space()='History']]\n")));
        logger.info("Clicking History tab");
        historyTab.click();
    }

    public String getFirstActionType() {
        // Example assumes label for first action type contains the text
        WebElement actionTypeElement = driver.findElement(By.xpath(
                "//*[@id=\"MainContent_ctrlHistoryList_gvHistory\"]/tbody/tr[2]/td[1]"
        ));
        return actionTypeElement.getText().trim();
    }

    public String getFirstActionFirstDate() {
        WebElement creationDateCell = driver.findElement(By.xpath(
                "//*[@id=\"MainContent_ctrlHistoryList_gvHistory\"]/tbody/tr[2]/td[3]"
        ));
        return creationDateCell.getText().trim();
    }
}
