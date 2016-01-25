#!/bin/sh

if [ -d "/htrc/conf/idp" ]; then
    # mounted idp conf available
    rm -rf $IDP_ROOT/repository/conf
    ln -s /htrc/conf/idp $IDP_ROOT/repository/conf
fi

case $IDP_MODE in
    debug)
        sh $IDP_ROOT/bin/wso2server.sh debug
        ;;
    *)
        sh $IDP_ROOT/bin/wso2server.sh
        ;;
esac
