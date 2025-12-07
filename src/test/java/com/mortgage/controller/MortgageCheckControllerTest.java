package com.mortgage.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mortgage.dto.MortgageCheckRequest;
import com.mortgage.dto.MortgageCheckResponse;
import com.mortgage.service.MortgageService;

@WebMvcTest(MortgageCheckController.class)
class MortgageCheckControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private MortgageService mortgageService;
    
    @Test
    void checkMortgage_WithValidRequest_ShouldReturnResponse() throws Exception {
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                20,
                new BigDecimal("200000"),
                new BigDecimal("300000")
        );
        
        MortgageCheckResponse response = new MortgageCheckResponse(true, new BigDecimal("1211.58"));
        when(mortgageService.checkMortgage(any(MortgageCheckRequest.class))).thenReturn(response);
        
        mockMvc.perform(post("/api/mortgage-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feasible").value(true))
                .andExpect(jsonPath("$.monthlyCosts").exists());
    }
     
    @Test
    void checkMortgage_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
        MortgageCheckRequest invalidRequest = new MortgageCheckRequest();
        
        mockMvc.perform(post("/api/mortgage-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}

