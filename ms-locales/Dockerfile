# ===== Etapa 1: compilación con Maven =====
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia el pom y descarga dependencias (se cachea si el pom no cambia)
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copia el código y genera el .jar (sin ejecutar tests en la imagen)
COPY src ./src
RUN mvn -q clean package -DskipTests

# ===== Etapa 2: imagen final, liviana, solo para ejecutar =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia el .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Render inyecta la variable PORT; la app la lee desde application.yml
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
