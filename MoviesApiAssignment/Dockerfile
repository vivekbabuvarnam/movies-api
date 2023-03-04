FROM openjdk:11-jdk-slim as builder

ENV JAVA_OPTS="-Xms256M -Xmx512M -XX:InitiatingHeapOccupancyPercent=90 -XX:ActiveProcessorCount=2 -Djava.net.preferIPv4Stack=true -Djava.security.egd=file:/dev/./urandom"

ARG JAR_FILE=target/moviesapi-1.0.0.jar

ADD ${JAR_FILE} moviesapi.jar

COPY academy_awards.csv /usr/src/academy_awards.csv

ENTRYPOINT ["java","-jar","/moviesapi.jar","--spring.profiles.active=$ENVIRONMENT"]

EXPOSE 8080
