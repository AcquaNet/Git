FROM java:8-jdk-alpine
MAINTAINER fjgodino@gmail.com
#
# ====================================
# COPY APP
# ==================================== 
#
COPY /target/jd-create-ini-files-1.0.0-jar-with-dependencies.jar /usr/app/
#
RUN echo "Oh dang look at that $JD_E1M_URL"
#
# ====================================
# Start
# ====================================
WORKDIR /usr/app/
#
#ENTRYPOINT echo $0 $1 $2 $3 $4
ENTRYPOINT java -jar jd-create-ini-files-1.0.0-jar-with-dependencies.jar -e $2 -u $0 -p $1 -s $3



