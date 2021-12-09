FROM java:8-jdk-alpine
MAINTAINER fjgodino@gmail.com
#
# ====================================
# COPY APP
# ==================================== 
#
COPY target/quarkus-app /usr/app/
#
EXPOSE 443
#
# ====================================
# Start
# ====================================
WORKDIR /usr/app/
#
ENTRYPOINT java -Djava.net.preferIPv4Stack=true -jar quarkus-run.jar



