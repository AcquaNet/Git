FROM ubuntu:16.04
MAINTAINER javier.godino@acqua.net.ar
# ====================================
# ARGUMENTOS DE CONSTRUCCION
# ====================================
# ------------------------------------------------------------------
# JDE Atina Version
# ------------------------------------------------------------------
#
ARG jdeAtinaVersion=1.0.0
ENV mirritVersion ${jdeAtinaVersion}

# ------------------------------------------------------------------
# JRE 64 Bits
# ------------------------------------------------------------------
# JRE 64
ARG javaFolder=/var/java
ARG javaImage=j2re-image-x86_64-u232-b01-20190729.tar.bz2
ARG javaRootFolder=j2re-image
ENV javaFolder ${javaFolder}
ENV javaRootFolder ${javaRootFolder} 
#
# ====================================
# Mule
# ====================================
# 
ARG muleVersion=3.9.0 
#
# ====================================
# JAVA
# ====================================
ENV JAVA_HOME ${javaFolder}/j2re-image
#
# ====================================
# MULE SERVER
# ====================================
ENV MULE_HOME /opt/mule/mule-standalone-${muleVersion}
ENV muleVersion ${muleVersion}
 
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
RUN apt-get install -y jq
RUN apt-get install -y curl

# ====================================
# Instalacion GIT
# ====================================
RUN apt-get install -y git
#
# ====================================
# Instalacion de Java Original
# ====================================
#RUN apt-get install -y openjdk-8-jre
RUN apt-get install -y openjdk-8-jdk
# ====================================
# Instalacion ActiveMQ
# ====================================
# Application settings
#
ENV CONFD_PREFIX_KEY="/activemq" \
    CONFD_BACKEND="env" \
    CONFD_INTERVAL="60" \
    CONFD_NODES="" \
    S6_BEHAVIOUR_IF_STAGE2_FAILS=2 \
    LANG="en_US.utf8" \
    APP_HOME="/opt/activemq" \
    APP_VERSION="5.15.3" \
    SCHEDULER_VOLUME="/opt/scheduler" \
    USER=activemq \
    GROUP=activemq \
    UID=10003 \
    GID=10003
