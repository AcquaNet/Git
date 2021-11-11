@echo off
ECHO ==========================================================
ECHO "Creating JDE Microservice Docker
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEDockers\Microservice
ECHO Removing curren jdeatina-server...
docker stop jdeatina-microserver
docker rm jdeatina-microserver
docker rmi 92455890/jdeatina-microserver:1.0.0
ECHO Start Building
docker-compose up --no-start
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
ECHO Moving image to Docker Hub
docker login --username 92455890 --password Anita22223636
docker push 92455890/jdeatina-microserver:1.0.0
docker push 92455890/ngrok-microserver:1.0.0
ECHO Starting Server...
cd C:\_work\JDEConnectorCE\Projects\JDEDockers\Microservice
docker cp config/JPS920 jdeatina-microserver:/tmp/jde/config
docker cp /tmp/build_jde_libs/jde-lib-wrapped-1.0.0.jar jdeatina-microserver:/tmp/jde
docker cp /tmp/build_jde_libs/StdWebService-1.0.0.jar jdeatina-microserver:/tmp/jde
docker-compose start
pause