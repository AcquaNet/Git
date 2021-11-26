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
CALL mvnw clean package -Dquarkus.container-image.push=true -Dquarkus.profile=cintelink-full











 
