#!/bin/bash
echo "==============="
echo "DEPLOY Nginx"
echo "==============="
echo "   remove:"
docker stop nginx || true && docker rm nginx || true
echo "   create:"
#docker run --restart=always -it -d -m 2g -p 80:80 -p 443:443 -v /home/nginx/nginx.conf:/etc/nginx/nginx.conf -v /home/nginx/key:/etc/nginx/key -v /home/nginx/logs:/var/log/nginx -v /etc/localtime:/etc/localtime --link admin1:admin1 --link admin2:admin2 --link app1:app1 --link app2:app2 --name nginx nginx:latest 
docker run --restart=always -it -d -m 2g -p 80:80 -v /home/register:/register -v /home/nginx/nginx.conf:/etc/nginx/nginx.conf -v /home/nginx/logs:/var/log/nginx -v /etc/localtime:/etc/localtime --link admin1:admin1 --link admin2:admin2 --link app1:app1 --link app2:app2 --name nginx nginx:latest 

docker ps -a

echo "==============="
echo "DEPLOY FINISHED"
echo "==============="

