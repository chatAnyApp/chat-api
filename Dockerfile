FROM eclipse-temurin:17-jdk-jammy
WORKDIR /build
COPY mvnw mvnw
COPY .mvn .mvn
COPY pom.xml pom.xml
COPY src src
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -DskipTests
RUN ./mvnw package -DskipTests
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=0 /build/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar", "--server.port=${PORT:-8080}"]
