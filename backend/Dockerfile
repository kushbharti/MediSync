# MediSync Backend Dockerfile
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw package -DskipTests

# Create final image
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built jar file
COPY --from=0 /app/target/*.jar medisync-backend.jar

# Create non-root user for security
RUN addgroup --system medisync && adduser --system --ingroup medisync medisync
USER medisync:medisync

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/api/public/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "/app/medisync-backend.jar"]

# Add JVM options for containerized environment
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseContainerSupport"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/medisync-backend.jar"]
