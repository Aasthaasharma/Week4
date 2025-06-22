package com.example.exercise3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class StatusSelectionPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public StatusSelectionPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void selectUnderOppositionCheckboxes() {
        List<WebElement> checkboxes = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//tr[td/label[text()='Under Opposition']]/td/input[@type='checkbox']")
                )
        );
        for (WebElement checkbox : checkboxes) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
        WebElement select = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"MainContent_ctrlTMSearch_ctrlCaseStatusSearchDialog_lnkBtnSelect\"]")));
        select.click();
    }

    public void clickSearch() {
                WebElement search = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(@id,'ctrlTMSearch_lnkbtnSearch')]")));
        search.click();
    }
}
