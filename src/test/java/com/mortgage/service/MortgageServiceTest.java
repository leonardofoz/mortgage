package com.mortgage.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mortgage.dto.MortgageCheckRequest;
import com.mortgage.dto.MortgageCheckResponse;
import com.mortgage.model.InterestRate;

@ExtendWith(MockitoExtension.class)
class MortgageServiceTest {
    
    @Mock
    private InterestRateService interestRateService;
    
    @InjectMocks
    private MortgageServiceImpl mortgageService;
    
    @BeforeEach
    void setUp() {
        InterestRate rate20 = new InterestRate(20, new BigDecimal("4.3"), LocalDateTime.now());
        when(interestRateService.getByMaturityPeriod(20)).thenReturn(Optional.of(rate20));
        when(interestRateService.getByMaturityPeriod(99)).thenReturn(Optional.empty());
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