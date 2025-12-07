package com.mortgage.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mortgage.model.InterestRate;

/**
 * In-memory implementation of InterestRateRepository.
 */
@Repository
public class InMemoryInterestRateRepository implements InterestRateRepository {
    
    private static final Logger log = LoggerFactory.getLogger(InMemoryInterestRateRepository.class);
    private final Map<Integer, InterestRate> interestRates = new HashMap<>();
    
    public InMemoryInterestRateRepository() {
        initializeInterestRates();
    }
    
    /**
     * Initialize interest rates with ING mortgage rates for 100% LTV.
     * Rates are based on ING's current mortgage rates for 100% loan-to-value mortgages.
     * Energy label A.
     * Updated on 2025-12-07.
     * Source: https://www.ing.nl/en/personal/mortgage/current-mortgage-rates
     */
    private void initializeInterestRates() {
        LocalDateTime now = LocalDateTime.now();
        interestRates.put(1, new InterestRate(1, new BigDecimal("3.37"), now));
        interestRates.put(2, new InterestRate(2, new BigDecimal("3.38"), now));
        interestRates.put(3, new InterestRate(3, new BigDecimal("3.43"), now));
        interestRates.put(5, new InterestRate(5, new BigDecimal("3.47"), now));
        interestRates.put(6, new InterestRate(6, new BigDecimal("3.69"), now));
        interestRates.put(7, new InterestRate(7, new BigDecimal("3.70"), now));
        interestRates.put(10, new InterestRate(10, new BigDecimal("3.85"), now));
        interestRates.put(12, new InterestRate(12, new BigDecimal("4.19"), now));
        interestRates.put(15, new InterestRate(15, new BigDecimal("4.22"), now));
        interestRates.put(20, new InterestRate(20, new BigDecimal("4.34"), now));
        log.info("Initialized {} interest rates", interestRates.size());
    }
    
    @Override
    public List<InterestRate> findAll() {
        return List.copyOf(interestRates.values());
    }
    
    @Override
    public Optional<InterestRate> findByMaturityPeriod(Integer maturityPeriod) {
        return Optional.ofNullable(interestRates.get(maturityPeriod));
    }
    
    @Override
    public InterestRate save(InterestRate interestRate) {
        interestRates.put(interestRate.getMaturityPeriod(), interestRate);
        return interestRate;
    }
}