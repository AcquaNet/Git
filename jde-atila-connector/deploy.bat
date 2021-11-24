@echo off
echo --------------------------------------------
echo Deploying JDE Connector
echo --------------------------------------------
CALL mvn clean package -P AcquaIT -D skipTests
mvn deploy:deploy-file -DgroupId=com.atina -DartifactId=jd-atina-connector -Dversion=1.0.0 -DrepositoryId=acquanet-central -Dpackaging=zip -Dfile=target\UpdateSite.zip -Durl=http://157.245.236.175:8081/artifactory/libs-release
ECHO ==========================================================
ECHO Press Enter to end
ECHO ==========================================================
PAUSE >nul