
FROM gradle:8.3-jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY src /app/src

RUN gradle clean bootJar


FROM openjdk:17-jdk-slim AS runtime

WORKDIR /app

COPY --from=build /app/build/libs/mychat-gate-service.jar mychat-gate-service.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "mychat-gate-service.jar"]
