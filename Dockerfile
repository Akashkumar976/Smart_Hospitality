# Step 1: Use Eclipse Temurin Java 17 base image
FROM eclipse-temurin:17-jdk

# Step 2: Set working directory inside container
WORKDIR /app

# Step 3: Copy entire project into container
COPY . .

# Step 4: Install Maven
RUN apt-get update && apt-get install -y maven

# Step 5: Build the Spring Boot project (skip tests)
RUN mvn clean package -DskipTests

# Step 6: Expose port 8080 for Spring Boot
EXPOSE 8080

# Step 7: Run the Spring Boot JAR
ENTRYPOINT ["java","-jar","target/SmartHospitality-0.0.1-SNAPSHOT.jar"]
