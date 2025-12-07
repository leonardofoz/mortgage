package com.mortgage.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.mortgage.dto.MortgageCheckRequest;
import com.mortgage.dto.MortgageCheckResponse;
import com.mortgage.model.InterestRate;

/**
 * Service implementation for mortgage operations.
 */
@Service
public class MortgageServiceImpl implements MortgageService {

    private static final BigDecimal MAX_LOAN_TO_INCOME = new BigDecimal("4.0");
    private static final int MONTHS_PER_YEAR = 12;

    private final InterestRateService interestRateService;

    public MortgageServiceImpl(InterestRateService interestRateService) {
        this.interestRateService = interestRateService;
    }

    @Override
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
        return interestRateService.getByMaturityPeriod(request.getMaturityPeriod())
                .map(rate -> calculatePayment(request, rate))
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal calculatePayment(MortgageCheckRequest request, InterestRate rate) {
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