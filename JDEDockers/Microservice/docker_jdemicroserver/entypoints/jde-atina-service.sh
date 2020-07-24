#!/bin/bash
echo '----------------------------------------------------' >/tmp/start.log
echo 'Updating Librarie StdWebService' >>/tmp/start.log
mvn org.apache.maven.plugins:maven-dependency-plugin:2.4:get -DremoteRepositories=$REPOSITORY_PROTOCOL://$REPOSITORY_URL -Dartifact=com.jdedwards:StdWebService:1.0.0 -Ddest=/tmp/jde/StdWebService-1.0.0.jar >>/tmp/start.log
echo 'Updating Librarie jde-lib-wrapped' >>/tmp/start.log
mvn org.apache.maven.plugins:maven-dependency-plugin:2.4:get -DremoteRepositories=$REPOSITORY_PROTOCOL://$REPOSITORY_URL -Dartifact=com.jdedwards:jde-lib-wrapped:1.0.0 -Ddest=/tmp/jde/jde-lib-wrapped-1.0.0.jar	>>/tmp/start.log
echo 'Updating Microservice' >>/tmp/start.log
mvn org.apache.maven.plugins:maven-dependency-plugin:2.4:get -DremoteRepositories=$REPOSITORY_PROTOCOL://$REPOSITORY_URL -Dartifact=com.atina:JDEAtinaServer:1.0.0 -Ddest=/var/jdeatinaserver/JDEAtinaServer-1.0.0.jar	>>/tmp/start.log
echo '----------------------------------------------------' >>/tmp/start.log
echo '----------------------------------------------------' >>/tmp/start.log
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
echo "JDE_MICROSERVER_ENTERPRISE_SERVER_NAME: " ${JDE_MICROSERVER_ENTERPRISE_SERVER_NAME} >>/tmp/start.log
echo "JDE_MICROSERVER_ENTERPRISE_SERVER_IP: " ${JDE_MICROSERVER_ENTERPRISE_SERVER_IP} >>/tmp/start.log
echo "JDE_MICROSERVER_ENTERPRISE_DB_NAME: " ${JDE_MICROSERVER_ENTERPRISE_DB_NAME} >>/tmp/start.log
echo "JDE_MICROSERVER_ENTERPRISE_DB_IP: " ${JDE_MICROSERVER_ENTERPRISE_DB_IP} >>/tmp/start.log
echo '----------------------------------------------------' >>/tmp/start.log
echo "ADDTIONAL SCRIPT: " >>/tmp/start.log
echo ${JDE_MICROSERVER_ENTERPRISE_SERVER_IP} ${JDE_MICROSERVER_ENTERPRISE_SERVER_NAME} >> /etc/hosts
echo ${JDE_MICROSERVER_ENTERPRISE_DB_IP} ${JDE_MICROSERVER_ENTERPRISE_DB_NAME} >> /etc/hosts 
cat /etc/hosts >>/tmp/start.log
echo '----------------------------------------------------' >>/tmp/start.log
cd  /var/jdeatinaserver
${JAVA_HOME}/bin/java -Xmx2G -Djava.io.tmpdir=/tmp/jde -jar /var/jdeatinaserver/JDEAtinaServer-1.0.0.jar -ipServicio $JDE_MICROSERVER_IP -portServicio $JDE_MICROSERVER_PORT -localIP 0.0.0.0 -clientcod $JDE_MICROSERVER_CODE  -jdeLibWrappedVersion $JDE_MICROSERVER_JDE_LIB_WRAPPED -StdWebServiceVersion $JDE_MICROSERVER_STD_WEB_SERVICE  -secretKey $JDE_MICROSERVER_SECRET_KEY -tokenExpiration $JDE_MICROSERVER_TOKEN_EXPIRATION     

