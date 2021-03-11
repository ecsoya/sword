#!/bin/bash
echo "==============="
echo "DEPLOY Nginx"
echo "==============="

echo "9. Recreate NGINX"
echo "   remove:"
docker stop nginx || true && docker rm nginx || true
echo "   create:"
docker run --restart=always -it -d -m 1g -p 80:80 -p 4431:443 -v /home/sword/web:/kc/web -v /home/sword/nginx/nginx.conf:/etc/nginx/nginx.conf -v /home/sword/nginx/logs:/var/log/nginx -v /etc/localtime:/etc/localtime --link sword-admin:admin --link sword-app:app --name nginx nginx:latest 

docker ps -a

echo "==============="
echo "DEPLOY FINISHED"
echo "==============="

