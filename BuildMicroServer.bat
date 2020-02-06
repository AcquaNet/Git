@echo off
ECHO ==========================================================
ECHO "StdWebService y jde-lib-wrapped
ECHO ==========================================================
ECHO ==========================================================
ECHO "Building JDEConnectorService
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEConnectorService
CALL mvn clean package
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
ECHO ==========================================================
ECHO "Building JDEConnectorServer
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEConnectorServer
CALL mvn clean package
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
ECHO ==========================================================
ECHO "Building JDE Atina Server
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEAtinaServer
CALL mvn clean package
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
xcopy C:\_work\JDEConnectorCE\Projects\JDEAtinaServer\target\JDEAtinaServer-1.0.0.jar C:\_work\JDEConnectorCE\Projects\JDEDockers\Developer\docker_jdemicroserver\images
xcopy C:\_work\JDEConnectorCE\Projects\JDEAtinaServer\target\JDEAtinaServer-1.0.0.jar C:\_work\JDEConnectorCE\Projects\JDEDockers\Microservice\docker_jdemicroserver\images


