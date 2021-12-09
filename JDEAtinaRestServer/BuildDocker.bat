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
ECHO **************************************************************************************
ECHO Building Console Docker
ECHO **************************************************************************************
#
#
CALL mvnw package
CALL docker build -f src/main/docker/Dockerfile.jvm -t 92455890/jd-atina-rest-server:1.0.0 .
CALL docker run -i --rm -p 80:80 -p 443:443 92455890/jd-atina-rest-server:1.0.0
#
CALL docker login -u 92455890 -p 97bcd883-fde2-46c6-9135-abda7d2c3a56
CALL docker push 92455890/jd-atina-rest-server:1.0.0
#
#
ECHO **************************************************************************************
ECHO Create Image Native
ECHO **************************************************************************************
#
#CALL mvnw package -Pnative -Dquarkus.native.remote-container-build=true -Dquarkus.container-image.build=true
#
#CALL mvnw clean package -Dquarkus.container-image.push=true
#
#CALL docker run -i --rm -p 443:443 registry-1.docker.io/92455890/jd-atina-rest-server:1.0.0
#












 
