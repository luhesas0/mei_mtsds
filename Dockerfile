FROM openjdk:17-jdk

EXPOSE 8074

ADD repo_entregas/target/repo_entregas-1.0-SNAPSHOT.jar repo.jar

ENTRYPOINT ["java", "-jar", "repo.jar"]