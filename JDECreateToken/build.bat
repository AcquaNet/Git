@echo off
echo --------------------------------------------
echo Building JDECreateToken...
echo --------------------------------------------
CALL mvn clean package
echo --------------------------------------------
echo Copying Jar JDECreateToken
echo --------------------------------------------
COPY /Y target\JDECreateToken-1.0.0-jar-with-dependencies.jar C:\_work\JDEConnectorCE\Delivery\Docker\tools\JDECreateToken-1.0.0.jar
ECHO ==========================================================
ECHO Press Enter to end
ECHO ==========================================================
PAUSE >nul