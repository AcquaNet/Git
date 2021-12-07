@echo off
#
ECHO **************************************************************************************
ECHO Use JDK11
ECHO **************************************************************************************
#
set JAVA_HOME=C:\Program Files\Java\openjdk-11
set Path=%JAVA_HOME%\bin;%Path%
echo Java 11 activated.
# 
#
CALL docker build -t 92455890/jd-atina-rest-server:1.0.0 .
docker run -p 443:443 92455890/jd-atina-rest-server
#
#
ECHO **************************************************************************************
ECHO Building Console Docker
ECHO **************************************************************************************
# 
#
CALL mvnw package
CALL docker build -f src/main/docker/Dockerfile.jvm -t 92455890/jd-atina-rest-server .
CALL docker run -i --rm -p 443:443 92455890/jd-atina-rest-server
#
ECHO **************************************************************************************
ECHO Running Locally
ECHO **************************************************************************************
#
CALL java -jar target/quarkus-app/quarkus-run.jar
#
ECHO **************************************************************************************
ECHO Create Image Native
ECHO **************************************************************************************
#
CALL mvnw package -Pnative -Dquarkus.native.remote-container-build=true -Dquarkus.container-image.build=true
#
CALL mvnw clean package -Dquarkus.container-image.push=true
#
CALL docker run -i --rm -p 443:443 registry-1.docker.io/92455890/jd-atina-rest-server:1.0.0
#












 
