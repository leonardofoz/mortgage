package com.mortgage.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mortgage.model.InterestRate;
import com.mortgage.repository.InterestRateRepository;

/**
 * Service implementation for interest rate operations.
 */
@Service
public class InterestRateServiceImpl implements InterestRateService {
    
    private final InterestRateRepository interestRateRepository;
    
    public InterestRateServiceImpl(InterestRateRepository interestRateRepository) {
        this.interestRateRepository = interestRateRepository;
    }
    
    @Override
    public List<InterestRate> getAllInterestRates() {
        return interestRateRepository.findAll();
    }
    
    @Override
    public Optional<InterestRate> getByMaturityPeriod(Integer maturityPeriod) {
        return interestRateRepository.findByMaturityPeriod(maturityPeriod);
    }
}