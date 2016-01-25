# Base image
FROM htrc/oracle-java7

RUN yum -y install unzip

WORKDIR /opt

ENV IDP_VERSION 1.1.0-SNAPSHOT
ENV IDP_ROOT /opt/htrc-idpandregistry-${IDP_VERSION}
ENV IDP_DEFAULT_CONF ${IDP_ROOT}/repository/conf
ENV IDP_ARCHIVE htrc-idpandregistry-${IDP_VERSION}.zip

ADD ${IDP_ARCHIVE} /opt/
RUN unzip ${IDP_ARCHIVE}

ADD idp.sh /opt/

# this is required because current maven build change file permission during wso2is repackaging.
RUN chmod +x ${IDP_ROOT}/bin/*.sh
RUN chmod +x /opt/idp.sh

EXPOSE 9443

CMD ["/opt/idp.sh"]





