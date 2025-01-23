FROM openjdk:17-jdk

EXPOSE 8074

ADD repo_entregas/target/repo.jar repo.jar

ENTRYPOINT ["java", "-jar", "repo.jar"]