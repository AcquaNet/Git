java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m TestLoggindAndGetAddressBookWS -a 28
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m TestGetMetadataWSAddressBook
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m TestLogindWS
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -s 192.168.99.100 -p 8077 -m TestIsConnectedWS -i 614076411
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -s 192.168.99.100 -p 8077 -m TestLogoutWS-i 614076411
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m ShowHidden 
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m TestGetAddressBookWSWithSessionId -a 28 -i 0
