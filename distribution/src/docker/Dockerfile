# Base image
FROM htrc/oracle-java7

# Use baseimage-docker's init system.
CMD ["/sbin/my_init"]

WORKDIR /opt

ADD htrc-idpandregistry-1.1.0-SNAPSHOT.zip /opt
RUN unzip htrc-idpandregistry-1.1.0-SNAPSHOT.zip



