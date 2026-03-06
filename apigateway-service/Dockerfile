FROM gradle:9.3.0-jdk17 AS build
WORKDIR /app

COPY gradlew /app/gradlew
COPY gradle /app/gradle
COPY build.gradle settings.gradle /app/
RUN sed -i 's/\r$//' /app/gradlew && chmod +x /app/gradlew
RUN /app/gradlew dependencies --no-daemon

COPY src /app/src
RUN /app/gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 8762
ENTRYPOINT ["java","-jar","/app/app.jar"]