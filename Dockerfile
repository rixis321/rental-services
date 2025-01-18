# Użyj obrazu z Mavenem do budowania projektu
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Ustaw katalog roboczy w kontenerze
WORKDIR /app

# Skopiuj pliki projektu do kontenera
COPY pom.xml .
COPY src ./src

# Budowanie aplikacji
RUN mvn clean package -DskipTests

# Użyj mniejszego obrazu JDK do uruchomienia aplikacji
FROM eclipse-temurin:17-jdk-alpine

# Ustaw katalog roboczy
WORKDIR /app

# Skopiuj wygenerowany plik JAR z etapu budowy
COPY --from=build /app/target/rental-services-0.0.1-SNAPSHOT.jar app.jar

# Wystaw port aplikacji
EXPOSE 8080

# Uruchomienie aplikacji
ENTRYPOINT ["java", "-jar", "app.jar"]


