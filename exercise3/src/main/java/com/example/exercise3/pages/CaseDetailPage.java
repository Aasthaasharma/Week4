package com.example.exercise3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URI;

import java.util.Base64;

public class CaseDetailPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final
    Logger logger = LoggerFactory.getLogger(CaseDetailPage.class);


    public CaseDetailPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public String getApplicationNumber() {
        WebElement appNumberElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"MainContent_ctrlTM_txtAppNr\"]")));
        return appNumberElement.getText().trim();
    }

    public String getClassName() {
        WebElement classElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"MainContent_ctrlTM_ctrlClassif_gvClassifications\"]/tbody/tr[2]/td[1]")));
        return classElement.getText().trim();
    }

    public String getMarkName() {
            WebElement markNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//tr[@id='MainContent_ctrlTM_trTMName']/td[@class='data break-word']")));
            return markNameElement.getText().trim();
    }

    public String getMarkNature() {
            WebElement markNameNature = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//tr[@id='MainContent_ctrlTM_trTMNature']/td[@id='MainContent_ctrlTM_tdNatureData']")));
            return markNameNature.getText().trim();
    }

    public String getMarkType() {
            WebElement markTypeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//tr[@id='MainContent_ctrlTM_trTMType']/td[@class='data break-word']")));
            return markTypeElement.getText().trim();
    }

    public String getMarkImage() {
        WebElement markImageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//tr[@id='MainContent_ctrlTM_trPicture']//a[contains(@id,'hlnkCasePicture')]")));
        String imageUrl = markImageElement.getDomProperty("href");

        if (imageUrl == null) {
            logger.warn("Image URL is null, skipping image download");
            return null;
        }

        try (InputStream in = new URI(imageUrl).toURL().openStream()) {
            byte[] imageBytes = in.readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            logger.error("Error while downloading or encoding image", e);
            return null;
        }
    }

    public String getRedPartyOpponent() {
            WebElement redPartyElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                    "//table[@id='MainContent_ctrlProcedureList_gvwIPCases']//tr[@class='alt1']/td[count(//table[@id='MainContent_ctrlProcedureList_gvwIPCases']//th[normalize-space()='Owner']/preceding-sibling::th) + 1]"
            )));
            return redPartyElement.getText().trim();
        }

        public String getPartyType() {
            WebElement partyTypeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//tr[td[starts-with(normalize-space(),'Proceedings')]]/td[2]")));
            return partyTypeElement.getText().trim();
}
}
