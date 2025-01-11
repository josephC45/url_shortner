# Reactive & Multithreaded URL Shortener

## Overview
This project is a high-performance, scalable backend application for shortening URLs. Built using Java, Spring Boot, and R2DBC-H2, the application supports asynchronous processing, multithreading, and end-to-end testing.

## Features
- Converts long URLs into short URLs using Base 62 conversion.
- Reactive and multithreaded processing for enhanced responsiveness and concurrency.
- REST API endpoints for creating and retrieving URLs.
- Comprehensive testing:
- Unit and integration tests with JUnit/Mockito/WebTestClient.
- End-to-end testing with RestAssured.
- In-memory storage using R2DBC-H2 for fast data operations.
- Logging and monitoring with Aspect-Oriented Programming (AOP).
- CI/CD pipeline using Docker and Jenkins.

## Tech Stack
- Java
- Spring Boot
- R2DBC-H2
- JUnit, Mockito, WebTestClient, and RestAssured for testing
- Docker and Jenkins

### License
This project uses the following open-source libraries:

- **Spring Boot**: Apache License 2.0 (Spring Boot): https://www.apache.org/licenses/LICENSE-2.0

- **RestAssured**: RestAssured License: https://github.com/rest-assured/rest-assured/blob/master/LICENSE
