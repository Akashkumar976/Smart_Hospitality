# ============ 1: Build Stage (Maven + Java 17) ============
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies first (cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy entire source code
COPY src ./src

# Build project
RUN mvn clean package -DskipTests

# ============ 2: Run Stage (Lightweight JDK) ============
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
