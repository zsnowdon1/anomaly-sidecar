FROM openjdk:17-jdk-slim
COPY build/libs/anomaly-sidecar.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]