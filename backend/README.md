# Backend - Reactive & Multithreaded URL Shortener

Secure, High-performance, scalable backend application for creating user accounts, and shortening URLs.
Built using Java, Spring Boot, and R2DBC-Postgres, the application supports asynchronous processing, multithreading, Unit/Integration/E2E testing and observability/monitoring via Prometheus and Grafana

---

## Features

- Creates User accounts (passwords salted and hashed)
- Converts long URLs into short URLs using Base 62 conversion.
- Reactive and multithreaded processing for enhanced responsiveness and concurrency.
- REST API endpoints for creating and retrieving URLs.
- Comprehensive testing:
- Unit and integration tests with JUnit/Mockito/WebTestClient.
- End-to-end testing with RestAssured.
- Storage using R2DBC-Postgres for fast reactive data operations.
- Logging (logs hardened) and monitoring with Grafana, Prometheus and Aspect-Oriented Programming (AOP).
- CI/CD pipeline using Docker and GitHub Actions.

---

## Tech Stack

- Java 17+
- Spring Boot
- R2DBC-Postgres
- JUnit, Mockito, WebTestClient, and RestAssured for testing
- Grafana/Prometheus for monitoring/observability
- Docker and GitHub Actions (CI/CD)

---

### Configuration

- env, application.yaml and docker-compose files

### Prerequisites

- Java 17+
- Maven
- Docker (optional)

---

### Local Development

- Done primarily by spinning up docker container via docker-compose file / docker desktop

---

### License

This project uses the following open-source libraries:

- **Spring Boot**: Apache License 2.0 (Spring Boot): <https://www.apache.org/licenses/LICENSE-2.0>

- **RestAssured**: RestAssured License: <https://github.com/rest-assured/rest-assured/blob/master/LICENSE>
