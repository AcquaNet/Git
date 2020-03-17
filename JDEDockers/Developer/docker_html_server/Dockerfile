#
# Nginx image with Consul Template
#
# https://www.free-css.com/free-css-templates/page208/simple-admin
#
FROM nginx:1.13-alpine
MAINTAINER 1science Devops Team <devops@1science.org>

ARG PROTO_SHOPIFY=https
ARG URL_SHOPIFY=acquait.myshopify.com
ARG NAME_SHOPIFY=Shopify
ARG PROTO_SHOPIFY_ADMIN=https
ARG URL_SHOPIFY_ADMIN=acquait.myshopify.com
ARG NAME_SHOPIFY_ADMIN='Shopify Backend'
ARG PROTO_MULE_CONSOLE=https
ARG URL_MULE_CONSOLE=localhost:8083/console
ARG NAME_MULE_CONSOLE='APIs Console'
ARG PROTO_METRICS_CONSOLE=http
ARG URL_METRICS_CONSOLE=localhost:8080
ARG NAME_METRICS_CONSOLE=Metrics
ARG SEC_METRICS_CONSOLE=http
ARG URL_SEC_CONSOLE=localhost:8080
ARG NAME_SEC_CONSOLE='Security Console'

ENV VAULT_IP ${VAULT_IP}
ENV VAULT_PORT ${VAULT_PORT}
ENV VAULT_PORT ${VAULT_USER}
ENV VAULT_PASSWORD ${VAULT_PASSWORD}

RUN apk update && apk upgrade && \
    apk add curl wget bash tree unzip less vim sed jq

# Consul template for configuration management
ENV S6_OVERLAY_VERSION=1.19.1.1 CONSUL_TEMPLATE_VERSION=0.18.2
  
# Install S6 Overlay and Consul Template
RUN curl -Ls https://github.com/just-containers/s6-overlay/releases/download/v${S6_OVERLAY_VERSION}/s6-overlay-amd64.tar.gz | tar -xz -C /
RUN curl -Ls https://releases.hashicorp.com/consul-template/${CONSUL_TEMPLATE_VERSION}/consul-template_${CONSUL_TEMPLATE_VERSION}_linux_amd64.zip -o consul-template.zip && \
    unzip consul-template.zip -d /usr/local/bin && \
    rm -f consul-template* && \
    echo -ne "- with `consul-template -v 2>&1`\n" >> /root/.built

# Add services configuration
ADD etc /etc
 
# Copiar HTML Files.
COPY source /home/html

# Copiar Valores Vault
COPY values.json /home/values.json

COPY entrypoint.sh /
RUN chmod +x /entrypoint.sh 
  
ENTRYPOINT ["/init","/entrypoint.sh"]
CMD ["nginx", "-g", "daemon off;"]