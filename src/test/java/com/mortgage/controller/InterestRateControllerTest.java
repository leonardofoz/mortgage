package com.mortgage.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mortgage.model.InterestRate;
import com.mortgage.service.InterestRateService;

@WebMvcTest(InterestRateController.class)
class InterestRateControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private InterestRateService interestRateService;
    
    @Test
    void getAllInterestRates_ShouldReturnListOfInterestRates() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        List<InterestRate> interestRates = Arrays.asList(
                new InterestRate(5, new BigDecimal("2.5"), now),
                new InterestRate(10, new BigDecimal("3.0"), now)
        );
        
        when(interestRateService.getAllInterestRates()).thenReturn(interestRates);
        
        mockMvc.perform(get("/api/interest-rates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].maturityPeriod").value(5))
                .andExpect(jsonPath("$[0].interestRate").value(2.5));
    }
}