# API Gateway

This is the backend service for the URL Shortener application, built using Spring Boot. It serves as an API Gateway, handling all incoming requests, delegating them to internal services, and managing user authentication using JWT (JSON Web Tokens).

---

## Features

- Centralized API Gateway using Spring Boot
- Redirection logic
- JWT-based authentication and authorization
- Token generation and validation
- RESTful endpoints
- Docker support

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JSON Web Token (JWT)
- Maven
- Docker (optional)

---

## JWT Authentication

- **Login endpoint** issues a JWT on successful authentication.
- JWT is required for protected endpoints (e.g., creating or retrieving shortened URLs).
- Middleware validates token authenticity and extracts user context.

### Example Workflow

1. User sends login credentials.
2. Server authenticates and returns a JWT.
3. Client stores JWT and includes it in `Authorization` headers
4. Server validates the token for protected routes.

---

## Setup & Run

### Configuration

- JWT secret, expiration, and other settings can be found in application.yaml

### Prerequisites

- Java 17+
- Maven
- Docker (optional)

### Local Development

```bash
./mvnw spring-boot:run

```bash
./mvnw clean package

```bash
docker build -t api-gateway .
docker run -p 8080:8080 api-gateway


