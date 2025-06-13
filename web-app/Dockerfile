# # # 1. Etap budowania WAR:
# FROM maven:3.9.9-amazoncorretto-21-debian AS build
# WORKDIR /app

# # # Kopiujemy całość projektu, aby mieć pewność, że nic nie pominiemy
# COPY . .

# # # Budujemy WAR bez testów
# RUN mvn clean package -DskipTests

# # 2. Etap uruchamiania Tomcata:
# FROM tomee:ubuntu-webprofile
# COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/app.war


# FROM maven:3.9.9-amazoncorretto-21-debian AS build
# WORKDIR /app
# COPY . .
# RUN mvn clean package -DskipTests

# FROM tomee:ubuntu-webprofile
# # Copy WAR with explicit name
# COPY --from=build /app/target/*.war /usr/local/tomee/webapps/app.war


FROM maven:3.9.9-amazoncorretto-21-debian AS build

WORKDIR /app

# Copy only the pom.xml for dependency caching
COPY pom.xml .

# Download dependencies (will be cached unless pom.xml changes)
RUN mvn dependency:go-offline

# Copy the rest of the source files
COPY . .

# Build the WAR file
RUN mvn clean package -DskipTests

# Final image with TomEE
FROM tomee:ubuntu-webprofile

# Copy the WAR from the build stage
COPY --from=build /app/target/*.war /usr/local/tomee/webapps/app.war