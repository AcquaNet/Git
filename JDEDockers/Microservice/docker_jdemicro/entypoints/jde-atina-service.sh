#!/bin/bash
echo '----------------------------------------------------' >/tmp/start.log
echo 'Starting JDE Microservice' >>/tmp/start.log
echo '----------------------------------------------------' >>/tmp/start.log
echo 'CODE: ' >>/tmp/start.log
echo $JDE_MICROSERVER_CODE >>/tmp/start.log
echo '----------------------------------------------------' >>/tmp/start.log
echo "Service Name: " ${JDE_MICROSERVER_IP} >>/tmp/start.log
echo "Service Port: " ${JDE_MICROSERVER_PORT} >>/tmp/start.log
echo '----------------------------------------------------' >>/tmp/start.log
cd  /var/jdeatinaserver
${JAVA_HOME}/bin/java -Xmx2G -jar /var/jdeatinaserver/JDEAtinaServer-1.0.0.jar -ipServicio $JDE_MICROSERVER_IP -portServicio $JDE_MICROSERVER_PORT -localIP 0.0.0.0 -clientcod $JDE_MICROSERVER_CODE

