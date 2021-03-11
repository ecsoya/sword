#!/bin/bash
echo "==============="
echo "DEPLOY sword/app"
echo "==============="

VERSION=$1

echo "Build Version:" ${VERSION}

echo "1. Build sword/app docker image."
# -t 表示指定镜像仓库名称/镜像名称:镜像标签 .表示使用当前目录下的Dockerfile
docker build -t sword/app:${VERSION} /home/sword/app

echo "2. Run app containers"
echo "   remove:"
docker stop sword-app || true && docker rm sword-app || true
echo "   create:"
docker run --restart=always -it -d -p 8063:8063 -v /home/sword/share:/sword -v /home/sword/app/logs:/logs -v /etc/localtime:/etc/localtime -e TZ='GMT+08' --link redis:redis --name sword-app sword/app:${VERSION}

docker ps -a

echo "==============="
echo "DEPLOY FINISHED"
echo "==============="

