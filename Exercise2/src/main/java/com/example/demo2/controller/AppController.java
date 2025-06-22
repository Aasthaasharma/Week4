package com.example.demo2.controller;

import com.example.demo2.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    private final AppService appService;
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/leetcode")
    public ResponseEntity<String> run() {
        logger.info("Received request to run Selenium LeetCode automation.");
        try {
            String output = appService.runCode();
            if (output.equalsIgnoreCase("Failed to retrieve output")) {
                logger.warn("Failed to produce output.");
                return ResponseEntity.status(500).body("Error: Could not retrieve output from LeetCode.");
            }
            logger.info("Output retrieved successfully.");
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            logger.error("Exception occurred while processing request: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Internal Server Error occurred.");
        }
    }

}
