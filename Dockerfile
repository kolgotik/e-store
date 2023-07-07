FROM maven:3.9.3-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package

FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp

COPY target/*.jar e-store-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/e-store-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080