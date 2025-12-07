package com.mortgage.contract;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;
import com.mortgage.dto.MortgageCheckRequest;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "springdoc.api-docs.path=/api-docs",
    "springdoc.swagger-ui.path=/swagger-ui.html"
})
class OpenApiContractTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void getInterestRates_ShouldMatchOpenApiContract() {
        given()
        .when()
            .get("/api/interest-rates")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    void checkMortgage_WithValidRequest_ShouldMatchOpenApiContract() {
        String openApiSpecUrl = "http://localhost:" + port + "/api-docs";
        MortgageCheckRequest request = new MortgageCheckRequest(
                new BigDecimal("50000"),
                20,
                new BigDecimal("200000"),
                new BigDecimal("300000")
        );

        given()
            .filter(new OpenApiValidationFilter(openApiSpecUrl))
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/api/mortgage-check")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

}