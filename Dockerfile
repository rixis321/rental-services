# Użyj obrazu JDK jako bazy
FROM eclipse-temurin:17-jdk-alpine

# Ustaw katalog roboczy
WORKDIR /app

# Skopiuj plik jar do kontenera
COPY target/rental-services-0.0.1-SNAPSHOT.jar app.jar

# Wystaw port, na którym działa aplikacja
EXPOSE 8080

# Polecenie uruchamiające aplikację
ENTRYPOINT ["java", "-jar", "app.jar"]

