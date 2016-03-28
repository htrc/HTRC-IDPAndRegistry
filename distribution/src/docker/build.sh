#!/usr/bin/env bash

cp ../${project.build.finalName}.zip ./
docker build -t htrc/idp:${project.version} .
docker tag htrc/idp:${project.version} registry.docker.htrc.indiana.edu/htrc/idp:${project.version}
docker push registry.docker.htrc.indiana.edu/htrc/idp:${project.version}