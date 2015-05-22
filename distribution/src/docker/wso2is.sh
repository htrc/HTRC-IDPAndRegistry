### In wso2is.sh (make sure this file is chmod +x):
#!/bin/sh
# `/sbin/setuser wso2is` runs the given command as the user `wso2is`.
# If you omit that part, the command will be run as root.
exec /sbin/setuser wso2is /etc/profile.d/java-env.sh
exec /sbin/setuser wso2is /opt/htrc-idpandregistry-1.1.0-SNAPSHOT/bin/wso2server.sh start 