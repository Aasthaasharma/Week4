package com.example.exercise3.controller;

import com.example.exercise3.model.Binder;
import com.example.exercise3.service.NZ_nzipotmComplaintsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NZ_nzipotmComplaintsController {
    private static final Logger logger = LoggerFactory.getLogger(NZ_nzipotmComplaintsController.class);
    private final NZ_nzipotmComplaintsService nzipotmService;

    public NZ_nzipotmComplaintsController(NZ_nzipotmComplaintsService nzipotmService) {
        this.nzipotmService = nzipotmService;
    }

    @GetMapping("/nzipotm-complaints")
    public ResponseEntity<?> run() {
        try {
            logger.info("GET method triggered.");
            Binder binder = nzipotmService.scrapeData();
            logger.info("Binder created successfully for application ID: {}", binder.getId());
            return ResponseEntity.ok(binder);
        } catch (Exception e) {
            logger.error("Failed to process NZ IPONZ TM complaint extraction", e);
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

}
