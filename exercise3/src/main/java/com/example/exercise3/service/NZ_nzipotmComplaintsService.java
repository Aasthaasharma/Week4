package com.example.exercise3.service;

import com.example.exercise3.model.*;
import com.example.exercise3.pages.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class NZ_nzipotmComplaintsService {
    private static final Logger logger = LoggerFactory.getLogger(NZ_nzipotmComplaintsService.class);

    public Binder scrapeData() throws InterruptedException, IOException {

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
        driver.manage().window().maximize();
        logger.info("Browser launched and maximized.");
        driver.get("https://app.iponz.govt.nz/app/Extra/Default.aspx?op=EXTRA_tm_qbe&fcoOp=EXTRA__Default&directAccess=true");
            logger.info("Navigated to IPONZ TM opposition search page.");

        StartPage startPage = new StartPage(driver, wait);
        startPage.clickSelectStatus();
            logger.info("Clicked select status.");

        StatusSelectionPage statusPage = new StatusSelectionPage(driver, wait);
        statusPage.selectUnderOppositionCheckboxes();
        statusPage.clickSearch();
            logger.info("Selected filters and submitted search.");

        SearchResultsPage resultsPage = new SearchResultsPage(driver, wait);
        resultsPage.sortByCaseNumberDescending();
        resultsPage.clickApplicationNumber();
            logger.info("Clicked on latest case number.");

        CaseDetailPage caseDetailPage = new CaseDetailPage(driver, wait);
        Thread.sleep(5000);
            logger.info("Fetching application details...");
        String appNumber = caseDetailPage.getApplicationNumber();
        String className = caseDetailPage.getClassName();

        // Scroll to the bottom of the page to ensure all elements are loaded
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo({top: document.body.scrollHeight, behavior: 'smooth'});");

        String markNature = caseDetailPage.getMarkNature();
        String markName = caseDetailPage.getMarkName();
        String markType = caseDetailPage.getMarkType();
        String markImage = caseDetailPage.getMarkImage();
        String redPartyName = caseDetailPage.getRedPartyOpponent();
        String partyType = caseDetailPage.getPartyType();

        DocumentPage documentPage = new DocumentPage(driver, wait);
        documentPage.openTab();
        String docUrl = documentPage.getFirstDocumentUrl();

        HistoryPage historyPage = new HistoryPage(driver, wait);
        historyPage.openHistoryTab();
        String firstAction = historyPage.getFirstActionType();
        String firstActionDate = historyPage.getFirstActionFirstDate();
        String formattedDate = formatDate(firstActionDate);

            return buildBinder(appNumber, className, markNature, markName, markType, markImage,
                    redPartyName, partyType, documentPage, docUrl, firstAction, firstActionDate, formattedDate);
    }
        catch (Exception e) {
            logger.error("Failed to process NZ IPONZ TM complaint extraction", e);
            throw new RuntimeException("Failed to process data", e);
        } finally {
            if (driver != null) {
                driver.quit();
                logger.info("Browser session ended.");
            }
        }
    }
    private String formatDate(String dateStr) throws ParseException {
        SimpleDateFormat input = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("yyyyMMdd");
        return output.format(input.parse(dateStr));
    }

    private Binder buildBinder(
            String appNumber, String className, String markNature, String markName, String markType, String markImage,
            String partyName, String partyType, DocumentPage documentPage, String docUrl,
            String firstAction, String firstActionDate, String formattedDate
    ) throws IOException {

        Binder binder = new Binder();
        binder.setId(appNumber);
        binder.setDomains(List.of("TM", "CR", "DM", "PT"));
        binder.setFirstAction(firstAction);
        binder.setFirstActionDate(firstActionDate);

        binder.setDockets(List.of(buildDocket(appNumber, formattedDate)));
        binder.setDecisions(List.of(buildDecision(appNumber, formattedDate, firstActionDate, documentPage, docUrl)));
        binder.setParties(List.of(buildParty(partyName, partyType)));
        binder.setRights(List.of(buildRight(appNumber, className, markNature, markName, markType, markImage)));

        logger.info("Binder built for application number: {}", appNumber);
        return binder;
    }

    private Docket buildDocket(String appNumber, String formattedDate) {
        Docket docket = new Docket();
        docket.setId("docket-" + appNumber);
        docket.setReference("nz-nzipotm-op-" + appNumber + "_" + formattedDate);
        docket.setCourtId("N/A");
        docket.setJudge("N/A");
        return docket;
    }

    private Decision buildDecision(
            String appNumber, String formattedDate, String judgmentDate,
            DocumentPage documentPage, String docUrl
    ) throws IOException {
        String reference = "nz-nzipotm-op-" + appNumber + "_" + formattedDate + "_Complaint_IS";
        Decision decision = new Decision();
        decision.setId("decision-" + appNumber);
        decision.setReference(reference);
        decision.setJudgmentDate(judgmentDate);
        decision.setLevel("First Instance");
        decision.setNature("Complaints & Hearings");
        decision.setRobotSource("N/A");

        String pdfPath = documentPage.downloadPdf(docUrl, reference);
        documentPage.savePdfAsJs(pdfPath, reference);
        documentPage.saveDecisionAsJson(decision, reference);

        return decision;
    }

    private Party buildParty(String name, String type) {
        Party party = new Party();
        party.setName(name);
        party.setType(type);
        party.setRepresentatives(null);
        return party;
    }

    private Right buildRight(String appNumber, String className, String nature, String name, String type, String image) {
        Classification classification = new Classification();
        classification.setName(name);
        classification.setType(type);
        classification.setClassName(className);
        classification.setImage(image);

        Right right = new Right();
        right.setId("right-" + appNumber);
        right.setOpponent(true);
        right.setClassification(classification);
        right.setName(nature);
        right.setType("TM");
        right.setReference(appNumber);
        return right;
    }

}