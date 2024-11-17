# Build stage
FROM gradle:7.5 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean bootJar --stacktrace --info

# Package stage
FROM openjdk:17
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "/app.jar"]
