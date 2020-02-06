docker stop html-server
docker rm html-server
docker rmi soporteacqua/html-server:1.0.0
docker build -f Dockerfile -t html-server .
pause