# syntax=docker/dockerfile:1

FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY pom.xml .
RUN mvn -B -ntp dependency:go-offline

COPY src ./src
RUN mvn -B -ntp clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app

RUN groupadd --system spring && useradd --system --gid spring --create-home --home-dir /home/spring spring

ENV APP_HOME=/app \
    SERVER_PORT=8081 \
    JAVA_OPTS=""

COPY --from=build /workspace/target/your-bamoe-business-service.jar /app/app.jar

RUN chown -R spring:spring /app /home/spring

USER spring:spring

EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${SERVER_PORT} -jar /app/app.jar"]