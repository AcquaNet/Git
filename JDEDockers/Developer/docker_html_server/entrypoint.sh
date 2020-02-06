#!/bin/bash
set -x
echo '-------------------------------------------------------------'
echo 'Defining IP'
echo '-------------------------------------------------------------'
echo 'Iniciando Determinacion de IPs '>>start.log
HTML_SERVER_IP=${HTML_SERVER_IP}
echo 'Direccion Publica de las Aplicaciones'>>start.log
echo 'PUBLIC IP:' $HTML_SERVER_IP >>start.log
echo 'Copiando HTML' >>start.log
echo '-------------------------------------------------------------'
echo 'Transformando Template'
echo '-------------------------------------------------------------'
echo 'Eliminando directorio /temp_html'>>start.log
rm -r /temp_html
echo 'Creando directorio /temp_html'>>start.log
mkdir /temp_html
echo 'Copiando directorio /home/html a /temp_html'>>start.log
cp -r /home/html /temp_html/
echo 'Template copiados a /temp_html'>>start.log
echo '-------------------------------------------------------------'
echo 'Reemplazando URL_SHOPIFY '
echo '-------------------------------------------------------------'
echo 'Reemplazando valores del URL_SHOPIFY del archivo /temp_html/html/index.html valor: ' ${URL_SHOPIFY} >>start.log
sed -i -r "s/%PROTO_SHOPIFY%/${PROTO_SHOPIFY}/g" /temp_html/html/index.html
sed -i -r "s/%URL_SHOPIFY%/${URL_SHOPIFY}/g" /temp_html/html/index.html
sed -i -r "s/%NAME_SHOPIFY%/${NAME_SHOPIFY}/g" /temp_html/html/index.html
echo '-------------------------------------------------------------'
echo 'Reemplazando URL_SHOPIFY_ADMIN '
echo '-------------------------------------------------------------'
echo 'Reemplazando valores del URL_SHOPIFY_ADMIN del archivo /temp_html/html/index.html valor: ' ${URL_SHOPIFY_ADMIN} >>start.log
sed -i -r "s/%PROTO_SHOPIFY_ADMIN%/${PROTO_SHOPIFY_ADMIN}/g" /temp_html/html/index.html
sed -i -r "s/%URL_SHOPIFY_ADMIN%/${URL_SHOPIFY_ADMIN}/g" /temp_html/html/index.html
sed -i -r "s/%NAME_SHOPIFY_ADMIN%/${NAME_SHOPIFY_ADMIN}/g" /temp_html/html/index.html
echo '-------------------------------------------------------------'
echo 'Reemplazando URL_MULE_CONSOLE '
echo '-------------------------------------------------------------' 
echo 'Reemplazando valores del URL_MULE_CONSOLE del archivo /temp_html/html/index.html valor: ' ${URL_MULE_CONSOLE} >>start.log
sed -i -r "s/%PROTO_MULE_CONSOLE%/${PROTO_MULE_CONSOLE}/g" /temp_html/html/index.html
sed -i -r "s/%URL_MULE_CONSOLE%/${URL_MULE_CONSOLE}/g" /temp_html/html/index.html
sed -i -r "s/%NAME_MULE_CONSOLE%/${NAME_MULE_CONSOLE}/g" /temp_html/html/index.html
echo '-------------------------------------------------------------'
echo 'Reemplazando URL_METRICS_CONSOLE '
echo '-------------------------------------------------------------'
echo 'Reemplazando valores del URL_METRICS_CONSOLE del archivo /temp_html/html/index.html valor: ' ${URL_METRICS_CONSOLE} >>start.log
sed -i -r "s/%PROTO_METRICS_CONSOLE%/${PROTO_METRICS_CONSOLE}/g" /temp_html/html/index.html
sed -i -r "s/%URL_METRICS_CONSOLE%/${URL_METRICS_CONSOLE}/g" /temp_html/html/index.html
sed -i -r "s/%NAME_METRICS_CONSOLE%/${NAME_METRICS_CONSOLE}/g" /temp_html/html/index.html
echo '-------------------------------------------------------------'
echo 'Reemplazando URL_SEC_CONSOLE '
echo '-------------------------------------------------------------'
echo 'Reemplazando valores del URL_SEC_CONSOLE del archivo /temp_html/html/index.html valor: ' ${URL_SEC_CONSOLE} >>start.log
sed -i -r "s/%PROTO_SEC_CONSOLE%/${PROTO_SEC_CONSOLE}/g" /temp_html/html/index.html
sed -i -r "s/%URL_SEC_CONSOLE%/${URL_SEC_CONSOLE}/g" /temp_html/html/index.html
sed -i -r "s/%NAME_SEC_CONSOLE%/${NAME_SEC_CONSOLE}/g" /temp_html/html/index.html
echo '-------------------------------------------------------------'
echo 'Valores reemplazados' >>start.log
cp -r /temp_html/html /usr/share/nginx/
echo 'HTML Copiado' >>start.log
exec "$@"