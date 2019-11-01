#!/bin/bash
DIR=/docker-entrypoint.d   
cd /docker-entrypoint.d
./activemq.sh
./mule-start.sh
exec "$@"