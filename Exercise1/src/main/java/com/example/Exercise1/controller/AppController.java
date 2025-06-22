package com.example.Exercise1.controller;

import com.example.Exercise1.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class AppController {
    private final AppService appService;
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/home-page")
    public ResponseEntity<Map<String, Map<String, String>>> runSelenium() {
        try {
            logger.info("Received request to scrape home page.");
            Map<String, Map<String, String>> result = appService.scrapeData();

            if (result.isEmpty()) {
                logger.warn("Scraping returned empty result.");
                return ResponseEntity.noContent().build(); // 204 No Content
            }

            return ResponseEntity.ok(result); // 200 OK
        } catch (Exception e) {
            logger.error("Failed to scrape home page: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build(); // 500 Internal Server Error
        }
    }
}
