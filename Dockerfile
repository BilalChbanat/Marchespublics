# Use Java 17 as base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the Maven/Gradle build files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Make the Maven wrapper executable
RUN chmod +x ./mvnw

# Build the application
RUN ./mvnw package -DskipTests

# Expose the port your application runs on
EXPOSE 8080

# Run the application with the docker profile
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "target/Marchespublics-0.0.1-SNAPSHOT.jar"]