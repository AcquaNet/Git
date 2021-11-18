@echo off
ECHO ==========================================================
ECHO Activando JAVA 11
ECHO ==========================================================
set JAVA_HOME=C:\Program Files\Java\openjdk-11
set Path=%JAVA_HOME%\bin;%Path%
echo Java 11 activated.
ECHO ==========================================================
ECHO Build
ECHO ==========================================================
mvnw clean deploy
REM java -jar target\jd-generate-ini-files-1.0.0-runner.jar -u jde_admin -p Modusbox2020! -s http://mdx-alpha-wls.westus.cloudapp.azure.com:8999/manage
REM mvnw compile quarkus:dev -Dsuspend=true -Dquarkus.args="-u jde_admin -p Modusbox2020! -s http://mdx-alpha-wls.westus.cloudapp.azure.com:8999/manage"