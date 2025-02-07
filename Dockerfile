# Build Stage
FROM openjdk:18-jdk-alpine as builder

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY src/ src/

RUN ./mvnw test

RUN ./mvnw clean package -DskipTests=false

FROM openjdk:18-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/technical_test-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
