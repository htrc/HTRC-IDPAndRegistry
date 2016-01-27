#!/bin/sh

# If IDP configuration is available through a Docker volume, remove the default configuration
# and replace it with the configuration supplied by the volume.
if [ -d "$IDP_CONF_DIR" ]; then
  rm -r $IDP_ROOT/repository/conf
  ln -s $IDP_CONF_DIR $IDP_ROOT/repository/conf
fi

exec $IDP_ROOT/bin/wso2server.sh
