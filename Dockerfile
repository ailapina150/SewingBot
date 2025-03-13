FROM maven AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn package
FROM openjdk:23-slim
COPY target/saleChatBot-1.0-SNAPSHOT.jar ./bot.jar
CMD ["java", "-jar", "bot.jar"]