FROM openjdk:17-jdk-slim

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/UrlShortner-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} urlShortnerApp.jar

ENTRYPOINT [ "java", "-jar", "/urlShortnerApp.jar" ]