# Use an OpenJDK 21 base image
FROM eclipse-temurin:21-jdk

# Set the working directory
WORKDIR /app

# Copy the WAR file to the container
COPY target/demo-0.0.1-SNAPSHOT.war /app/myapp.war

# Expose the default Spring Boot port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/myapp.war"]
