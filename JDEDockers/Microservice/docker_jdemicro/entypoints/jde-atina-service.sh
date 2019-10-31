#!/bin/bash
echo '----------------------------------------------------' >/tmp/start.log
echo 'Starting JDE Microservice' >>/tmp/start.log
echo '----------------------------------------------------' >>/tmp/start.log
echo 'CODE: ' >>/tmp/start.log
echo $JDE_MICROSERVER_CODE >>/tmp/start.log
echo '----------------------------------------------------' >>/tmp/start.log
echo "Service Name: " ${JDE_MICROSERVER_IP} >>/tmp/start.log
echo "Service Port: " ${JDE_MICROSERVER_PORT} >>/tmp/start.log
echo "JDE_LIB_WRAPPED: " ${JDE_MICROSERVER_JDE_LIB_WRAPPED} >>/tmp/start.log
echo "STD_WEB_SERVICE: " ${JDE_MICROSERVER_STD_WEB_SERVICE} >>/tmp/start.log
echo "JDE_MICROSERVER_SECRET_KEY: " ${JDE_MICROSERVER_SECRET_KEY} >>/tmp/start.log
echo "JDE_MICROSERVER_TOKEN_EXPIRATION: " ${JDE_MICROSERVER_TOKEN_EXPIRATION} >>/tmp/start.log
echo '----------------------------------------------------' >>/tmp/start.log
cd  /var/jdeatinaserver
${JAVA_HOME}/bin/java -Xmx2G -jar /var/jdeatinaserver/JDEAtinaServer-1.0.0.jar -ipServicio $JDE_MICROSERVER_IP -portServicio $JDE_MICROSERVER_PORT -localIP 0.0.0.0 -clientcod $JDE_MICROSERVER_CODE  -jdeLibWrappedVersion $JDE_MICROSERVER_JDE_LIB_WRAPPED -StdWebServiceVersion $JDE_MICROSERVER_STD_WEB_SERVICE  -secretKey $JDE_MICROSERVER_SECRET_KEY -tokenExpiration $JDE_MICROSERVER_TOKEN_EXPIRATION     

