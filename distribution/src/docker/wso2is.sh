#!/bin/sh

if [ -d "$IDP_CONF_DIR" ]; then
  rm -r $IDP_ROOT/repository/conf
  ln -s $IDP_CONF_DIR $IDP_ROOT/repository/conf
fi

exec $IDP_ROOT/bin/wso2server.sh
