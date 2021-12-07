FROM openjdk:8-jdk
MAINTAINER fjgodino@gmail.com
#
# ==========================================
# MAVEN
# ==========================================
#
ARG MAVEN_VERSION=3.8.4
ARG USER_HOME_DIR="/root"
ARG SHA=a9b2d825eacf2e771ed5d6b0e01398589ac1bfa4171f36154d1b5787879605507802f699da6f7cfc80732a5282fd31b28e4cd6052338cbef0fa1358b48a5e3c8
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries
#
RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
#
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
#
# ====================================
# COPY MAVEN FILES
# ==================================== 
#
COPY mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY settings.xml /usr/share/maven/ref/
COPY settings.xml /usr/share/maven/ref/settings-docker.xml

RUN chmod +x /usr/local/bin/mvn-entrypoint.sh
ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]
#
# ====================================
# COPY APP
# ==================================== 
#
COPY /target/jd-create-jar-files-1.0.0-jar-with-dependencies.jar /usr/app/
# 
# ====================================
# Start
# ====================================
WORKDIR /usr/app/
#
# 0: -i jdeInstallPath /tmp/build_jde_libs/
# 1: -j jdbcDriver     /tmp/build_jde_libs/JDBC_Vendor_Drivers
# 2: -r localRepo      /root/.m2/repository/
# 3: -s settings       /tmp/build_jde_libs/settings.xml
# 4: -o version        1.0.0
#
#
ENTRYPOINT java -jar jd-create-jar-files-1.0.0-jar-with-dependencies.jar -r /root/.m2/repository



