# Mortgage Application

A web application for mortgage feasibility checks and interest rate management.

## Technology Stack

- Java 17
- Spring Boot 3.2.0
- Spring Web
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Running the Application

1. Build the project:
   ```bash
   mvn clean install
   ```

2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

3. The application will start on `http://localhost:8080`

4. Swagger UI:
   ```bash
   http://localhost:8080/swagger-ui.html
   ```

5. API Documentation:
   ```bash
   http://localhost:8080/api-docs
   ```

### Running Tests

```bash
mvn test
```

### Code Coverage

Generate coverage report:
```bash
mvn jacoco:report
```

View report: `target/site/jacoco/index.html`

Verify coverage (fails if below 80% line, 70% branch):
```bash
mvn verify
```

## Docker

### Build Docker Image

```bash
docker build -t mortgage-api:latest .
```

### Run with Docker

```bash
docker run -p 8080:8080 mortgage-api:latest
```

### Run with Docker Compose

```bash
docker-compose up
```

## API Endpoints

- `GET /api/interest-rates` - Get all interest rates
- `POST /api/mortgage-check` - Check mortgage feasibility

### Request Example

```json
{
  "income": 50000,
  "maturityPeriod": 20,
  "loanValue": 200000,
  "homeValue": 300000
}
```

### Response Example

```json
{
  "feasible": true,
  "monthlyCosts": 1211.58
}
```

## Business Rules

The mortgage check applies the following business rules:

1. **Loan-to-Income Ratio**: The loan value must not exceed 4 times the annual income
2. **Loan-to-Value Ratio**: The loan value must not exceed the home value
3. **Monthly Payment Affordability**: The monthly costs must not exceed 30% of the annual income divided by 12 months

If any of these rules are violated, the mortgage is considered not feasible.

## Code Coverage Requirements

- **Line Coverage**: Minimum 80%
- **Branch Coverage**: Minimum 70%

The build will fail if coverage falls below these thresholds.