FROM openjdk:11

RUN mkdir devinhouse

COPY target/backend-0.0.1-SNAPSHOT.jar /devinhouse

WORKDIR /devinhouse

CMD ["java", "-jar", "/devinhouse/backend-0.0.1-SNAPSHOT.jar"]