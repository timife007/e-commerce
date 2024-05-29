FROM maven:3.8-openjdk-17 as builder

WORKDIR /app
COPY src ./src
COPY pom.xml ./pom.xml
ENV  POSTGRES_DB=e-commerce \
     POSTGRES_PASSWORD=secret \
     POSTGRES_USER=timife
COPY target/authentication-1.0-SNAPSHOT.jar ./target/

#RUN #mvn -f pom.xml clean package

FROM openjdk:17-alpine
WORKDIR /app

COPY --from=builder ./app/target/authentication-1.0-SNAPSHOT.jar ./authentication.jar

CMD ["java", "-jar", "authentication.jar"]