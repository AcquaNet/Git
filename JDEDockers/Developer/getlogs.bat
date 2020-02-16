del /s /q microserverlog
del /s /q mulelog
#docker exec -it jdeatina-microserver ls -l /tmp/jdelog
docker cp jdeatina-microserver:/tmp/jdelog microserverlog
docker cp jdeatina-microserver:/tmp/jde/JDEConnectorServerLog microserverlog
docker cp mule-server:/opt/mule/mule-standalone-3.9.0/logs mulelog
