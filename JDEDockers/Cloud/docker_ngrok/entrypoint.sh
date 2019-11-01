#!/bin/ash
echo 'Cambiando Autorizacion. Token informado: ' ${NGROK_NGROK_TOKEN} >>/home/ngrok/start-ngrok-server.log
sed -i 's/%TOKEN%/${NGROK_NGROK_TOKEN}/g' /home/ngrok/.ngrok2/ngrok.yml
echo 'Nuevo : ngrok.yml' >>/home/ngrok/start-ngrok-server.log
cat /home/ngrok/.ngrok2/ngrok.yml >>/home/ngrok/start-ngrok-server.log
echo 'Iniciando NGROK' >>/home/ngrok/start-ngrok-server.log
cd /bin
ngrok authtoken $NGROK_NGROK_TOKEN
echo 'ngrok authtoken '  $NGROK_NGROK_TOKEN ' ejecutado' >>/home/ngrok/start-ngrok-server.log
set -x
echo 'NGROK_MULE_SERVER_HOST: '  $NGROK_MULE_SERVER_HOST >>/home/ngrok/start-ngrok-server.log
echo 'NGROK Ejecutando... ngrok http https://'$NGROK_MULE_SERVER_HOST ':'$NGROK_MULE_SERVER_PORT>>/home/ngrok/start-ngrok-server.log
ngrok http https://$NGROK_MULE_SERVER_HOST:$NGROK_MULE_SERVER_PORT
exec "$@"
