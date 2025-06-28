# URL Shortener Project

## Overview  
This project started as a single-threaded REST API and has evolved into a Full Stack / Distributed Systems project. 

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
![alt text](./docs/Url_Shortener_HLD.png)

---

## Current Work  
- Clean up any unnecessary dependencies
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

