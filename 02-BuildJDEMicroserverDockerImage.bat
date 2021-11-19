@echo off
ECHO ==========================================================
ECHO "Creating JDE Microservice Docker
ECHO ==========================================================
cd C:\_work\JDEConnectorCE\Projects\JDEDockers\Microservice
ECHO Removing curren jdeatina-server...
docker stop jd-atina-microserver
docker rm jd-atina-microserver
docker rmi 92455890/jd-atina-microserver:1.0.0
ECHO Start Building
docker-compose up --no-start
ECHO ==========================================================
ECHO Docker Files
ECHO ==========================================================
"C:\Program Files\7-Zip\7z.exe" a -tzip jd-docker-files.zip .env docker-compose-dist.yml
mvn deploy:deploy-file -DgroupId=com.atina -DartifactId=jd-docker-files -Dversion=1.0.0 -DrepositoryId=acquanet-central -Dpackaging=zip -Dfile=jd-docker-files.zip -Durl=http://157.245.236.175:8081/artifactory/libs-release 
PAUSE >nul
ECHO ==========================================================
ECHO Press Enter to continue
ECHO ==========================================================
PAUSE >nul
ECHO Moving image to Docker Hub
docker login -u 92455890 -p 97bcd883-fde2-46c6-9135-abda7d2c3a56
docker push 92455890/jd-atina-microserver:1.0.0
ECHO Starting Server...
docker cp /tmp/build_jde_libs/JPS920 jd-atina-microserver:/tmp/jde/config
docker cp /tmp/build_jde_libs/jde-lib-wrapped-1.0.0.jar jd-atina-microserver:/tmp/jde
docker cp /tmp/build_jde_libs/StdWebService-1.0.0.jar jd-atina-microserver:/tmp/jde
docker-compose start
pause