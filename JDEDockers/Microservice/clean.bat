docker-compose stop
docker rm jdeatina-microserver
docker rmi -f 92455890/jdeatina-microserver:1.0.0
docker-compose up --no-start
docker cp tmp/jde/config/JDV920 jdeatina-microserver:/tmp/jde/config
docker cp tmp/jde/config/JDV920 jdeatina-microserver:/tmp/jde
docker-compose start
pause