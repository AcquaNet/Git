@echo off
ECHO ==========================================================
ECHO Build Process JAVA 8
ECHO ==========================================================
mvn clean deploy -P AcquaIT 
ECHO ==========================================================
ECHO Docker
ECHO ==========================================================
docker build -t 92455890/jd-create-ini-files:1.0.0 .
docker login -u 92455890 -p 97bcd883-fde2-46c6-9135-abda7d2c3a56
docker push 92455890/jd-create-ini-files:1.0.0
docker run --rm -v /tmp/build_jde_libs:/tmp/build_jde_libs/ -i --name jd-create-ini-files 92455890/jd-create-ini-files:1.0.0 jde_admin Modusbox2020! JPS920 http://mdx-alpha-wls.westus.cloudapp.azure.com:8999/manage