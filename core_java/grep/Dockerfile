FROM eclipse-temurin:17-jdk-alpine as base
RUN apk add maven
WORKDIR /app

COPY pom.xml pom.xml
RUN mvn dependency:resolve
COPY src src
RUN mvn package

RUN cp ./target/grep*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "./app.jar"]