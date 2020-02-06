#!/bin/ash
echo 'Cambiando Autorizacion. Token informado: ' ${NGROK_MICROSERVER_TOKEN} >>/home/ngrok/start-ngrok-server.log
sed -i 's/%TOKEN%/${NGROK_MICROSERVER_TOKEN}/g' /home/ngrok/.ngrok2/ngrok.yml
echo 'Nuevo : ngrok.yml' >>/home/ngrok/start-ngrok-server.log
cat /home/ngrok/.ngrok2/ngrok.yml >>/home/ngrok/start-ngrok-server.log
echo 'Iniciando NGROK' >>/home/ngrok/start-ngrok-server.log
cd /bin
ngrok authtoken $NGROK_MICROSERVER_TOKEN
echo 'ngrok authtoken '  $NGROK_MICROSERVER_TOKEN ' ejecutado' >>/home/ngrok/start-ngrok-server.log
set -x
echo 'NGROK_MICROSERVER_HOST: '  $NGROK_MICROSERVER_HOST >>/home/ngrok/start-ngrok-server.log
echo 'NGROK Ejecutando... ngrok http https://'$NGROK_MICROSERVER_HOST ':'$NGROK_MICROSERVER_PORT>>/home/ngrok/start-ngrok-server.log
ngrok http http://$NGROK_MICROSERVER_HOST:$NGROK_MICROSERVER_PORT
exec "$@"
