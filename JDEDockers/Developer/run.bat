docker-compose up --no-start
docker cp tmp/jde/lib jdeatina-microserver:/tmp/jde
docker cp tmp/jde/config/JDV920 jdeatina-microserver:/tmp/jde/config
docker-compose start