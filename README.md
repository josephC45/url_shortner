# URL Shortener Project

## Overview  

This project started as a single-threaded REST API and has evolved into a scalable, high-performance URL shortener
built with Java, Spring Boot, and a reactive microservices architecture. 
It transforms long URLs into compact links using Base62 encoding, optimized for responsiveness and concurrency.

Key backend components include a non-blocking, multithreaded system using R2DBC with PostgreSQL, enabling efficient asynchronous data access.
The system exposes secure, RESTful APIs for URL creation and retrieval, protected with JWT-based authentication and authorization.

Real-time monitoring is achieved through Debezium CDC (Change Data Capture) integrated with Kafka, which captures and streams new URL creation events.
These events are consumed and cached in Redis, allowing the frontend to display the latest 10 created URLs globally, enhancing user experience and responsiveness.

The application follows a microservices architecture, leveraging Eureka for service discovery
and Feign for inter-service communication, simplifying HTTP client implementation and enabling dynamic service resolution.

A lightweight, responsive Vue.js frontend offers a seamless user interface, while Aspect-Oriented Programming (AOP) ensures consistent logging and observability across services. The project is fully containerized with Docker, and CI/CD pipelines are implemented using GitHub Actions to enable rapid, reliable deployments.

---

## Features

- **Frontend:** Vue.js, JavaScript  
- **Backend:** Java, Spring Boot, Aspect-Oriented Programming (AOP), reactive programming, multithreading  
- **Security:** JWT authentication/authorization, Cookies  
- **Service Discovery:** Enables dynamic discovery of microservices for scalable deployment (Eureka)
- **API Gateway:** Centralized routing and request handling, including authentication, rate limiting, and load balancing (Feign)
- **Testing:** Unit, Integration, and End-to-End (E2E) tests  
- **Monitoring:** Prometheus, Grafana  
- **Change Data Capture (CDC):** Debezium
- **Message Broker:** Kafka
- **Caching:** Redis
- **Containerization:** Docker  

---

## High-Level Design

![alt text](docs/URL_Shortener_HLD.png)

---

## Current Work
  
- Add prometheus/grafana to cdc/kafka/redis
- Add appropriate logging

---

### Prerequisites

- Docker 

### Installation

- Clone the repository

### Running Stack via Docker

```bash
docker-compose up
```

