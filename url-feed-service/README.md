# URL-Feed-Service

REST API that retrieves the last 10 globally created URLs from Redis.

---

## Features

- Reactive REST API
- Makes sure origin of request is coming from api-gateway via api-key
- Calls Redis to get back 10 most recently created URLs
- Deserializes redis payload and returns to user

---

## Tech Stack

- Java
- Spring Boot 3.5.1
- Jackson (JSON)
- Redis

---

### Configuration

- env, application.yaml and docker-compose files

### Prerequisites

- Redis
- Docker (optional)

### Local Development

- Done primarily by spinning up docker container via docker-compose file / docker desktop

---
