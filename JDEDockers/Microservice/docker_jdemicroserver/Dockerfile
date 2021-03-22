FROM ubuntu:16.04
MAINTAINER javier.godino@acqua.net.ar
# ====================================
# ARGUMENTOS DE CONSTRUCCION
# ====================================
# ====================================
# Java
# ====================================
ARG javaFolder=/var/java
ARG javaType=JDK
# ------------------------------------------------------------------
# JRE 64 Bits
# ------------------------------------------------------------------
# JRE 64
ARG javaJREImage=j2re-image-x86_64-u232-b01-20190729.tar.bz2
ARG javaJRERootFolder=j2re-image

# JDK 64
ARG javaJDKImage=j2sdk-image-x86_64-u232-b01-20190729.tar.bz2
ARG javaJDKRootFolder=j2sdk-image

# ====================================
# Swagger Server
# ====================================
ARG swaggerServerPorts=8085

# ====================================
# JAVA
# ====================================
#ENV JAVA_HOME ${javaFolder}/${javaJRERootFolder}
ENV JAVA_HOME ${javaFolder}/${javaJDKRootFolder}
#  
# ====================================
# REPOSITORY
# ====================================
#
ARG ATINA_REPOSITORY_PROTOCOL=${ATINA_REPOSITORY_PROTOCOL} 
ARG ATINA_REPOSITORY_URL=${ATINA_REPOSITORY_URL} 
ARG CUSTOMER_REPOSITORY_PROTOCOL=${CUSTOMER_REPOSITORY_PROTOCOL} 
ARG CUSTOMER_REPOSITORY_URL=${CUSTOMER_REPOSITORY_URL} 
#
ENV ATINA_REPOSITORY_PROTOCOL ${ATINA_REPOSITORY_PROTOCOL} 
ENV ATINA_REPOSITORY_URL ${ATINA_REPOSITORY_URL} 
ENV CUSTOMER_REPOSITORY_PROTOCOL ${CUSTOMER_REPOSITORY_PROTOCOL} 
ENV CUSTOMER_REPOSITORY_URL ${CUSTOMER_REPOSITORY_URL} 
#
# ====================================
# JDE ATINA MICRO SERVER
# ====================================
#
ARG JDE_MICROSERVER_IP=${JDE_MICROSERVER_IP} 
ARG JDE_MICROSERVER_PORT=${JDE_MICROSERVER_PORT} 
ARG JDE_MICROSERVER_CODE=${JDE_MICROSERVER_CODE}
ARG JDE_LIB_WRAPPED_VERSION=${JDE_LIB_WRAPPED_VERSION}
ARG STD_WEB_SERVICE_VERSION=${STD_WEB_SERVICE_VERSION}
ARG JDE_MICROSERVER_ENTERPRISE_SERVER_NAME=${JDE_MICROSERVER_ENTERPRISE_SERVER_NAME}
ARG JDE_MICROSERVER_ENTERPRISE_SERVER_IP=${JDE_MICROSERVER_ENTERPRISE_SERVER_IP}
ARG JDE_MICROSERVER_ENTERPRISE_DB_NAME=${JDE_MICROSERVER_ENTERPRISE_DB_NAME}
ARG JDE_MICROSERVER_ENTERPRISE_DB_IP=${JDE_MICROSERVER_ENTERPRISE_DB_IP}
ARG JDE_MICROSERVER_MOCKING=${JDE_MICROSERVER_MOCKING} 
#
ENV JDE_MICROSERVER_IP ${JDE_MICROSERVER_IP} 
ENV JDE_MICROSERVER_PORT ${JDE_MICROSERVER_PORT} 
ENV JDE_MICROSERVER_CODE ${JDE_MICROSERVER_CODE}
ENV JDE_LIB_WRAPPED_VERSION ${JDE_LIB_WRAPPED_VERSION}
ENV STD_WEB_SERVICE_VERSION ${STD_WEB_SERVICE_VERSION}
ENV JDE_MICROSERVER_ENTERPRISE_SERVER_NAME ${JDE_MICROSERVER_ENTERPRISE_SERVER_NAME}
ENV JDE_MICROSERVER_ENTERPRISE_SERVER_IP ${JDE_MICROSERVER_ENTERPRISE_SERVER_IP}
ENV JDE_MICROSERVER_ENTERPRISE_DB_NAME ${JDE_MICROSERVER_ENTERPRISE_DB_NAME}
ENV JDE_MICROSERVER_ENTERPRISE_DB_IP ${JDE_MICROSERVER_ENTERPRISE_DB_IP}
ENV JDE_MICROSERVER_MOCKING ${JDE_MICROSERVER_MOCKING}
#
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
RUN apt-get install -y curl
#
# ===================================================
# Instalacion de Java OpenJDK Modificado 64 Bits JDK
# ===================================================
RUN mkdir ${javaFolder}
RUN cd ${javaFolder} 
COPY java64/${javaJDKImage} ${javaFolder}/
RUN tar -xvjf ${javaFolder}/${javaJDKImage} -C ${javaFolder}
RUN /usr/bin/update-alternatives --install /usr/bin/java java ${javaFolder}/${javaJDKRootFolder}/bin/java 1
RUN rm ${javaFolder}/${javaJDKImage} 
  
# ====================================
# Creacion de Variable LOCALIP
# ====================================
RUN LOCALIP=$(hostname  -I) 
#
# ====================================
# Create MAVEN
# ====================================
ARG MAVEN_VERSION=3.6.3
ARG USER_HOME_DIR="/root"
ARG SHA=c35a1803a6e70a126e80b2b3ae33eed961f83ed74d18fcd16909b2d44d7dada3203f1ffe726c17ef8dcca2dcaa9fca676987befeadc9b9f759967a8cb77181c0
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries
RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
COPY images/settings-docker.xml /usr/share/maven/ref/
  
# ====================================
# Instalacion de JDE Atina Microserver
# ====================================
#
RUN mkdir /tmp/jde
RUN mkdir /tmp/jde/config
RUN mkdir /tmp/jde/mocking
#
# ====================================
# Instalacion de JDE Atina Microserver
# ====================================

RUN mkdir /var/jdeatinaserver
EXPOSE $SWAGGER_SERVER_PORT 

# ====================================
# Copiar Claves de Licencia	
# ==================================== 
COPY images/jdelicense.dat /var/jdeatinaserver/

# ====================================
# Copiar Metadata for Mocking
# ==================================== 
COPY images/mocking/*.json /tmp/jde/mocking/

# ====================================
# Start
# ====================================

COPY docker-entrypoint.sh /
COPY entypoints/* /docker-entrypoint.d/
RUN chmod +x /docker-entrypoint.sh
RUN chmod +x /docker-entrypoint.d/jde-atina-service.sh
ENTRYPOINT ["/docker-entrypoint.sh"]