
OPCIONES:

Permite Logearse, invocar a un WS y deslogearse.

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m TestLoggindAndGetAddressBookWS -a 28

Se logea en JDE y devuelve la sesion creada. El microservicio mantiene el token y la sesión. 

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m TestLogindWS
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -s 192.168.99.100 -p 8077 -m TestIsConnectedWS -i 614076411
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -s 192.168.99.100 -p 8077 -m TestLogoutWS-i 614076411

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m ShowHidden 

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m TestGetAddressBookWSWithSessionId -a 28 -i 0

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -s 192.168.99.100 -p 8077 -m GetLog -t 20211120140212

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m CreateToken
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -s 192.168.99.100 -p 8077 -m ParseToken -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NDM0MzI2LCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiIiLCJlbnZpcm9ubWVudCI6IkpEVjkyMCIsInJvbGUiOiIqQUxMIiwiZXhwIjoxNjM3NDM5MTI2fQ.miUdS6BjrlkkeBcv1fe1Z5un1udg6AXt_2hg6wKhBmw

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m GetMetadataOperations
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m GetMetadataWS -o "oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook"
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m GetJsonWS -o "oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook"

