FROM ubuntu:16.04
MAINTAINER javier.godino@acqua.net.ar
# ====================================
# ARGUMENTOS DE CONSTRUCCION
# ====================================
# ====================================
# Java
# ====================================
ARG javaFolder=/var/java
# ------------------------------------------------------------------
# JRE 64 Bits
# ------------------------------------------------------------------
# JRE 64
ARG javaImage=j2re-image-x86_64-u232-b01-20190729.tar.bz2
ARG javaRootFolder=j2re-image

# ====================================
# Swagger Server
# ====================================
ARG swaggerServerPorts=8085

# ====================================
# JAVA
# ====================================
ENV JAVA_HOME ${javaFolder}/${javaRootFolder}
  
# ====================================
# JDE ATINA MICRO SERVER
# ====================================
ARG JDE_MICROSERVER_IP=${JDE_MICROSERVER_IP} 
ARG JDE_MICROSERVER_PORT=${JDE_MICROSERVER_PORT} 
ARG JDE_MICROSERVER_CODE=${JDE_MICROSERVER_CODE}
ARG JDE_MICROSERVER_JDE_LIB_WRAPPED=${JDE_MICROSERVER_JDE_LIB_WRAPPED}
ARG JDE_MICROSERVER_STD_WEB_SERVICE=${JDE_MICROSERVER_STD_WEB_SERVICE}
#
ENV JDE_MICROSERVER_IP ${JDE_MICROSERVER_IP} 
ENV JDE_MICROSERVER_PORT ${JDE_MICROSERVER_PORT} 
ENV JDE_MICROSERVER_CODE ${JDE_MICROSERVER_CODE}
ENV JDE_MICROSERVER_JDE_LIB_WRAPPED ${JDE_MICROSERVER_JDE_LIB_WRAPPED}
ENV JDE_MICROSERVER_STD_WEB_SERVICE ${JDE_MICROSERVER_STD_WEB_SERVICE}

# ====================================
# Actualizacion del Ubuntu
# ====================================
RUN apt update

# ====================================
# Set TimeZone
# ====================================
RUN export DEBIAN_FRONTEND=noninteractive
RUN ln -fs /usr/share/zoneinfo/America/Buenos_Aires /etc/localtime
RUN apt-get install -y tzdata
RUN dpkg-reconfigure --frontend noninteractive tzdata

# ====================================
# Instalacion de Software Requerido
# ====================================
RUN apt-get install -y wget
RUN apt-get install -y iproute2
RUN apt-get install -y net-tools 
RUN apt-get install -y bzip2
RUN apt-get install -y unzip
#RUN apt-get install -y curl
# ===================================================
# Instalacion de Java OpenJDK Modificado 64 Bits
# ===================================================
RUN mkdir ${javaFolder}
RUN cd ${javaFolder}
COPY java64/${javaImage} ${javaFolder}/
RUN tar -xvjf ${javaFolder}/${javaImage} -C ${javaFolder}
RUN /usr/bin/update-alternatives --install /usr/bin/java java ${javaFolder}/${javaRootFolder}/bin/java 1 
RUN rm ${javaFolder}/${javaImage} 
  
# ====================================
# Creacion de Variable LOCALIP
# ====================================
RUN LOCALIP=$(hostname  -I | cut -f2 -d' ') 

# ====================================
# Create MAVEN
# ====================================
#ARG MAVEN_VERSION=3.6.3
#ARG USER_HOME_DIR="/root"
#ARG SHA=c35a1803a6e70a126e80b2b3ae33eed961f83ed74d18fcd16909b2d44d7dada3203f1ffe726c17ef8dcca2dcaa9fca676987befeadc9b9f759967a8cb77181c0
#ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries
#RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
#  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
#  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
#  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
#  && rm -f /tmp/apache-maven.tar.gz \
#  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
#ENV MAVEN_HOME /usr/share/maven
#ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
#COPY images/settings-docker.xml /usr/share/maven/ref/
  
# ====================================
# Instalacion de JDE Atina Microserver
# ====================================
RUN mkdir /tmp/config
RUN mkdir /tmp/lib
RUN mkdir /tmp/jde
RUN mkdir /tmp/jde/config
RUN mkdir /tmp/jde/lib

# ====================================
# Instalacion de JDE Atina Microserver
# ====================================

RUN mkdir /var/jdeatinaserver
RUN cd /var/jdeatinaserver
COPY images/JDEAtinaServer-1.0.0.jar /var/jdeatinaserver/
EXPOSE $SWAGGER_SERVER_PORT 

# ====================================
# Copiar Claves de Licencia	
# ==================================== 
COPY images/jdelicense.dat /var/jdeatinaserver/

# ====================================
# Start
# ====================================

COPY docker-entrypoint.sh /
COPY entypoints/* /docker-entrypoint.d/
RUN chmod +x /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.d/jde-atina-service.sh
ENTRYPOINT ["/docker-entrypoint.sh"]