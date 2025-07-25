FROM openjdk:17-jdk-alpine

WORKDIR /app


COPY target/your-spring-boot-app.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
