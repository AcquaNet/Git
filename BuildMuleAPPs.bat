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
ECHO Press Enter ....
ECHO ==========================================================
PAUSE >nul
cd C:\_work\JDEConnectorCE\Projects\JDEGeneretaResponseJavaClass
CALL mvn clean install
CALL mvn clean deploy
