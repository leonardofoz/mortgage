package com.mortgage.exception;

/**
 * Exception thrown when a maturity period is not found.
 */
public class MaturityPeriodNotFoundException extends RuntimeException {
    
    public MaturityPeriodNotFoundException(String message) {
        super(message);
    }
}