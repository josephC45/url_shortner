# Build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests 

# Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/url-feed-service-0.0.1-SNAPSHOT.jar url-feed-service.jar
ENTRYPOINT [ "java", "-jar", "url-feed-service.jar" ]