# Dockerfile para gestao_utilizadores
FROM openjdk:17-jdk-slim
COPY target/gestao_utilizadores-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8071
ENTRYPOINT ["java", "-jar", "/app.jar"]
