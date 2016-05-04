#!/bin/bash

imageName="joseoc/java-example-001"
#rev="$(date +%Y%m%d)"

# Exit immediately if a command exits with a non-zero status.
set -e
# when you run a script, it will show all actions done in that script. So all ifs, loops and commands run. Very useful for debugging.
set -x

base=$(readlink -f $(dirname $(readlink -f $0))/)
here=${PWD}


docker rmi ${imageName}:latest || true

cd ${base}/..
docker build --pull --no-cache -t ${imageName} .
# docker tag ${imageName} ${imageName}:latest
cd ${here}

rake spec

docker push ${imageName}
# docker push ${imageName}:latest

docker rmi ${imageName}:${rev}
# docker rmi ${imageName}:latest
