package com.mortgage.service;

import java.util.List;
import java.util.Optional;

import com.mortgage.model.InterestRate;

/**
 * Service interface for interest rate operations.
 */
public interface InterestRateService {
    
    /**
     * Get all available interest rates.
     * 
     * @return a list of all interest rates
     */
    List<InterestRate> getAllInterestRates();
    
    /**
     * Get interest rate by maturity period.
     * 
     * @param maturityPeriod the maturity period in years
     * @return optional interest rate
     */
    Optional<InterestRate> getByMaturityPeriod(Integer maturityPeriod);
}