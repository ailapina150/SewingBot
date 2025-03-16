FROM maven AS build
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn package
FROM openjdk:23-slim
COPY --from=build /app/target/saleChatBot-1.0-SNAPSHOT.jar ./app.jar
CMD ["java", "-jar", "app.jar"]