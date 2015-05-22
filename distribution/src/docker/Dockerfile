# Base image
FROM htrc/oracle-java7

# RUN useradd -ms /bin/bash wso2is

RUN apt-get update

RUN apt-get install -y unzip

# Clean up APT when done.
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

WORKDIR /opt

ADD htrc-idpandregistry-1.1.0-SNAPSHOT.zip /opt/
RUN unzip htrc-idpandregistry-1.1.0-SNAPSHOT.zip

# this is required because current maven build change file permission during wso2is repackaging.
RUN chmod +x /opt/htrc-idpandregistry-1.1.0-SNAPSHOT/bin/*.sh

RUN mkdir -p /etc/my_init.d
ADD wso2is.sh /etc/my_init.d/wso2is.sh
RUN chmod +x /etc/my_init.d/wso2is.sh

# TODO: Make htrc-idpandregistry run as a daemon.
#RUN mkdir /etc/service/wso2is
#ADD wso2is.sh /etc/service/wso2is/run


# Use baseimage-docker's init system.
CMD ["/sbin/my_init"]