ARG activemqImage=apache-activemq-${APP_VERSION}-bin.tar.gz
RUN mkdir -p ${APP_HOME} /data /var/log/activemq
RUN cd ${APP_HOME}
RUN wget http://archive.apache.org/dist/activemq/${APP_VERSION}/apache-activemq-${APP_VERSION}-bin.tar.gz
RUN tar xzvf ${activemqImage}
RUN mv apache-activemq-${APP_VERSION}/* ${APP_HOME}/
RUN addgroup --quiet --system activemq
RUN adduser --quiet --system --ingroup activemq --no-create-home --disabled-password activemq
RUN usermod -c "ActiveMQ Broker" -d /opt/activemq -g activemq activemq
RUN chown -R activemq:activemq /opt/activemq
RUN chmod u=rwx,g=rxs,o= /opt/activemq
RUN rm apache-activemq-${APP_VERSION}-bin.tar.gz
RUN rm -r apache-activemq-${APP_VERSION}

# ====================================
# Install MAVEN
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
#	
# ====================================
# Instalacion de Mule Server
# ====================================
# 
RUN mkdir /opt/mule
RUN cd /opt/mule
COPY images/mule-standalone-${muleVersion}.tar.gz /opt/mule/
RUN tar xvzf /opt/mule/mule-standalone-${muleVersion}.tar.gz -C /opt/mule  
VOLUME ["/opt/mule/mule-standalone-${muleVersion}/logs", "/opt/mule/mule-standalone-${muleVersion}/apps"]
COPY images/wrapper.conf /opt/mule/mule-standalone-${muleVersion}/conf
RUN rm /opt/mule/mule-standalone-${muleVersion}.tar.gz
RUN date +%Y%m%d%H%M%S >/opt/mule/build-date.txt
  
# ==========================================
# Configuracion de Variables de la App Mule
# ==========================================
#
ENV mule_env=dev 
#
# ------------------------------------------------
# JDE User
# ------------------------------------------------
#
ARG jde_user=${JDE_USER}
ARG jde_password=${JDE_PASSWORD}
ARG jde_environment=${JDE_ENVIRONMENT}
ARG jde_role=${JDE_ROLE}
#
ENV jde_user ${JDE_USER}
ENV jde_password ${JDE_PASSWORD}
ENV jde_environment ${JDE_ENVIRONMENT}
ENV jde_role ${JDE_ROLE}
#
# ------------------------------------------------
# SMTP
# ------------------------------------------------
#
ARG dragon_shopify_smtp_host=${dragon_shopify_smtp_host}
ARG dragon_shopify_smtp_port=${dragon_shopify_smtp_port}
ARG dragon_shopify_smtp_user=${dragon_shopify_smtp_user}
ARG dragon_shopify_smtp_pass=${dragon_shopify_smtp_pass}
ARG dragon_shopify_smtp_email_from=${dragon_shopify_smtp_email_from}
ARG dragon_shopify_smtp_email_to=${dragon_shopify_smtp_email_to}
ARG dragon_shopify_smtp_email_errorSubject=${dragon_shopify_smtp_email_errorSubject}
ARG dragon_shopify_smtp_email_msgSubject=${dragon_shopify_smtp_email_msgSubject}
#
ENV dragon_shopify_smtp_host ${dragon_shopify_smtp_host}
ENV dragon_shopify_smtp_port ${dragon_shopify_smtp_port}
ENV dragon_shopify_smtp_user ${dragon_shopify_smtp_user}
ENV dragon_shopify_smtp_pass ${dragon_shopify_smtp_pass}
ENV dragon_shopify_smtp_email_from ${dragon_shopify_smtp_email_from}
ENV dragon_shopify_smtp_email_to ${dragon_shopify_smtp_email_to}
ENV dragon_shopify_smtp_email_errorSubject ${dragon_shopify_smtp_email_errorSubject}
ENV dragon_shopify_smtp_email_msgSubject ${dragon_shopify_smtp_email_msgSubject}
#
# Atina Microservice
ARG jde_atina_server_name=${JDE_ATINA_SERVER_NAME}
ARG jde_atina_server_port=${JDE_ATINA_SERVER_PORT}
#
ENV jde_atina_server_name=${JDE_ATINA_SERVER_NAME}
ENV jde_atina_server_port=${JDE_ATINA_SERVER_PORT}
#
#Active MQ 
#
ENV activemq_api_jolokia_Host localhost
ENV activemq_api_jolokia_Port 8161
ENV activemq_api_jolokia_BasePath /api/jolokia/
ENV activemq.queue.user admin
ENV activemq.queue.pass admin
#
# Metrics Console
#
ENV metrics_reporter_interval 30
ENV metrics_console_enable Y
#
#
# Metrics Graphite
#
ARG metrics_graphite_enable=Y
ARG metrics_graphite_server=graphite
ARG metrics_graphite_port=2003
ARG metrics_graphite_applicaction=JDEAtina
ARG metrics_graphite_environment=dev
ARG metrics_graphite_prefix=jde
ENV metrics_graphite_enable ${metrics_graphite_enable}
ENV metrics_graphite_server ${metrics_graphite_server}
ENV metrics_graphite_port ${metrics_graphite_port}
ENV metrics_graphite_applicaction ${metrics_graphite_applicaction}
ENV metrics_graphite_environment ${metrics_graphite_environment}
ENV metrics_graphite_prefix ${metrics_graphite_prefix}
#
# Este parametro es el IP del ngrok server.
# Usado por el Entrypoint para invocar al API Ngron y recuperar el 
# la url que genero ngrok para construir el webhook 
# Genera la variable Shopify_Webhooks_url con ese resultado
ARG Shopify_ngrok_server_ip=${NGROK_SERVER}
ENV Shopify_ngrok_server_ip ${NGROK_SERVER}
#
# ====================================
# Start
# ====================================
#
COPY docker-entrypoint.sh /
COPY docker-entrypoint-stop.sh /
COPY entypoints/* /docker-entrypoint.d/
RUN chmod +x /docker-entrypoint.sh 
RUN chmod +x /docker-entrypoint-stop.sh 
RUN chmod +x /docker-entrypoint.d/mule-start.sh
RUN chmod +x /docker-entrypoint.d/mule-stop.sh
RUN chmod +x /docker-entrypoint.d/activemq.sh 
ENTRYPOINT ["./docker-entrypoint.sh"]
