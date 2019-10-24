#!/bin/bash
echo '----------------------------------------------------' >>start.log
echo 'Starting JDE Microservice' >>start.log
echo '----------------------------------------------------' >>start.log
echo 'CODE: ' >>start.log
echo $JDE_MICROSERVER_CODE >>start.log
echo '----------------------------------------------------' >>start.log
echo "Service Name: " ${JDE_MICROSERVER_IP} >>start.log
echo "Service Port: " ${JDE_MICROSERVER_PORT} >>start.log
echo '----------------------------------------------------' >>start.log
cd  /var/jdeatinaserver
${JAVA_HOME}/bin/java -Xmx2G -jar /var/jdeatinaserver/JDEAtinaServer-1.0.0.jar -ipServicio $JDE_MICROSERVER_IP -portServicio $JDE_MICROSERVER_PORT -localIP 0.0.0.0 -clientcod $JDE_MICROSERVER_CODE

