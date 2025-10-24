FROM azul/zulu-openjdk:17 AS builder

WORKDIR /app

COPY target/eda-consumer-1.0-SNAPSHOT.jar app.jar

FROM azul/zulu-openjdk-alpine:17

WORKDIR /app

COPY --from=builder /app/app.jar .

ENTRYPOINT ["java", "-jar", "app.jar"]