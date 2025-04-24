FROM openjdk:17-jdk-slim
COPY target/chickentest-app-0.0.1-SNAPSHOT.jar chickentest-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/chickentest-app-0.0.1-SNAPSHOT.jar"]