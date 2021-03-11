#!/bin/bash
echo "==============="
echo "DEPLOY sword/admin"
echo "==============="

VERSION=$1

echo "Build Version:" ${VERSION}

echo "1. Build sword/admin docker image."
# -t 表示指定镜像仓库名称/镜像名称:镜像标签 .表示使用当前目录下的Dockerfile
docker build -t sword/admin:${VERSION} /home/sword/admin

echo "2. Run admin containers"
echo "   remove:"
docker stop sword-admin || true && docker rm sword-admin || true
echo "   create:"
docker run --restart=always -it -d -p 8061:8061 -v /home/sword/admin/logs:/logs -v /home/sword/share:/sword -v /etc/localtime:/etc/localtime -e TZ='GMT+08' --link redis:redis --name sword-admin sword/admin:${VERSION}

docker ps -a

echo "==============="
echo "DEPLOY FINISHED"
echo "==============="

