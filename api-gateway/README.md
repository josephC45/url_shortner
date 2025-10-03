# API Gateway

Handle incoming requests from frontend (verifying with appropriate security headers and cors), delegating them to other backend services, and managing user authentication using JWT (JSON Web Tokens).

---

## Features

- Centralized API Gateway using Spring Boot
- Spring Security Web Filter Chain (Including security headers, cors settings and applying api-key to propagate downstream)
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
- Slf4j
- Docker (optional)

---

## JWT Authentication

- **Login endpoint** issues a JWT on successful authentication.
- JWT is required for protected endpoints (e.g., creating or retrieving shortened URLs).
- Middleware validates token authenticity and extracts user context.

- **Logout endpoint** logs out user (expires persistent cookie)

### Example Workflow

1. User sends login credentials.
2. Server authenticates and returns a JWT.
3. Client stores JWT and includes it in `Authorization` headers
4. Server validates the token for protected routes.

---

## Setup & Run

### Configuration

- env, application.yaml and docker-compose files

### Prerequisites

- Java 17+
- Maven
- Docker (optional)

### Local Development

- Done primarily by spinning up docker container via docker-compose file / docker desktop