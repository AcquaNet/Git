@echo off
ECHO ==========================================================
ECHO "Building JDE Connector Service...
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEConnectorServer
ECHO ==========================================================
ECHO "Starting Connector Server...
ECHO ==========================================================
java -Djava.io.tmpdir=/tmp/jde -jar target/JDEConnectorServer-jar-with-dependencies.jar -ipServicio localhost -portServicio 8085 -localIP 0.0.0.0 -clientcod demo -jdeLibWrappedVersion 1.0.0 -StdWebServiceVersion 1.0.0 -secretKey 123456789012345678901234567890123456789012345678901234567890 -tokenExpiration 480000