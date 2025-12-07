package com.mortgage.service;

import com.mortgage.dto.MortgageCheckRequest;
import com.mortgage.dto.MortgageCheckResponse;

/**
 * Service interface for mortgage operations.
 */
public interface MortgageService {
    
    /**
     * Checks mortgage feasibility and calculates monthly costs.
     * 
     * @param request the mortgage check request containing income, maturity period, loan value, and home value
     * @return mortgage check response with feasibility and monthly costs
     */
    MortgageCheckResponse checkMortgage(MortgageCheckRequest request);
}