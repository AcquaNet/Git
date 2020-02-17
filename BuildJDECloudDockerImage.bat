@echo off
ECHO ==========================================================
ECHO "Building JDE Atila Connector
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\jde-atila-connector
CALL mvn clean install -DskipTests
CALL mvn clean deploy -DskipTests
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
CALL deploy.bat
ECHO ==========================================================
ECHO Press Enter to continue PUSH TAG
ECHO ==========================================================
PAUSE >nul
ECHO ==========================================================
ECHO "Building JDE Process Layer
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\ProjectsPlatforms\process-layer
CALL mvn clean package
CALL deploy.bat
ECHO ==========================================================
ECHO "Deploy POM from Multimodule
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\ProjectsPlatforms
CALL mvn clean install
ECHO ==========================================================
ECHO Press Enter ....
ECHO ==========================================================
PAUSE >nul
cd C:\_work\JDEConnectorCE\Projects\JDEGeneretaResponseJavaClass
CALL mvn clean install
CALL mvn clean deploy










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
docker rmi 92455890/mule-server:1.0.0
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
docker push 92455890/mule-server:1.0.0
docker push 92455890/ngrok-server:1.0.0
pause