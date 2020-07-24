@echo off
ECHO ==========================================================
ECHO "Building Shader para Conector
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\jde-gRPC-Shader
CALL mvn clean deploy
ECHO ==========================================================
ECHO "Building JDE Connector Service...
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEConnectorService
CALL mvn clean install
ECHO ==========================================================
ECHO "Building JDE Connector Server...
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEConnectorServer
CALL mvn clean install
del C:\Users\jgodi\.m2\repository\com\atina\JDEConnectorServer\1.0.0\JDEConnectorServer-1.0.0.jar
copy C:\Users\jgodi\.m2\repository\com\atina\JDEConnectorServer\1.0.0\JDEConnectorServer-1.0.0-jar-with-dependencies.jar C:\Users\jgodi\.m2\repository\com\atina\JDEConnectorServer\1.0.0\JDEConnectorServer-1.0.0.jar
ECHO ==========================================================
ECHO "Building JDE Atina Server...
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEAtinaServer
CALL mvn clean install
CALL deploy.bat
ECHO ==========================================================
ECHO "Building JDE Microservice...
ECHO ==========================================================
ECHO "Copying JDEAtinaServer-1.0.0.jar to Docker workspace...
copy  C:\Users\jgodi\.m2\repository\com\atina\JDEAtinaServer\1.0.0\JDEAtinaServer-1.0.0.jar C:\_work\JDEConnectorCE\Projects\JDEDockers\Microservice\docker_jdemicroserver\images
copy  C:\Users\jgodi\.m2\repository\com\atina\JDEAtinaServer\1.0.0\JDEAtinaServer-1.0.0.jar C:\_work\JDEConnectorCE\Projects\JDEDockers\Developer\docker_jdemicroserver\images
ECHO "JDEAtinaServer-1.0.0.jar copied to Docker workspace...
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
