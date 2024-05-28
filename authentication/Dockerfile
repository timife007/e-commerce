FROM maven:3.8-openjdk-17 as builder

COPY src /usr/app/src
COPY pom.xml /usr/app
ENV  POSTGRES_DB=e-commerce \
     POSTGRES_PASSWORD=secret \
     POSTGRES_USER=timife
RUN mvn -f /usr/app/pom.xml clean package

FROM openjdk:17-alpine

COPY --from=build /usr/app/target/*.jar /usr/app/app.jar

ENTRYPOINT ["java", "-jar", "/usr/app/app.jar"]