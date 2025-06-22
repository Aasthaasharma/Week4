package com.example.exercise3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchResultsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public SearchResultsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void sortByCaseNumberDescending()  {
        // Wait for the table to be present and clickable
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"MainContent_ctrlTMSearch_upPnlProcList\"]")));
                WebElement caseNumber= wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//table[@id='MainContent_ctrlTMSearch_ctrlProcList_gvwIPCases']//th[a[text()='Case Number']]/a")));
        caseNumber.click();
    }

    public void clickApplicationNumber() {
        WebElement appLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//tr[td[contains(text(),'Tims')]]//a[contains(@id,'lnkBtnCaseBrowser')]")));
        appLink.click();
    }

}
