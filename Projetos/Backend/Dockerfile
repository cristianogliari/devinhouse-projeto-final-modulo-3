## Build
FROM maven:3.6-jdk-11 AS build

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package install

## Image
FROM openjdk:11

RUN mkdir devinhouse

COPY --from=build /home/app/target/backend-0.0.1-SNAPSHOT.jar /devinhouse

WORKDIR /devinhouse

CMD ["java", "-jar", "/devinhouse/backend-0.0.1-SNAPSHOT.jar"]