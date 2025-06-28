# URL-Kafka-Consumer

The URL-Kafka_Consumer is an internal service that consumes the URLs pushed to Kafka by Debezium (CDC) upon Short URL creation.

---

## Features

- Consumes and deserializes output from Kafka
- Maps output to DTO that will be pushed to Redis
- Reduces redis cache to last 10 created URLs
- Adds DTO to redis

---

##  Tech Stack

- Java
- Spring Boot 3.5.1 
- Debezium (CDC)
- Jackson (JSON)
- Redis

---

### Prerequisites

- Debezium (CDC)
- Redis 
- Docker