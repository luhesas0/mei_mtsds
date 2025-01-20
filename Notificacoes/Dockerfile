FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/Notificacoes-1.0-SNAPSHOT.jar notificacoes.jar
EXPOSE 8076
ENTRYPOINT ["java", "-jar", "notificacoes.jar"]
