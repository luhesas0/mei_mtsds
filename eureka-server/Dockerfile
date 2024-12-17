# Usar uma imagem base do OpenJDK para rodar aplicações Java
FROM openjdk:17-jdk-slim

# Criar e definir o diretório de trabalho no container
WORKDIR /app

# Copiar o JAR da aplicação gerado pelo Maven para o container
COPY target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar

# Expor a porta 8761 para permitir comunicação externa
EXPOSE 8761

# Comando para rodar o Eureka Server
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
