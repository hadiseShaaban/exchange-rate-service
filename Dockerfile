FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/exchange-rate-service.jar /app/exchange-rate-service.jar
ENTRYPOINT ["java", "-jar", "exchange-rate-service.jar"]