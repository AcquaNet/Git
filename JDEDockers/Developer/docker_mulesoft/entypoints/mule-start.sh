#!/bin/bash
cd /home
echo '---------------------------------------------'>/home/start.log
echo 'Starting Mule Server' >>/home/start.log
export app_version=$(cat /opt/mule/build-date.txt)
echo '            Build date: ' $app_version >>/home/start.log
echo '------------------------------------------------------------------------------------'>>/home/start.log 
echo '          IP Metrics Server: ' ${metrics_graphite_server}>>/home/start.log
metrics_graphite_server=${metrics_graphite_server}
echo '------------------------------------------------------------------------------------'>>/home/start.log 
echo '          IP JDE Atina Microserver: ' ${JDE_CONNECTOR_MICROSERVER_NAME}: ${JDE_CONNECTOR_MICROSERVER_PORT}>>/home/start.log
export jde_atina_server_name=${JDE_CONNECTOR_MICROSERVER_NAME}
export jde_atina_server_port=${JDE_CONNECTOR_MICROSERVER_PORT}
echo '------------------------------------------------------------------------------------'>>/home/start.log 
export jde_user=${JDE_CONNECTOR_USER}
export jde_password=${JDE_CONNECTOR_PASSWORD}
export jde_environment=${JDE_CONNECTOR_ENVIRONMENT}
export jde_role=${JDE_CONNECTOR_ROLE}
echo '            JDE User: ' $jde_user >>/home/start.log 
echo '            JDE Environment: ' $jde_environment >>/home/start.log
echo '            JDE Role: ' $jde_role >>/home/start.log
echo '------------------------------------------------------------------------------------'>>/home/start.log 
if [  -z "$Shopify_ngrok_server_ip" ];then
	echo 'Recuperando URL del NGROK Server =' $NGROK_MULE_SERVER >>/home/start.log
else
	echo 'Se usara NGROK Server para armar URL Webhook =' $NGROK_MULE_SERVER >>/home/start.log
	echo 'Recuperando URL de redireccion del NGROK Server hacia el Mule Server' >>/home/start.log 
	i=0
	sleep 5m
	urlAPI=$(curl -s ${NGROK_MULE_SERVER}:4040/api/tunnels | jq '.tunnels[0].public_url')
	echo 'URL recuperada del NGROK API Server =' $urlAPI>>/home/start.log
	while [$urlAPI != '"https://*']
	do
	  echo 'Intento numero =' $i >>/home/start.log
	  ((i++))
	  if [[ "$i" == '100' ]]; then
	    echo 'Se supero la secuencia: 100' >>/home/start.log
	    continue
	  fi
	  echo 'Esperando 3 minutos .... ' >>/home/start.log
	  sleep 3m
	  urlAPI=$(curl -s ${NGROK_MULE_SERVER}:4040/api/tunnels | jq '.tunnels[0].public_url')
	  echo 'URL recuperada del NGROK API Server =' $urlAPI>>/home/start.log
	  if [$urlAPI == '"http://*']
		echo 'URL recuperada del NGROK API Server =' $urlAPI>>/home/start.log
	  then
		 urlAPI=$(curl -s ${NGROK_MULE_SERVER}:4040/api/tunnels | jq '.tunnels[1].public_url')
	  fi
	  
	done
	echo 'Extrayendo el dominio de la URL recuperada del NGROK API Server =' $urlAPI>>/home/start.log
	Shopify_Webhooks_url=$(echo $urlAPI | grep -o -P '(?<="https://).*(?=.ngrok.io")').ngrok.io
fi
echo 'Shopify_Webhooks_url a configurar: ' $Shopify_Webhooks_url >>/home/start.log
#echo 'Actualizando la Aplicaciones mule desde Github.' >>/home/start.log	
#cd /opt/mule/mule-standalone-${muleVersion}/appsgit
#git config --global user.email soporte@acqua.net
#git config --global user.name  soporte
#git pull --force >>/home/start.log
#echo 'Copiando actualizaciones descargadas a mule ...' >>/home/start.log
#cp -a /opt/mule/mule-standalone-${muleVersion}/appsgit/. /opt/mule/mule-standalone-${muleVersion}/apps/
#echo 'Actualizando la Aplicaciones mule desde Repository.' >>/home/start.log	
echo '------------------------------------------------------------------------------------'>>/home/start.log
echo 'Download system-layer'>>/home/start.log
mvn org.apache.maven.plugins:maven-dependency-plugin:2.4:get -DremoteRepositories=${REPOSITORY_PROTOCOL}://${REPOSITORY_URL} -Dartifact=com.atina.jde:system-layer:1.0.0 -Ddest=/opt/mule/mule-standalone-${muleVersion}/apps/system-layer-1.0.0.zip >>/home/start.log
echo 'Download Process-layer'>>/home/start.log
mvn org.apache.maven.plugins:maven-dependency-plugin:2.4:get -DremoteRepositories=${REPOSITORY_PROTOCOL}://${REPOSITORY_URL} -Dartifact=com.atina.jde:process-layer:1.0.0 -Ddest=/opt/mule/mule-standalone-${muleVersion}/apps/process-layer-1.0.0.zip >>/home/start.log
echo '------------------------------------------------------------------------------------'>>/home/start.log
echo 'Iniciando Mule' >>/home/start.log
exec /opt/mule/mule-standalone-3.9.0/bin/mule -app system-layer-${mirritVersion}:process-layer-${mirritVersion}
#exec /opt/mule/mule-standalone-3.9.0/bin/mule
echo 'Mule Iniciado' >>/home/start.log