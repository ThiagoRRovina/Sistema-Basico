
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY demo/mvnw .
COPY demo/.mvn .mvn
COPY demo/pom.xml .

RUN ./mvnw dependency:go-offline

COPY demo/src src

RUN ./mvnw package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
