cd C:\_work\JDEConnectorCE\Projects\JDEConnectorService
cmd /k "mvn clean install"
cd C:\_work\JDEConnectorCE\Projects\JDEConnectorServer
cmd /k "mvn clean install"
del C:\Users\jgodi\.m2\repository\com\jdedwards\JDEConnectorServer\1.0.0\JDEConnectorServer-1.0.0.jar
copy C:\Users\jgodi\.m2\repository\com\jdedwards\JDEConnectorServer\1.0.0\JDEConnectorServer-1.0.0-jar-with-dependencies.jar C:\Users\jgodi\.m2\repository\com\jdedwards\JDEConnectorServer\1.0.0\JDEConnectorServer-1.0.0.jar
cd C:\_work\JDEConnectorCE\Projects\JDEAtinaServer
cmd /k "mvn clean install"
copy  C:\Users\jgodi\.m2\repository\com\jdedwards\JDEAtinaServer\1.0.0\JDEAtinaServer-1.0.0.jar C:\_work\JDEConnectorCE\Projects\JDEDockers\Microservice\docker_jdemicro\images
pause
cd C:\_work\JDEConnectorCE\Projects\JDEDockers\Microservice
cmd /k "docker-compose up --no-start"
cmd /k "docker-compose start"
pause