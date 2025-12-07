package com.mortgage.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Request DTO for mortgage feasibility check.
 */
public class MortgageCheckRequest {
    
    @NotNull(message = "Income is required")
    @Positive(message = "Income must be positive")
    private BigDecimal income;

    @NotNull(message = "Maturity period is required")
    @Positive(message = "Maturity period must be positive")
    private Integer maturityPeriod;

    @NotNull(message = "Loan value is required")
    @Positive(message = "Loan value must be positive")
    private BigDecimal loanValue;

    @NotNull(message = "Home value is required")
    @Positive(message = "Home value must be positive")
    private BigDecimal homeValue;

    public MortgageCheckRequest() {
    }

    public MortgageCheckRequest(BigDecimal income, Integer maturityPeriod, BigDecimal loanValue, BigDecimal homeValue) {
        this.income = income;
        this.maturityPeriod = maturityPeriod;
        this.loanValue = loanValue;
        this.homeValue = homeValue;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Integer getMaturityPeriod() {
        return maturityPeriod;
    }

    public void setMaturityPeriod(Integer maturityPeriod) {
        this.maturityPeriod = maturityPeriod;
    }

    public BigDecimal getLoanValue() {
        return loanValue;
    }

    public void setLoanValue(BigDecimal loanValue) {
        this.loanValue = loanValue;
    }

    public BigDecimal getHomeValue() {
        return homeValue;
    }

    public void setHomeValue(BigDecimal homeValue) {
        this.homeValue = homeValue;
    }
}