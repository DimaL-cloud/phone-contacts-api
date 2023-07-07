FROM openjdk:17-jdk-alpine
EXPOSE 8080
WORKDIR /app
COPY target/phone-contacts-api-0.0.1.jar /app
CMD ["java", "-jar", "/app/phone-contacts-api-0.0.1.jar"]
