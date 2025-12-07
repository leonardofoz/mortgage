package com.mortgage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mortgage.model.InterestRate;
import com.mortgage.service.InterestRateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller to get all available interest rates.
 */
@RestController
@RequestMapping("/api/interest-rates")
@Tag(name = "Interest Rates", description = "API for managing interest rates")
public class InterestRateController {
    
    private static final Logger log = LoggerFactory.getLogger(InterestRateController.class);
    private final InterestRateService interestRateService;
    
    public InterestRateController(InterestRateService interestRateService) {
        this.interestRateService = interestRateService;
    }
    
    /**
     * Get all available interest rates.
     * 
     * @return a list of all interest rates
     */
    @GetMapping
    @Operation(summary = "Get all interest rates", description = "Get a list of all current interest rates")
    public ResponseEntity<List<InterestRate>> getAllInterestRates() {
        log.info("Getting all interest rates");
        List<InterestRate> interestRates = interestRateService.getAllInterestRates();
        return ResponseEntity.ok(interestRates);
    }
}