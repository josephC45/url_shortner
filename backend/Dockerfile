# Build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests 

# Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/UrlShortner-0.0.1-SNAPSHOT.jar backend.jar
EXPOSE 8081
ENTRYPOINT [ "java", "-jar", "backend.jar" ]