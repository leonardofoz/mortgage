package com.mortgage.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mortgage.dto.MortgageCheckRequest;
import com.mortgage.dto.MortgageCheckResponse;
import com.mortgage.model.InterestRate;

/**
 * Service to get interest rates and calculate mortgage monthly payments based
 * on business rules. It also checks if the mortgage is feasible.
 */
@Service
public class MortgageService {

    private static final BigDecimal MAX_LOAN_TO_INCOME = new BigDecimal("4.0");
    private static final int MONTHS_PER_YEAR = 12;

    private final Map<Integer, InterestRate> interestRates = new HashMap<>();

    public MortgageService() {
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

    /**
     * Get all available interest rates.
     *
     * @return a list of all interest rates
     */
    public List<InterestRate> getAllInterestRates() {
        return List.copyOf(interestRates.values());
    }

    /**
     * Checks mortgage feasibility and calculates monthly costs. Applies
     * business rules:
     *
     * @param request the mortgage check request containing income, maturity
     * period, loan value, and home value
     * @return mortgage check response with feasibility and monthly costs
     */
    public MortgageCheckResponse checkMortgage(MortgageCheckRequest request) {
        boolean feasible = request.getLoanValue().compareTo(request.getIncome().multiply(MAX_LOAN_TO_INCOME)) <= 0
                && request.getLoanValue().compareTo(request.getHomeValue()) <= 0;

        BigDecimal monthlyCosts = feasible ? calculateMonthlyPayment(request) : BigDecimal.ZERO;
        return new MortgageCheckResponse(feasible, monthlyCosts);
    }

    /**
     * Calculate the monthly payment using the standard mortgage formula.
     *
     * @param request the mortgage check request
     * @return the monthly payment amount
     */
    private BigDecimal calculateMonthlyPayment(MortgageCheckRequest request) {
        InterestRate rate = interestRates.get(request.getMaturityPeriod());
        if (rate == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal monthlyRate = rate.getInterestRate()
                .divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP)
                .divide(new BigDecimal(MONTHS_PER_YEAR), 10, RoundingMode.HALF_UP);

        int months = request.getMaturityPeriod() * MONTHS_PER_YEAR;
        BigDecimal compoundFactor = BigDecimal.ONE.add(monthlyRate);
        BigDecimal compoundFactorPower = compoundFactor.pow(months);

        return request.getLoanValue()
                .multiply(monthlyRate.multiply(compoundFactorPower))
                .divide(compoundFactorPower.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
    }
}