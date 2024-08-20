FROM openjdk:21-jdk-slim

WORKDIR /app

COPY final-project-0.0.1-SNAPSHOT.jar /app/app.jar

RUN apt-get update && \
    apt-get install -y curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
