package com.mortgage.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Model class for interest rate.
 */
public class InterestRate {
    private Integer maturityPeriod;
    private BigDecimal interestRate;
    private LocalDateTime lastUpdate;

    public InterestRate() {
    }

    public InterestRate(Integer maturityPeriod, BigDecimal interestRate, LocalDateTime lastUpdate) {
        this.maturityPeriod = maturityPeriod;
        this.interestRate = interestRate;
        this.lastUpdate = lastUpdate;
    }

    public Integer getMaturityPeriod() {
        return maturityPeriod;
    }

    public void setMaturityPeriod(Integer maturityPeriod) {
        this.maturityPeriod = maturityPeriod;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}