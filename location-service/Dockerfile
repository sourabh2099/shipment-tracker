# BUILD
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml .

COPY shipment-tracker/pom.xml shipment-tracker/
COPY notification-service/pom.xml notification-service/
COPY shipment-tracker-pojo/pom.xml shipment-tracker-pojo/
COPY shipment-eureka-server/pom.xml shipment-eureka-server/
COPY shiment-api-cloud-gateway/pom.xml shiment-api-cloud-gateway/
COPY location-service/pom.xml location-service/
COPY shipment-tracker-config-server/pom.xml shipment-tracker-config-server/
COPY routing-engine/pom.xml routing-engine/

RUN mvn install -N -DskipTests

COPY shipment-tracker-pojo/src shipment-tracker-pojo/src
RUN mvn -f shipment-tracker-pojo/ install -DskipTests

RUN mvn dependency:go-offline -pl location-service

COPY location-service/src location-service/src
RUN mvn -f location-service/ install -DskipTests

FROM eclipse-temurin:21-jre-alpine AS layers

WORKDIR /app

COPY --from=builder /app/location-service/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

RUN ls -la /app/ || echo "app dir empty"

# RUN
FROM eclipse-temurin:21-jre-alpine as runner
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=layers /app/dependencies/ ./
COPY --from=layers /app/spring-boot-loader ./
COPY --from=layers /app/snapshot-dependencies/ ./
COPY --from=layers /app/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]