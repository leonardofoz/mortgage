package com.mortgage.dto;

import java.math.BigDecimal;

/**
 * Response DTO for mortgage feasibility check.
 */
public class MortgageCheckResponse {
    private Boolean feasible;
    private BigDecimal monthlyCosts;

    public MortgageCheckResponse() {
    }

    public MortgageCheckResponse(Boolean feasible, BigDecimal monthlyCosts) {
        this.feasible = feasible;
        this.monthlyCosts = monthlyCosts;
    }

    public Boolean getFeasible() {
        return feasible;
    }

    public void setFeasible(Boolean feasible) {
        this.feasible = feasible;
    }

    public BigDecimal getMonthlyCosts() {
        return monthlyCosts;
    }

    public void setMonthlyCosts(BigDecimal monthlyCosts) {
        this.monthlyCosts = monthlyCosts;
    }
}