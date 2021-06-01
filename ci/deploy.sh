#!/bin/bash
set -e

image_name="fictio/higos"
container="higos"
current_path="$(cd `dirname $0`; pwd)"
log_path="${current_path}/logs"
conf_path="${current_path}/config"
port=9900

opt=$1
if [ $opt ]; then echo "opt is ${opt}"
else opt="run"
fi

act=$2
if [ $act ]; then echo "act is ${act}"
else act="dev"
fi

function remove()
{
  sudo docker rm -f ${container} || true
  sudo docker rmi -f ${image_name} || true
}

function buildImage()
{
  remove
  sudo docker build -t ${image_name} .
}

if [ $opt == "build" ];then
  buildImage
]
fi

if [ $opt == "run" ];then
  buildImage
  sudo docker run -d -p ${port}:${port} -e PROFILES_ACTIVE=${act} \
    -v $log_path:/logs -v $conf_path:/config \
    --restart=always --name ${container} ${image_name}
fi

