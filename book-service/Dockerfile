FROM openjdk:17-jdk-slim
COPY build/libs/book-service-1.0-SNAPSHOT.jar book-service.jar
ENTRYPOINT ["java", "-jar", "book-service.jar"]