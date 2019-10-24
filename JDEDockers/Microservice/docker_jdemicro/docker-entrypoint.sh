#!/bin/bash
DIR=/docker-entrypoint.d   
cd /docker-entrypoint.d
./jde-atina-service.sh
exec "$@"