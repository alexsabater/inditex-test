
FROM openjdk:17-jdk


WORKDIR /app


COPY ./target/InditexTest-0.0.1-SNAPSHOT.jar /app/inditex-test.jar


EXPOSE 8080


CMD ["java", "-jar", "/app/inditex-test.jar"]
