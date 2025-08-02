FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Download dependencies and build the JAR
RUN mvn clean package

FROM openjdk:17-jdk-alpine

WORKDIR /app
VOLUME /tmp

# Install bash for the entrypoint script
# RUN apk add --no-cache bash

COPY --from=build /app/target/*.jar app.jar


# Copy the entrypoint script
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

EXPOSE 8080

ENTRYPOINT ["/app/entrypoint.sh"]