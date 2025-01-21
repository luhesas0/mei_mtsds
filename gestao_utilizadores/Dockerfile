# Dockerfile para gestao_utilizadores
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/gestao_utilizadores-1.0-SNAPSHOT.jar utilizadores.jar
EXPOSE 8071
ENTRYPOINT ["java", "-jar", "/utilizadores.jar"]
