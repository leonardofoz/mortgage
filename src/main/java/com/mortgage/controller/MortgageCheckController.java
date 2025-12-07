package com.mortgage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mortgage.dto.MortgageCheckRequest;
import com.mortgage.dto.MortgageCheckResponse;
import com.mortgage.service.MortgageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controller to check if a mortgage is feasible.
 */
@RestController
@RequestMapping("/api/mortgage-check")
@Tag(name = "Mortgage Check", description = "API for mortgage feasibility checks")
public class MortgageCheckController {
    
    private static final Logger log = LoggerFactory.getLogger(MortgageCheckController.class);
    private final MortgageService mortgageService;
    
    public MortgageCheckController(MortgageService mortgageService) {
        this.mortgageService = mortgageService;
    }
    
    /**
     * Check if a mortgage is feasible.
     * 
     * @param request the mortgage check request
     * @return the mortgage check response
     */
    @PostMapping
    @Operation(summary = "Check mortgage feasibility", 
               description = "Calculate mortgage feasibility and monthly costs")
    public ResponseEntity<MortgageCheckResponse> checkMortgage(@Valid @RequestBody MortgageCheckRequest request) {
        log.info("Checking mortgage feasibility - Loan: {}, Income: {}, Period: {}", 
                request.getLoanValue(), request.getIncome(), request.getMaturityPeriod());
        MortgageCheckResponse response = mortgageService.checkMortgage(request);
        log.info("Mortgage check result - Feasible: {}, Monthly: {}", response.getFeasible(), response.getMonthlyCosts());
        return ResponseEntity.ok(response);
    }
}