# Base image
FROM htrc/java7:alpine

MAINTAINER Data to Insight Center <d2i@indiana.edu>

COPY distribution/target/wso2is/ /opt/wso2is/
WORKDIR /opt/wso2is/

EXPOSE 9443

# Use baseimage-docker's init system.
CMD ["dumb-init", "./bin/wso2server.sh"]
