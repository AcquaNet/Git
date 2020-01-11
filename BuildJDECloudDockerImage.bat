@echo off
ECHO ==========================================================
ECHO "Building JDE Atila Connector
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\jde-atila-connector
CALL mvn clean install -DskipTests
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
ECHO ==========================================================
ECHO "Building JDE Common Library
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\ProjectsPlatforms\common
CALL mvn clean install
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
ECHO ==========================================================
ECHO "Building JDE System Layer
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\ProjectsPlatforms\system-layer
CALL mvn clean package
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
ECHO ==========================================================
ECHO Pushing mule app to Github
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Delivery\Docker
git checkout mule
xcopy C:\_work\JDEConnectorCE\ProjectsPlatforms\system-layer\target\system-layer-1.0.0.zip
REM git commit -a -m "Update Mule App"
REM git push -u origin mule
ECHO ==========================================================
ECHO Press Enter to continue PUSH TAG
ECHO ==========================================================
PAUSE >nul
ECHO ==========================================================
ECHO "Creating JDE Mule Cloud
ECHO ==========================================================
CD C:\_work\JDEConnectorCE\Projects\JDEDockers\Cloud
ECHO Removing curren jdeatina-server...
docker stop ngrok-server
docker stop mule-server
docker rm ngrok-server
docker rm mule-server
docker rm jdeatina-microserver 
docker rmi 92455890/ngrok-server:1.0.0
docker rmi 92455890/jdeatina-server:1.0.0
ECHO ==========================================================
ECHO Press Enter to continue PUSH TAG
ECHO ==========================================================
PAUSE >nul
ECHO Start Building
docker-compose up --no-start
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
ECHO Moving image to Docker Hub
docker login --username 92455890 --password Anita22223636
docker push 92455890/jdeatina-microserver:1.0.0
docker push 92455890/mule-server:1.0.0
docker push 92455890/ngrok-server:1.0.0

ECHO Starting Server...
cd C:\_work\JDEConnectorCE\Projects\JDEDockers\Microservice
docker-compose start
docker cp tmp/jde/config/JDV920 jdeatina-microserver:/tmp/config/JDV920
docker cp tmp/jde/lib jdeatina-microserver:/tmp/jde
pause