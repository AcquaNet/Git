#!/bin/bash
cd /home
echo '---------------------------------------------'>/home/start.log
echo 'INICIANDO MULE SERVER' >>/home/start.log
export app_version=$(cat /opt/mule/build-date.txt)
echo '            Fecha del Build del MuleServer: ' $app_version >>/home/start.log
echo '------------------------------------------------------------------------------------'>>/home/start.log 
echo '          IP Metrics Server: ' ${metrics_graphite_server}>>/home/start.log
metrics_graphite_server=${metrics_graphite_server}
echo '------------------------------------------------------------------------------------'>>/home/start.log 
echo '          IP JDE Microserver Server: ' ${jde_atina_server_name}: ${jde_atina_server_port}>>/home/start.log
jde_atina_server_name=${jde_atina_server_name}
jde_atina_server_port=${jde_atina_server_port}
if [  -z "$Shopify_ngrok_server_ip" ];then
	echo 'Recuperando URL del NGROK Server =' $Shopify_ngrok_server_ip >>/home/start.log
else
	echo 'Se usara NGROK Server para armar URL Webhook =' $Shopify_ngrok_server_ip >>/home/start.log
	echo 'Recuperando URL de redireccion del NGROK Server hacia el Mule Server' >>/home/start.log 
	i=0
	sleep 5m
	urlAPI=$(curl -s ${Shopify_ngrok_server_ip}:4040/api/tunnels | jq '.tunnels[0].public_url')
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
	  urlAPI=$(curl -s ${Shopify_ngrok_server_ip}:4040/api/tunnels | jq '.tunnels[0].public_url')
	  echo 'URL recuperada del NGROK API Server =' $urlAPI>>/home/start.log
	  if [$urlAPI == '"http://*']
		echo 'URL recuperada del NGROK API Server =' $urlAPI>>/home/start.log
	  then
		 urlAPI=$(curl -s ${Shopify_ngrok_server_ip}:4040/api/tunnels | jq '.tunnels[1].public_url')
	  fi
	  
	done
	echo 'Extrayendo el dominio de la URL recuperada del NGROK API Server =' $urlAPI>>/home/start.log
	Shopify_Webhooks_url=$(echo $urlAPI | grep -o -P '(?<="https://).*(?=.ngrok.io")').ngrok.io
fi
echo 'Shopify_Webhooks_url a configurar: ' $Shopify_Webhooks_url >>/home/start.log
echo '------------------------------------------------------------------------------------'>>/home/start.log
echo 'Actualizando la Aplicaciones mule desde Github.' >>/home/start.log	
cd /opt/mule/mule-standalone-${muleVersion}/appsgit
git config --global user.email soporte@acqua.net
git config --global user.name  soporte
git pull --force >>/home/start.log
echo 'Copiando actualizaciones descargadas a mule ...' >>/home/start.log
cp -a /opt/mule/mule-standalone-${muleVersion}/appsgit/. /opt/mule/mule-standalone-${muleVersion}/apps/
echo 'Iniciando Mule' >>/home/start.log
#exec /opt/mule/mule-standalone-3.9.0/bin/mule -app mirrit-system-layer-${mirritVersion}:mirrit-process-layer-${mirritVersion}
exec /opt/mule/mule-standalone-3.9.0/bin/mule -app system-layer-${mirritVersion}:mirrit-process-layer-${mirritVersion}
echo 'Mule Iniciado' >>/home/start.log