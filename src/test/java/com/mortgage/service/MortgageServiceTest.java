package com.mortgage.service;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mortgage.dto.MortgageCheckRequest;
import com.mortgage.dto.MortgageCheckResponse;

class MortgageServiceTest {
    
    private MortgageService mortgageService;
    
    @BeforeEach
    void setUp() {
        mortgageService = new MortgageService();
    }
    
    @Test
    void checkMortgage_WhenFeasible_ShouldReturnTrueAndMonthlyCosts() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                20,
                new BigDecimal("200000"),
                new BigDecimal("300000")
        );
        
        MortgageCheckResponse response = mortgageService.checkMortgage(request);
        
        assertTrue(response.getFeasible());
        assertNotNull(response.getMonthlyCosts());
        assertTrue(response.getMonthlyCosts().compareTo(BigDecimal.ZERO) > 0);
    }
    
    @Test
    void checkMortgage_WhenLoanExceeds4TimesIncome_ShouldReturnFalse() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                20,
                new BigDecimal("250000"),
                new BigDecimal("300000")
        );
        
        MortgageCheckResponse response = mortgageService.checkMortgage(request);
        
        assertFalse(response.getFeasible());
        assertEquals(BigDecimal.ZERO, response.getMonthlyCosts());
    }
    
    @Test
    void checkMortgage_WhenLoanExceedsHomeValue_ShouldReturnFalse() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                20,
                new BigDecimal("200000"),
                new BigDecimal("150000")
        );
        
        MortgageCheckResponse response = mortgageService.checkMortgage(request);
        
        assertFalse(response.getFeasible());
        assertEquals(BigDecimal.ZERO, response.getMonthlyCosts());
    }
    
    @Test
    void checkMortgage_WhenInterestRateNotFound_ShouldReturnZeroMonthlyCosts() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                99,
                new BigDecimal("200000"),
                new BigDecimal("300000")
        );
        
        MortgageCheckResponse response = mortgageService.checkMortgage(request);
        
        assertTrue(response.getFeasible());
        assertEquals(BigDecimal.ZERO, response.getMonthlyCosts());
    }
}