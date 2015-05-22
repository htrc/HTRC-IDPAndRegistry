### In wso2is.sh (make sure this file is chmod +x):
#!/bin/sh
# `/sbin/setuser wso2is` runs the given command as the user `wso2is`.
# If you omit that part, the command will be run as root.
exec /sbin/setuser wso2is /usr/bin/memcached 

### In Dockerfile:
RUN mkdir /etc/service/wso2is
ADD wso2is.sh /etc/service/wso2is/run