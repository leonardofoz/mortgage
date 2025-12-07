package com.mortgage.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mortgage.model.InterestRate;

/**
 * In-memory implementation of InterestRateRepository.
 */
@Repository
public class InMemoryInterestRateRepository implements InterestRateRepository {
    
    private final Map<Integer, InterestRate> interestRates = new HashMap<>();
    
    public InMemoryInterestRateRepository() {
        initializeInterestRates();
    }
    
    /**
     * Initialize the interest rates.
     */
    private void initializeInterestRates() {
        LocalDateTime now = LocalDateTime.now();
        interestRates.put(5, new InterestRate(5, new BigDecimal("3.6"), now));
        interestRates.put(10, new InterestRate(10, new BigDecimal("3.8"), now));
        interestRates.put(15, new InterestRate(15, new BigDecimal("4.2"), now));
        interestRates.put(20, new InterestRate(20, new BigDecimal("4.3"), now));
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