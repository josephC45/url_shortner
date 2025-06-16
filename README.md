# URL Shortener Project

## Overview  
This project started as a single-threaded backend service built with Java, Spring/Spring Boot, and an in-memory R2DBC-H2 database.  
As of June 16, it has evolved into a full-stack application featuring a Vue.js frontend and a robust Java backend.

---

## Features  
- **Frontend:** Vue.js, JavaScript  
- **Backend:** Java, Spring Boot, Aspect-Oriented Programming (AOP), reactive programming, multithreading  
- **Security:** JWT authentication, Cookies  
- **Service Discovery:** Enables dynamic discovery of microservices for scalable deployment  
- **API Gateway:** Centralized routing and request handling, including authentication, rate limiting, and load balancing  
- **Testing:** Unit, Integration, and End-to-End (E2E) tests  
- **Monitoring:** Prometheus, Grafana  
- **Containerization:** Docker  

---

## Current Work  
- Integrating JWT for authentication and authorization  

---

## Planned Features  
- Implement Change Data Capture (CDC) to ensure single-write semantics by capturing database changes  
- Stream captured changes to Kafka, acting as an ordered, durable event log  
- Use Redis to cache the last 10 globally created shortened URLs for fast retrieval and display to users  

---

### Prerequisites
- Docker (Optional)

### Installation  
- Clone the repository

### Running Stack via Docker
```bash
docker-compose up
```

