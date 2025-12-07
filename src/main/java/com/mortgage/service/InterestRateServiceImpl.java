package com.mortgage.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mortgage.model.InterestRate;
import com.mortgage.repository.InterestRateRepository;

/**
 * Service implementation for interest rate operations.
 */
@Service
public class InterestRateServiceImpl implements InterestRateService {
    
    private static final Logger log = LoggerFactory.getLogger(InterestRateServiceImpl.class);
    private final InterestRateRepository interestRateRepository;
    
    public InterestRateServiceImpl(InterestRateRepository interestRateRepository) {
        this.interestRateRepository = interestRateRepository;
    }
    
    @Override
    public List<InterestRate> getAllInterestRates() {
        List<InterestRate> rates = interestRateRepository.findAll();
        log.debug("Retrieved {} interest rates", rates.size());
        return rates;
    }
    
    @Override
    public Optional<InterestRate> getByMaturityPeriod(Integer maturityPeriod) {
        Optional<InterestRate> rate = interestRateRepository.findByMaturityPeriod(maturityPeriod);
        if (rate.isEmpty()) {
            log.warn("Interest rate not found for maturity period: {}", maturityPeriod);
        }
        return rate;
    }
}