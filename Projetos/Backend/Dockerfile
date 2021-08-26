## Build
FROM maven:3.6-jdk-11 as maven_build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package install

## Image
FROM openjdk:11

RUN mkdir devinhouse

COPY target/backend-0.0.1-SNAPSHOT.jar /devinhouse

WORKDIR /devinhouse

CMD ["java", "-jar", "/devinhouse/backend-0.0.1-SNAPSHOT.jar"]