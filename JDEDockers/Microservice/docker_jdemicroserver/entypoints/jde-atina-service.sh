#!/bin/bash
echo '-SERVICE--------------------------------------------' >/tmp/start.log
echo "   Name: " ${JDE_MICROSERVER_IP} >>/tmp/start.log
echo "   Port: " ${JDE_MICROSERVER_PORT} >>/tmp/start.log
echo '-REPOSITORY-----------------------------------------' >>/tmp/start.log
echo "   Customer: " ${CUSTOMER_REPOSITORY_PROTOCOL}:${CUSTOMER_REPOSITORY_URL} >>/tmp/start.log
echo "   Atina:    " ${ATINA_REPOSITORY_PROTOCOL}:${ATINA_REPOSITORY_URL} >>/tmp/start.log
echo '-LIBRARIES------------------------------------------' >>/tmp/start.log
echo '   Updating Librarie StdWebService Version ' ${STD_WEB_SERVICE_VERSION} >>/tmp/start.log
mvn org.apache.maven.plugins:maven-dependency-plugin:2.4:get -DremoteRepositories=$CUSTOMER_REPOSITORY_PROTOCOL://$CUSTOMER_REPOSITORY_URL -Dartifact=com.jdedwards:StdWebService:$STD_WEB_SERVICE_VERSION -Ddest=/tmp/jde/StdWebService-$STD_WEB_SERVICE_VERSION.jar >>/tmp/start.log
echo '   Updating Librarie jde-lib-wrapped ' ${JDE_LIB_WRAPPED_VERSION} >>/tmp/start.log
mvn org.apache.maven.plugins:maven-dependency-plugin:2.4:get -DremoteRepositories=$CUSTOMER_REPOSITORY_PROTOCOL://$CUSTOMER_REPOSITORY_URL -Dartifact=com.jdedwards:jde-lib-wrapped:$JDE_LIB_WRAPPED_VERSION -Ddest=/tmp/jde/jde-lib-wrapped-$JDE_LIB_WRAPPED_VERSION.jar >>/tmp/start.log
echo '   Updating JDEAtinaServer ' ${JDE_ATINA_SERVER_VERSION}>>/tmp/start.log
mvn org.apache.maven.plugins:maven-dependency-plugin:2.4:get -DremoteRepositories=$ATINA_REPOSITORY_PROTOCOL://$ATINA_REPOSITORY_URL -Dartifact=com.atina:JDEAtinaServer:$JDE_ATINA_SERVER_VERSION -Ddest=/var/jdeatinaserver/JDEAtinaServer-$JDE_ATINA_SERVER_VERSION.jar >>/tmp/start.log
echo '-LICENSE--------------------------------------------' >>/tmp/start.log 
echo '   Code: ' $JDE_MICROSERVER_CODE >>/tmp/start.log
echo '-MICROSERVER----------------------------------------' >>/tmp/start.log
echo "   JDE_MICROSERVER_SECRET_KEY: " ${JDE_MICROSERVER_SECRET_KEY} >>/tmp/start.log
echo "   JDE_MICROSERVER_TOKEN_EXPIRATION: " ${JDE_MICROSERVER_TOKEN_EXPIRATION} >>/tmp/start.log
echo "   JDE_MICROSERVER_ENTERPRISE_SERVER_NAME: " ${JDE_MICROSERVER_ENTERPRISE_SERVER_NAME} >>/tmp/start.log
echo "   JDE_MICROSERVER_ENTERPRISE_SERVER_IP: " ${JDE_MICROSERVER_ENTERPRISE_SERVER_IP} >>/tmp/start.log
echo "   JDE_MICROSERVER_ENTERPRISE_DB_NAME: " ${JDE_MICROSERVER_ENTERPRISE_DB_NAME} >>/tmp/start.log
echo "   JDE_MICROSERVER_ENTERPRISE_DB_IP: " ${JDE_MICROSERVER_ENTERPRISE_DB_IP} >>/tmp/start.log
echo '----------------------------------------------------' >>/tmp/start.log
echo "ADDITIONAL SCRIPT: " >>/tmp/start.log
echo ${JDE_MICROSERVER_ENTERPRISE_SERVER_IP} ${JDE_MICROSERVER_ENTERPRISE_SERVER_NAME} >> /etc/hosts
echo ${JDE_MICROSERVER_ENTERPRISE_DB_IP} ${JDE_MICROSERVER_ENTERPRISE_DB_NAME} >> /etc/hosts 
cat /etc/hosts >>/tmp/start.log
echo '----------------------------------------------------' >>/tmp/start.log
cd  /var/jdeatinaserver
${JAVA_HOME}/bin/java -Xmx2G -Djava.io.tmpdir=/tmp/jde -jar /var/jdeatinaserver/JDEAtinaServer-$STD_WEB_SERVICE_VERSION.jar -ipServicio $JDE_MICROSERVER_IP -portServicio $JDE_MICROSERVER_PORT -localIP 0.0.0.0 -clientcod $JDE_MICROSERVER_CODE  -jdeLibWrappedVersion $JDE_LIB_WRAPPED_VERSION -StdWebServiceVersion $STD_WEB_SERVICE_VERSION  -secretKey $JDE_MICROSERVER_SECRET_KEY -tokenExpiration $JDE_MICROSERVER_TOKEN_EXPIRATION     

