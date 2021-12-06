#!/bin/bash
echo '-CREATING INI FILES--------------------------------------------'
echo "   Envirmonment: " ${JD_E1M_URL}
cd  /usr/app
java -Xmx2G -Djava.io.tmpdir=/tmp -jar jd-create-ini-files-1.0.0-jar-with-dependencies.jar -e ${JD_ENVIRONMENT} -u ${JD_USER} -p ${JD_PWD} -s ${JD_E1M_URL}
