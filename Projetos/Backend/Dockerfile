FROM openjdk:11

RUN mkdir devinhouse

COPY target/backend-0.0.1-SNAPSHOT.jar /devinhouse

WORKDIR /devinhouse

CMD ["java", "-jar", "/devinhouse/backend-0.0.1-SNAPSHOT.jar"]

# docker image build -t backend:0.0.1-SNAPSHOT --build-arg version=0.0.1-SNAPSHOT .
# docker image build -t backend:0.0.1-SNAPSHOT .