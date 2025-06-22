package com.example.exercise3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public StartPage(WebDriver driver, WebDriverWait wait) {
        this.wait=wait;
        this.driver = driver;
    }

    public void clickSelectStatus() {
                WebElement statusSearch = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(@id,'hdrClassifStatusCriteria_lblheader')]")));
        statusSearch.click();
        WebElement selectStatus = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(@id,'ctrlCaseStatusSearchDialog_lnkBtnSearch')]")));
        selectStatus.click();
    }
}
