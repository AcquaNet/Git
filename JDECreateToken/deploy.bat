@echo off
echo --------------------------------------------
echo Deploying JDECreateToken...
echo --------------------------------------------
CALL mvn clean package -P AcquaIT
echo --------------------------------------------
echo Copying Jar JDECreateToken
echo --------------------------------------------
COPY /Y target\JDECreateToken-1.0.0-jar-with-dependencies.jar C:\_work\JDEConnectorCE\Delivery\Docker\tools\JDECreateToken-1.0.0.jar
COPY /Y target\JDECreateToken-1.0.0-jar-with-dependencies.jar target\JDECreateToken-1.0.0.jar
mvn deploy:deploy-file -DgroupId=com.atina -DartifactId=pdf-process-addon-jde-layer -Dversion=1.0.0 -DrepositoryId=acquanet-central -Dpackaging=jar -Dfile=target\pdf-process-addon-jde-layer-1.0.0.jar -Durl=http://157.245.236.175:8081/artifactory/libs-release
ECHO ==========================================================
ECHO Press Enter to end
ECHO ==========================================================
PAUSE >nul