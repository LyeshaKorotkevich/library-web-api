FROM openjdk:17-jdk-slim
COPY build/libs/auth-service-1.0-SNAPSHOT.jar auth-service.jar
ENTRYPOINT ["java", "-jar", "auth-service.jar"]