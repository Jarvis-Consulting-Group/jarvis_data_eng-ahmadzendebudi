FROM eclipse-temurin:17-jdk-alpine as base
RUN apk add maven
WORKDIR /app

COPY pom.xml pom.xml
RUN mvn dependency:resolve
COPY src src
RUN mvn package

COPY ./target/grep*.jar ./app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "./app.jar"]