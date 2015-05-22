#
# HTRC-IDPAndRegistry Dockerfile
#
# https://github.com/htrc/HTRC-IDPAndRegistry
#

# Pull base image.
FROM java:7

MAINTAINER Data to Insight Center <d2i@indiana.edu>

# Install maven
RUN apt-get update
RUN apt-get install -y maven

# Setting the working directory
WORKDIR /code

# Copying the code
ADD pom.xml /code/pom.xml
ADD useradmin-ext /code/useradmin-ext
ADD distribution /code/distribution
ADD patches /code/patches

# Prepare by downloading dependencies
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

# Compile and package into a zip
RUN ["mvn", "package"]

# unzip the HTRC-IDPAndRegistry to /opt

WORKDIR /opt



EXPOSE 9443


