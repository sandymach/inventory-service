# Use a base image that contains JDK
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the host to the container
COPY target/inventory-service-0.0.1-SNAPSHOT.jar inventory-service-0.0.1-SNAPSHOT.jar

# Expose the port on which the application will run
EXPOSE 8080

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "inventory-service-0.0.1-SNAPSHOT.jar"]
