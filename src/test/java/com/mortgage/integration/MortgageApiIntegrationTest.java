package com.mortgage.integration;

import com.mortgage.dto.MortgageCheckRequest;
import com.mortgage.dto.MortgageCheckResponse;
import com.mortgage.model.InterestRate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MortgageApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void getAllInterestRates_ShouldReturnListOfInterestRates() {
        ResponseEntity<List<InterestRate>> response = restTemplate.exchange(
                getBaseUrl() + "/api/interest-rates",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InterestRate>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void checkMortgage_WithValidRequest_ShouldReturnFeasible() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                20,
                new BigDecimal("200000"),
                new BigDecimal("300000")
        );

        HttpEntity<MortgageCheckRequest> entity = new HttpEntity<>(request);
        ResponseEntity<MortgageCheckResponse> response = restTemplate.exchange(
                getBaseUrl() + "/api/mortgage-check",
                HttpMethod.POST,
                entity,
                MortgageCheckResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getFeasible());
        assertTrue(response.getBody().getMonthlyCosts().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void checkMortgage_WhenLoanExceeds4TimesIncome_ShouldReturnNotFeasible() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                20,
                new BigDecimal("250000"),
                new BigDecimal("300000")
        );

        HttpEntity<MortgageCheckRequest> entity = new HttpEntity<>(request);
        ResponseEntity<MortgageCheckResponse> response = restTemplate.exchange(
                getBaseUrl() + "/api/mortgage-check",
                HttpMethod.POST,
                entity,
                MortgageCheckResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().getFeasible());
        assertEquals(BigDecimal.ZERO, response.getBody().getMonthlyCosts());
    }

    @Test
    void checkMortgage_WithInvalidRequest_ShouldReturnBadRequest() {
        MortgageCheckRequest request = new MortgageCheckRequest();

        HttpEntity<MortgageCheckRequest> entity = new HttpEntity<>(request);
        ResponseEntity<String> response = restTemplate.exchange(
                getBaseUrl() + "/api/mortgage-check",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void checkMortgage_WithUnknownMaturityPeriod_ShouldReturnBadRequest() {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                30,
                new BigDecimal("200000"),
                new BigDecimal("300000")
        );

        HttpEntity<MortgageCheckRequest> entity = new HttpEntity<>(request);
        ResponseEntity<String> response = restTemplate.exchange(
                getBaseUrl() + "/api/mortgage-check",
                HttpMethod.POST,
                entity,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Maturity Period Not Found"));
        assertTrue(response.getBody().contains("30"));
    }
}