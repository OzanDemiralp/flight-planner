# 1. Aşama: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. Aşama: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Uygulama jar dosyasını al
COPY --from=build /app/target/flightplanner-*.jar app.jar

# Log klasörü (opsiyonel)
RUN mkdir -p /logs

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
