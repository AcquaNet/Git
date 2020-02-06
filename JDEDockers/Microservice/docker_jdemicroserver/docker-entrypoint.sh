#!/bin/bash
DIR=/docker-entrypoint.d   
cd /docker-entrypoint.d
./mvn-entrypoint.sh
./jde-atina-service.sh
exec "$@"