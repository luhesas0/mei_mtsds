FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/Notificacoes-0.0.1-SNAPSHOT.jar notificacoes.jar
EXPOSE 8076
ENTRYPOINT ["java", "-jar", "notificacoes.jar"]
