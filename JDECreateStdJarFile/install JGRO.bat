xcopy C:\Users\jgodi\.m2\repository\com\jdedwards\jde-lib-wrapped\1.0.0\jde-lib-wrapped-1.0.0.jar C:\tmp
mvn deploy:deploy-file -DgroupId=com.jdedwards -DartifactId=jde-lib-wrapped -Dversion=1.0.0 -DrepositoryId=acquanet-central -Dpackaging=jar -Dfile=C:\tmp\jde-lib-wrapped-1.0.0.jar -DgeneratePom=true -Durl=http://157.245.236.175:8081/artifactory/libs-release
xcopy C:\Users\jgodi\.m2\repository\com\jdedwards\StdWebService\1.0.0\StdWebService-1.0.0.jar C:\tmp
mvn deploy:deploy-file -DgroupId=com.jdedwards -DartifactId=StdWebService -Dversion=1.0.0 -DrepositoryId=acquanet-central -Dpackaging=jar -Dfile=C:\tmp\StdWebService-1.0.0.jar -DgeneratePom=true -Durl=http://157.245.236.175:8081/artifactory/libs-release
