FROM busybox:ubuntu-14.04
MAINTAINER Werner Beroux <werner@beroux.com>
# https://github.com/rcgcoder/docker-ngrok/blob/master/Dockerfile
# Install ngrok (latest official stable from https://ngrok.com/download).
ADD https://bin.equinox.io/c/4VmDzA7iaHb/ngrok-stable-linux-amd64.zip /ngrok.zip
#COPY images/ngrok-stable-linux-amd64.zip /ngrok.zip
RUN set -x \
 && unzip -o /ngrok.zip -d /bin \
 && rm -f /ngrok.zip 

# Add config script.
COPY ngrok.yml /home/ngrok/.ngrok2/
COPY ngrok.yml /opt/ngrok/conf/ngrok.yml
COPY entrypoint.sh /

# Create non-root user.
RUN set -x \
 && echo 'ngrok:x:6737:6737:Ngrok user:/home/ngrok:/bin/false' >> /etc/passwd \
 && echo 'ngrok:x:6737:' >> /etc/group \
 && chown -R ngrok:ngrok /home/ngrok \
 && chmod -R go=u,go-w /home/ngrok \
 && chmod go= /home/ngrok

USER ngrok

ARG NGROK_NGROK_TOKEN=1PCF9faaexWLy1K8gB5lVv4gzUB_B7QY8aX89BXtiKMWbXZ2
ARG NGROK_MULE_SERVER_HOST=172.25.0.5
ARG NGROK_MULE_SERVER_PORT=8083
ENV NGROK_NGROK_TOKEN ${NGROK_NGROK_TOKEN}
ENV NGROK_MULE_SERVER_HOST ${NGROK_MULE_SERVER_HOST}
ENV NGROK_MULE_SERVER_PORT ${NGROK_MULE_SERVER_PORT}

RUN echo "                                       NGROK_NGROK_TOKEN" $NGROK_NGROK_TOKEN 
RUN echo "                                       NGROK_MULE_SERVER_HOST" $NGROK_MULE_SERVER_HOST
RUN echo "                                       NGROK_MULE_SERVER_PORT" $NGROK_MULE_SERVER_PORT

COPY ngrok.yml /home/ngrok/.ngrok2/ngrok.yml

EXPOSE 4040
 
WORKDIR /opt/ngrok/conf
RUN chmod +x /entrypoint.sh 
ENTRYPOINT ["/entrypoint.sh"]