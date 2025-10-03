# URL-Kafka-Consumer

Internal service that consumes the URLs pushed to Kafka by Debezium (CDC) upon Short URL creation.

---

## Features

- Consumes and deserializes output from Kafka
- Maps output to DTO that will be pushed to Redis
- Reduces redis cache to last 10 created URLs
- Adds DTO to redis

---

## Tech Stack

- Java
- Spring Boot 3.5.1
- Debezium (CDC)
- Jackson (JSON)
- Redis

---

### Configuration

- env, application.yaml and docker-compose files

### Prerequisites

- Debezium (CDC)
- Redis
- Docker

### Local Development

- Done primarily by spinning up docker container via docker-compose file / docker desktop

---
