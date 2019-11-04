@echo off
ECHO ==========================================================
ECHO "Building JDE Connector Service...
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEConnectorService
CALL mvn clean install
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
ECHO ==========================================================
ECHO "Building JDE Connector Server...
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEConnectorServer
CALL mvn clean install
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
del C:\Users\jgodi\.m2\repository\com\jdedwards\JDEConnectorServer\1.0.0\JDEConnectorServer-1.0.0.jar
copy C:\Users\jgodi\.m2\repository\com\jdedwards\JDEConnectorServer\1.0.0\JDEConnectorServer-1.0.0-jar-with-dependencies.jar C:\Users\jgodi\.m2\repository\com\jdedwards\JDEConnectorServer\1.0.0\JDEConnectorServer-1.0.0.jar
ECHO ==========================================================
ECHO "Building JDE Atina Server...
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEAtinaServer
CALL mvn clean install
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
ECHO ==========================================================
ECHO "Building JDE Microservice...
ECHO ==========================================================
ECHO "Copying JDEAtinaServer-1.0.0.jar to Docker workspace...
copy  C:\Users\jgodi\.m2\repository\com\jdedwards\JDEAtinaServer\1.0.0\JDEAtinaServer-1.0.0.jar C:\_work\JDEConnectorCE\Projects\JDEDockers\Microservice\docker_jdemicro\images
ECHO "JDEAtinaServer-1.0.0.jar copied to Docker workspace...
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
ECHO ==========================================================
ECHO "Creating JDE Microservice Docker
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEDockers\Microservice
ECHO Removing curren jdeatina-server...
docker stop jdeatina-server
docker rm jdeatina-server
docker rmi 92455890/jdeatina-server:1.0.0
ECHO Start Building
docker-compose up --no-start
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
ECHO Moving image to Docker Hub
docker login --username 92455890 --password Anita22223636
docker push 92455890/jdeatina-server:1.0.0
ECHO Starting Server...
cd C:\_work\JDEConnectorCE\Projects\JDEDockers\Microservice
docker-compose start
docker cp tmp/jde/config/JDV920 jdeatina-server:/tmp/config/JDV920
docker cp tmp/jde/lib jdeatina-server:/tmp/jde
pause