@echo off
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
ECHO ==========================================================
ECHO "Copying requirenments
ECHO ==========================================================
xcopy requirenment c:\tmp /E /C /I /Q /G /H /R /K /Y /Z /J
ECHO ==========================================================
ECHO "Starting Connector Server...
ECHO ==========================================================
java -Djava.io.tmpdir=/tmp/jde -jar target/JDEConnectorServer-jar-with-dependencies.jar -ipServicio localhost -portServicio 8085 -localIP 0.0.0.0 -clientcod demo -jdeLibWrappedVersion 1.0.0 -StdWebServiceVersion 1.0.0 -secretKey 123456789012345678901234567890123456789012345678901234567890 -tokenExpiration 480000
