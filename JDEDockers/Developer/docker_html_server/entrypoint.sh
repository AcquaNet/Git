#!/bin/bash
set -x
echo '-------------------------------------------------------------'
echo 'Generando Informacion en VAULT'
echo '-------------------------------------------------------------'
echo "Getting Vault Status" + ${VAULT_IP}:${VAULT_PORT} >start.log
http_response_health=$(curl -s -o /home/response.txt -w "%{http_code}" http://${VAULT_IP}:${VAULT_PORT}/v1/sys/health)
echo "Response:" 
cat  /home/response.txt
echo "Http Status:" 
echo $http_response_health
echo '-------------------------------------------------------------'
echo "Starting Vault" + ${VAULT_IP}:${VAULT_PORT} >start.log
initialized="false"
sealed="false"
keys_base64=""
root_token=""
sleep 10
if [ $http_response_health == "501" ] || [ $http_response_health == "503" ]; then
	initialized=$(cat /home/response.txt |jq .initialized)
	sealed=$(cat /home/response.txt |jq .sealed)
	echo "  Initialized: " + $initialized
	echo "  Sealed: " + $sealed
	if [ $initialized == "false" ]; then
		http_response_init=$(curl -s -o /home/response.txt -w "%{http_code}" --request POST --data '{"secret_shares": 1, "secret_threshold": 1}' http://${VAULT_IP}:${VAULT_PORT}/v1/sys/init)
		if [ $http_response_init == "200" ]; then
			keys_base64=$(cat /home/response.txt |jq .keys_base64[0] | tr -d '"')
			root_token=$(cat /home/response.txt |jq .root_token | tr -d '"')
			echo "  Key: " + $keys_base64
			echo "  Root Token: " + $root_token
		else
			echo "Vault ERROR: Cannot initialize Vault" >start.log
		fi	
	else
		echo "Vault" + ${VAULT_IP}:${VAULT_PORT} + " is initialized" >start.log
	fi	
fi
echo '-------------------------------------------------------------'
echo "Unsealing Valult " + ${VAULT_IP}:${VAULT_PORT} >start.log
if [ $sealed == "true" ]; then
    echo '{"key": "'$keys_base64'"}' >/home/data
	cat /home/data
	http_response_unseal=$(curl -s -o /home/response.txt -w "%{http_code}" --request POST --data  @/home/data  http://${VAULT_IP}:${VAULT_PORT}/v1/sys/unseal)
	if [ $http_response_unseal == "200" ]; then
		sealed=$(cat /home/response.txt |jq .sealed)
		echo "  Sealed: " + $sealed
	fi
fi
echo '-------------------------------------------------------------'
echo "Creating UserPass " + ${VAULT_IP}:${VAULT_PORT} >start.log
if [ $sealed == "false" ]; then
    echo '{"type": "userpass"}' >/home/data 
	headerToken=$(echo "X-Vault-Token: ${root_token}")
	echo $headerToken
	cat /home/data
	http_response_unseal=$(curl -s -o /home/response.txt -w "%{http_code}" --request POST --header 'Content-Type: application/json' --header "$headerToken" --data @/home/data  http://${VAULT_IP}:${VAULT_PORT}/v1/sys/auth/userpass)
	if [ $http_response_unseal == "204" ]; then 
		echo "  UserPass created"
	fi
fi
echo '-------------------------------------------------------------'
echo "Creating Policy " + ${VAULT_IP}:${VAULT_PORT} >start.log
if [ $sealed == "false" ]; then
    echo '{"policy": "# List, create, update, and delete key\/value secrets\r\npath \"secret\/*\"\r\n{\r\n  capabilities = [\"create\", \"read\", \"update\", \"delete\", \"list\", \"sudo\"]\r\n}\r\n"}' >/home/data 
	headerToken=$(echo "X-Vault-Token: ${root_token}")
	echo $headerToken
	cat /home/data
	http_response_unseal=$(curl -s -o /home/response.txt -w "%{http_code}" --request PUT --header 'Content-Type: application/json' --header "$headerToken" --data @/home/data  http://${VAULT_IP}:${VAULT_PORT}/v1/sys/policies/acl/my-policy)
	if [ $http_response_unseal == "204" ]; then 
		echo "  Policy created"
	fi
fi
echo '-------------------------------------------------------------'
echo "Mounting secret " + ${VAULT_IP}:${VAULT_PORT} >start.log
if [ $sealed == "false" ]; then
    echo '{"type": "kv-v2"}' >/home/data 
	headerToken=$(echo "X-Vault-Token: ${root_token}")
	echo $headerToken
	cat /home/data
	http_response_unseal=$(curl -s -o /home/response.txt -w "%{http_code}" --request POST --header 'Content-Type: application/json' --header "$headerToken" --data @/home/data  http://${VAULT_IP}:${VAULT_PORT}/v1/sys/mounts/secret)
	if [ $http_response_unseal == "204" ]; then 
		echo "  Secret mounted"
	fi
fi
echo '-------------------------------------------------------------'
echo "Creating User " + ${VAULT_IP}:${VAULT_PORT} >start.log
if [ $sealed == "false" ]; then
    echo '{"password": "'${VAULT_PASSWORD}'", "policies": "my-policy"}' >/home/data 
	headerToken=$(echo "X-Vault-Token: ${root_token}")
	echo $headerToken
	cat /home/data
	http_response_unseal=$(curl -s -o /home/response.txt -w "%{http_code}" --request POST --header 'Content-Type: application/json' --header "$headerToken" --data @/home/data  http://${VAULT_IP}:${VAULT_PORT}/v1/auth/userpass/users/${VAULT_USER})
	if [ $http_response_unseal == "204" ]; then 
		echo "  User created"
	fi
fi
echo '-------------------------------------------------------------'
echo "Loging User " + ${VAULT_IP}:${VAULT_PORT} >start.log
if [ $sealed == "false" ]; then
    echo '{"password": "'${VAULT_PASSWORD}'"}' >/home/data 
	cat /home/data
	http_response_unseal=$(curl -s -o /home/response.txt -w "%{http_code}" --request POST --header 'Content-Type: application/json' --data @/home/data  http://${VAULT_IP}:${VAULT_PORT}/v1/auth/userpass/login/${VAULT_USER})
	if [ $http_response_unseal == "200" ]; then 
		echo "  User Logged"
	fi
fi
echo '-------------------------------------------------------------'
echo "Process Values " >start.log
for i in $(cat /home/values.json |jq ".| keys")
do
	for j in $(cat /home/values.json |jq ".$i| keys")
	do
		echo $(cat /home/values.json |jq ".$i.$j")
		echo '.'
	done
done

echo '-------------------------------------------------------------'
echo '-------------------------------------------------------------'
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