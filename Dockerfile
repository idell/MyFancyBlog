FROM maven:3.6-openjdk-17-slim AS build
COPY pom.xml /app/
COPY src /app/src
RUN mvn -f /app/pom.xml clean package

FROM openjdk:17-alpine
COPY --from=build /app/target/my-fancy-blog*.jar /app/blog.jar
ENTRYPOINT ["java", "-jar","/app/blog.jar"]