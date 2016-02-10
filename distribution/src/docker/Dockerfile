# Base image
FROM htrc/oracle-java7

WORKDIR /opt

ENV IDP_VERSION=${project.version} \
    IDP_ROOT="/opt/${project.build.finalName}" \
    IDP_CONF_DIR="/htrc/conf/idp"

ADD ${project.build.finalName}.zip /opt/
RUN unzip ${project.build.finalName}.zip

RUN mkdir -p /usr/local/bin
ADD wso2is.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/wso2is.sh

EXPOSE 9443

# Use baseimage-docker's init system.
CMD ["dumb-init", "/usr/local/bin/wso2is.sh"]
