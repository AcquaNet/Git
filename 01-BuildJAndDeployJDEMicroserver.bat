@echo off
ECHO ==========================================================
ECHO "Building Shader para Conector
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\jde-gRPC-Shader
CALL mvn clean deploy -P AcquaIT
ECHO ==========================================================
ECHO "Building JDE Connector Service...
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEConnectorService
CALL mvn clean install -P AcquaIT
ECHO ==========================================================
ECHO "Building JDE Connector Server...
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEConnectorServer
CALL mvn clean install -P AcquaIT
ECHO ==========================================================
ECHO "Building JDE Atina Server...
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEAtinaServer
CALL mvn clean deploy -P AcquaIT
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
