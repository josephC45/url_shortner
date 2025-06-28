# Backend - Reactive & Multithreaded URL Shortener

This project is a high-performance, scalable backend application for shortening URLs. 
Built using Java, Spring Boot, and R2DBC-Postgres, the application supports asynchronous processing, multithreading, and end-to-end testing.

---

## Features

- Creates User accounts
- Converts long URLs into short URLs using Base 62 conversion.
- Reactive and multithreaded processing for enhanced responsiveness and concurrency.
- REST API endpoints for creating and retrieving URLs.
- Comprehensive testing:
- Unit and integration tests with JUnit/Mockito/WebTestClient.
- End-to-end testing with RestAssured.
- Storage using R2DBC-Postgres for fast reactive data operations.
- Logging and monitoring with Grafana, Prometheus and Aspect-Oriented Programming (AOP).
- CI/CD pipeline using Docker and GitHub Actions.

---

## Tech Stack

- Java
- Spring Boot
- R2DBC-Postgres
- JUnit, Mockito, WebTestClient, and RestAssured for testing
- Grafana and Prometheus for monitoring and observability
- Docker and GitHub Actions (CI/CD)

---

### Configuration

- PostgresDB, Eureka, Prometheus, Application and other settings can be found in application.yaml

### Prerequisites

- Java 17+
- Maven
- Docker (optional)

---

### Local Development

```bash
./mvnw spring-boot:run
```

### Create Jar

```bash
./mvnw clean package
```

### Create Docker Image and Run Docker Container
```bash
docker build -t api-gateway .
docker run -p 8080:8080 api-gateway
```

---

### License

This project uses the following open-source libraries:

- **Spring Boot**: Apache License 2.0 (Spring Boot): https://www.apache.org/licenses/LICENSE-2.0

- **RestAssured**: RestAssured License: https://github.com/rest-assured/rest-assured/blob/master/LICENSE
