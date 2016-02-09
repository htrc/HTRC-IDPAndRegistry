# Base image
FROM htrc/oracle-java7

WORKDIR /opt

ENV IDP_VERSION 1.1.0-SNAPSHOT
ENV IDP_ARCHIVE htrc-idpandregistry-$IDP_VERSION.zip
ENV IDP_ROOT /opt/htrc-idpandregistry-$IDP_VERSION
ENV IDP_CONF_DIR /htrc/conf/idp

ADD $IDP_ARCHIVE /opt/
RUN unzip $IDP_ARCHIVE

# this is required because current maven build change file permission during wso2is repackaging.
RUN chmod +x $IDP_ROOT/bin/*.sh

RUN mkdir -p /usr/local/bin
ADD wso2is.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/wso2is.sh

EXPOSE 9443

# Use baseimage-docker's init system.
CMD ["dumb-init", "/usr/local/bin/wso2is.sh"]
