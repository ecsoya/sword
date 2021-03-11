#!/bin/bash
echo "==============="
echo "DEPLOY sword/app"
echo "==============="

VERSION=$1

echo "Build Version:" ${VERSION}

rm -rf /home/sword/app/logs/8093/*.log
rm -rf /home/sword/app/logs/8092/*.log

echo "1. Build sword/app docker image."
# -t 表示指定镜像仓库名称/镜像名称:镜像标签 .表示使用当前目录下的Dockerfile
docker build -t sword/app:${VERSION} /home/sword/app

echo "2. Run app containers"
echo "- remove:"
docker stop app1 || true && docker rm app1 || true
echo "- create:"
docker run --restart=always -it -d -m 2g -p 8092:8063 -v /home/sword/share:/sword -v /home/sword/app/logs/8092:/logs -v /etc/localtime:/etc/localtime -e TZ='GMT+08' --name app1 sword/app:${VERSION}
#docker logs app1

#sleep 1m

echo "- remove:"
docker stop app2 || true && docker rm app2 || true
echo "- create:"
docker run --restart=always -it -d -m 2g -p 8093:8063 -v /home/sword/share:/sword -v /home/sword/app/logs/8093:/logs -v /etc/localtime:/etc/localtime -e TZ='GMT+08' --name app2 sword/app:${VERSION}
#docker logs app2

docker ps -a

echo "==============="
echo "DEPLOY FINISHED"
echo "==============="

