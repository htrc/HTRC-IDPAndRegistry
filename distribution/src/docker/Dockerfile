# Base image
FROM htrc/oracle-java7

# Use baseimage-docker's init system.
CMD ["/sbin/my_init"]

RUN useradd -ms /bin/bash wso2is

RUN apt-get install -y unzip

# Clean up APT when done.
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

WORKDIR /opt

ADD htrc-idpandregistry-1.1.0-SNAPSHOT.zip /opt/
RUN unzip htrc-idpandregistry-1.1.0-SNAPSHOT.zip

RUN mkdir /etc/service/wso2is
ADD wso2is.sh /etc/service/wso2is/run





