
FROM gradle:8.3-jdk17 AS build

WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY src /app/src

RUN gradle clean bootJar


FROM openjdk:17-jdk-slim AS runtime

WORKDIR /app

COPY --from=build /app/build/libs/gate-service.jar gate-service.jar

EXPOSE 8083
RUN apt-get update && apt-get install -y curl

COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh

ENTRYPOINT ["/wait-for.sh","java", "-jar", "gate-service.jar"]
