@echo off
ECHO ==========================================================
ECHO JAVA 11
ECHO ==========================================================
#
set JAVA_HOME=C:\Program Files\Java\openjdk-11
set Path=%JAVA_HOME%\bin;%Path%
echo Java 11 activated.
#
ECHO ==========================================================
ECHO CLEANING
ECHO ==========================================================
#
cd C:\_work\JDEConnectorCE\Projects\JDEAtinaRestServer
ECHO Removing curren jdeatina-server...
docker stop jd-atina-rest-server
docker rm jd-atina-rest-server
docker rmi 92455890/jd-atina-rest-server:1.0.0
#
ECHO ==========================================================
ECHO Creating JDE Rest AIP Microservice Docker
ECHO ==========================================================
#
ECHO Start Building
CALL mvnw package
CALL docker build -f src/main/docker/Dockerfile.jvm -t 92455890/jd-atina-rest-server:1.0.0 .
#
ECHO ==========================================================
ECHO Deploying
ECHO ==========================================================
#
CALL docker login -u 92455890 -p 97bcd883-fde2-46c6-9135-abda7d2c3a56
CALL docker push 92455890/jd-atina-rest-server:1.0.0
#
pause