
======================================
OPTIONS
======================================

------------------------------------------------------
Permite Logearse, invocar a un WS y deslogearse.
------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s localhost -p 8085 -m TestLoggindAndGetAddressBookWS -a 28

------------------------------------------------------
LOGIN WITH USER/PWD (LoginWithUserAndPassword)
------------------------------------------------------

Input: User Credential

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m LoginWithUserAndPassword -s localhost -p 8085 -u JDE -w Modus2020! -e JDV920 -r *ALL

Output: Token que contiene la sesion.

Ejecutar Parse Token para ver el detalle.
Debe corresponder al Session ID infromado.

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -s localhost -p 8085 -m ParseToken -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NzU2MzY5LCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiJNb2R1czIwMjAhIiwiZW52aXJvbm1lbnQiOiJKRFY5MjAiLCJyb2xlIjoiKkFMTCIsInNlc3Npb25JZCI6MTg5NjI2NjIwMSwiZXhwIjoxNjM3NzYxMTY5fQ.sMheTRywMR_0Bb6RoaeAMPx9cZlh9MdC_uSw6M2hMIM
 
------------------------------------------------------
LOGOUT WITH Session Id (LoginWithUserAndPassword)
------------------------------------------------------








Se logea en JDE y devuelve la sesion creada. El microservicio mantiene el token y la sesión. 


java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -s 192.168.99.100 -p 8077 -m TestIsConnectedWS -i 614076411
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -s 192.168.99.100 -p 8077 -m TestLogoutWS-i 614076411

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m ShowHidden 

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m TestGetAddressBookWSWithSessionId -a 28 -i 0

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -s 192.168.99.100 -p 8077 -m GetLog -t  20211123093130

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m CreateToken
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -s 192.168.99.100 -p 8077 -m ParseToken -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NDM0MzI2LCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiIiLCJlbnZpcm9ubWVudCI6IkpEVjkyMCIsInJvbGUiOiIqQUxMIiwiZXhwIjoxNjM3NDM5MTI2fQ.miUdS6BjrlkkeBcv1fe1Z5un1udg6AXt_2hg6wKhBmw

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m GetMetadataOperations
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m GetMetadataWS -o "oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook"
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s 192.168.99.100 -p 8077 -m GetJsonWS -o "oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook"



java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s localhost -p 8085 -m GetMetadataWS -o "oracle.e1.bssv.JP430000.ProcurementManager.processPurchaseOrder"

oracle.e1.bssv.JP430000.ProcurementManager.processPurchaseOrder


The Atina JDE Web Service Connector provides access to all JDE web services pubbwith Oracle’s JD Edwards EnterpriseOne consuming all JDE Web Services published

The JDE Connector can be dropped into any Mule flow allowing you to easily integrate your JDE messages with all of the APIs, SaaS, Enterprise, and Legacy systems supported by the Anypoint Platform. The connector provides robust support for the entire range of JDE services including Financial Management, Project Management, Asset Life Cycle, Order Management, Manufacturing, Mobile, and Reporting solutions.


The Atina JDE Web Service Connector CE provides interoperability with Oracle’s JD Edwards EnterpriseOne™ allowing consume all JDE Web Services published

. The JDE Connector can be dropped into any Mule flow allowing you to easily integrate your JDE messages with all of the APIs, SaaS, Enterprise, and Legacy systems supported by the Anypoint Platform. The connector provides robust support for the entire range of JDE services including Financial Management, Project Management, Asset Life Cycle, Order Management, Manufacturing, Mobile, and Reporting solutions.

