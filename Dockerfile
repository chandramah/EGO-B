# Multi-stage Dockerfile for Spring Boot (Maven) - Java 17

# ===== Build stage =====
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom first for layer caching
COPY pom.xml ./
RUN mvn -q -e -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
RUN mvn -q -DskipTests package

# ===== Runtime stage =====
FROM eclipse-temurin:17-jre

# Non-root user for better security
RUN useradd -ms /bin/bash appuser
USER appuser

WORKDIR /app

# Copy built jar from builder
COPY --from=build /app/target/*.jar app.jar

# Application runtime configuration via environment
ENV JAVA_OPTS=""
ENV SERVER_PORT=8080
EXPOSE 8080

# Health-friendly, graceful signals
STOPSIGNAL SIGTERM

# Run Spring Boot app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
