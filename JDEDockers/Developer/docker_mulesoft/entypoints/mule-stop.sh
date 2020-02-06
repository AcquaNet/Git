#!/bin/bash
cd /home
echo '---------------------------------------------'>/home/stop.log
echo 'STOPPING MULE SERVER' >>/home/stop.log
export app_version=$(cat /opt/mule/build-date.txt)
echo '            Fecha del Build del MuleServer: ' $app_version >>/home/stop.log
echo '------------------------------------------------------------------------------------'>>/home/stop.log 
exec /opt/mule/mule-standalone-3.9.0/bin/mule stop
echo 'Mule Finalizado' >>/home/stop.log