@echo off 
@set jdebase=C:/_work/JDEConnectorCE/jdeJarsFile/JDETool_9_2_2_4
mvn deploy:deploy-file -Dfile=%jdebase%/ApplicationAPIs_JAR.jar -DgroupId=com.jdedwards -DartifactId=applicationAPIs -Dversion=9.2.2.4 -Dpackaging=jar -Durl=file:./maven-repository/ -DrepositoryId=maven-repository -DupdateReleaseInfo=true