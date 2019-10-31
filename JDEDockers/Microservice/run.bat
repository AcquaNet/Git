# Network
#  docker network ls
#  docker network rm microservice_atinanet
#  docker exec -ti jdeatina-server /bin/bash
#  docker-compose up --no-start
docker stop jdeatina-server
docker rm jdeatina-server
docker rmi soporteacqua/jdeatina-server:1.0.0
docker-compose up --no-start
docker-compose start
docker cp tmp/jde/config/JDV920 jdeatina-server:/tmp/jde/config
docker cp tmp/jde/lib jdeatina-server:/tmp/jde