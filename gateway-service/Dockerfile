FROM maven:3.8-openjdk-17 as builder

WORKDIR /app
COPY src ./src
COPY pom.xml ./pom.xml
COPY target/gateway-service-1.0-SNAPSHOT.jar ./target/

#RUN #mvn -f pom.xml clean package

FROM openjdk:17-alpine

COPY --from=builder ./app/target/gateway-service-1.0-SNAPSHOT.jar ./gateway-service.jar

ENTRYPOINT ["java", "-jar", "./gateway-service.jar"]