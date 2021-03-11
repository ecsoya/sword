#!/bin/bash
echo "==============="
echo "DEPLOY sword/admin"
echo "==============="

VERSION=$1

echo "Build Version:" ${VERSION}

rm -rf /home/sword/admin/logs/8082/*.log
rm -rf /home/sword/admin/logs/8083/*.log

echo "1. Build sword/admin docker image."
# -t 表示指定镜像仓库名称/镜像名称:镜像标签 .表示使用当前目录下的Dockerfile
docker build -t sword/admin:${VERSION} /home/sword/admin

echo "2. Run admin containers"
echo "- remove:"
docker stop admin1 || true && docker rm admin1 || true
echo "- create:"
docker run --restart=always -it -d -m 2g -p 8082:8061 -v /home/sword/admin/logs/8082:/logs -v /etc/localtime:/etc/localtime -e TZ='GMT+08' --name admin1 sword/admin:${VERSION}
#docker logs admin1

#sleep 1m

echo "- remove:"
docker stop admin2 || true && docker rm admin2 || true
echo "- create:"
docker run --restart=always -it -d -m 2g -p 8083:8061 -v /home/sword/admin/logs/8083:/logs -v /etc/localtime:/etc/localtime -e TZ='GMT+08' --name admin2 sword/admin:${VERSION}
#docker logs admin2

docker ps -a

echo "==============="
echo "DEPLOY FINISHED"
echo "==============="

