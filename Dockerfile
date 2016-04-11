# Base image
FROM anapsix/alpine-java:jdk7

MAINTAINER Data to Insight Center <d2i@indiana.edu>

COPY distribution/target/wso2is/ /opt/wso2is/
WORKDIR /opt/wso2is/

EXPOSE 9443

CMD ["/opt/wso2is/bin/wso2server.sh"]
