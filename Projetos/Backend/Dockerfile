FROM openjdk:11

RUN mkdir devinhouse

ARG version

ENV APP_VERSION $version

COPY target/backend-$version.jar /devinhouse

WORKDIR /devinhouse

CMD ["java", "-jar", "/devinhouse/backend-$APP_VERSION.jar"]

# docker image build -t backend:0.0.1-SNAPSHOT --build-arg version=0.0.1-SNAPSHOT .