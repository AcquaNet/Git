@echo off
echo "Creating JDE Atina Server..."
docker network rm microservice_atinanet
docker-compose up --no-start
docker-compose start
docker cp tmp/jde/config/JDV920 jdeatina-server:/tmp/
docker cp tmp/jde/lib jdeatina-server:/tmp/jde