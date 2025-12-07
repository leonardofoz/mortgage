package com.mortgage.repository;

import java.util.List;
import java.util.Optional;

import com.mortgage.model.InterestRate;

/**
 * Repository interface for managing interest rates.
 */
public interface InterestRateRepository {
    
    /**
     * Find all interest rates.
     * 
     * @return list of all interest rates
     */
    List<InterestRate> findAll();
    
    /**
     * Find an interest rate by maturity period.
     * 
     * @param maturityPeriod the maturity period in years
     * @return optional interest rate
     */
    Optional<InterestRate> findByMaturityPeriod(Integer maturityPeriod);
    
    /**
     * Save an interest rate.
     * 
     * @param interestRate the interest rate to save
     * @return the saved interest rate
     */
    InterestRate save(InterestRate interestRate);
}