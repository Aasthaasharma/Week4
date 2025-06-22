package com.example.exercise3.pages;

import java.io.*;
import java.net.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

public class DocumentPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(DocumentPage.class);

    public DocumentPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }
    public void openTab() {
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='#MainContent_tabDocuments' and contains(text(),'Documents')]")
        ));
        tab.click();
    }

    public String getFirstDocumentUrl() {
        // Wait for document table to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table[@id='MainContent_ctrlDocumentList_gvDocuments']")
        ));
        WebElement docLink=wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"MainContent_ctrlDocumentList_gvDocuments_hnkView_0\"]")
        ));
        return docLink.getDomProperty("href");
    }

    public String downloadPdf(String fileUrl, String decisionReference) throws IOException {
        Path targetPath = Paths.get("src/main/downloads/" + decisionReference + ".pdf");
        Files.createDirectories(targetPath.getParent());
        try (InputStream in = new URI(fileUrl).toURL().openStream()) {
            Files.copy(in, targetPath, StandardCopyOption.REPLACE_EXISTING);
            logger.info("PDF saved to: {}", targetPath);
            return targetPath.toString();
        } catch (URISyntaxException e) {
            logger.error("Invalid URL syntax: {}", fileUrl, e);
            throw new IOException("Invalid URL syntax: " + fileUrl, e);
        }
    }


    public void savePdfAsJs(String pdfFilePath, String decisionReference) throws IOException {
        // Read all bytes from the PDF file
        byte[] pdfBytes = Files.readAllBytes(Paths.get(pdfFilePath));
        // Encode PDF bytes to Base64 string
        String base64EncodedPdf = Base64.getEncoder().encodeToString(pdfBytes);
        // Create JS content that exports this Base64 string
        String jsContent = "const pdfBase64 = \"" + base64EncodedPdf + "\";\n\nexport default pdfBase64;";
        // Save JS file to the same directory or any desired path
        String jsFilePath = "src/main/downloads/" + decisionReference + ".js";
        Files.write(Paths.get(jsFilePath), jsContent.getBytes());
        logger.info("JS file with Base64 PDF saved at: {}", jsFilePath);
    }

    public void saveDecisionAsJson(Object decisionObject, String decisionReference) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // Define file path where JSON will be saved
        String jsonFilePath = "src/main/downloads/" + decisionReference + ".json";
        // Write the object as a JSON file
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonFilePath), decisionObject);
        logger.info("Decision JSON saved at: {}", jsonFilePath);
    }
}
