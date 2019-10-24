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
ENV SWAGGER_SERVER_PORT ${JDE_MICROSERVER_PORT} 
ENV JDE_MICROSERVER_CODE ${JDE_MICROSERVER_CODE}
 
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
# Instalacion de JDE Atina Microserver
# ====================================

RUN mkdir /var/jdeatinaserver
RUN cd /var/jdeatinaserver
COPY images/JDEAtinaServer-1.0.0.jar /var/jdeatinaserver/
VOLUME ["/var/jdeatinaserver/logs", "/var/jdeatinaserver/jde-atina-files"]
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