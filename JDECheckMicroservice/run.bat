
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
LOGOUT WITH Session Id (LogoutWithSessionID)
------------------------------------------------------

Checkear si aun esta conectado

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -s localhost -p 8085 -m IsConnectedWithSessionId -i 94311738

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -s localhost -p 8085 -m LogoutWithSessionID -i 385635919

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -s localhost -p 8085 -m ParseToken -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NzYwMDY3LCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiIiwicGFzc3dvcmQiOiIiLCJlbnZpcm9ubWVudCI6IiIsInJvbGUiOiIiLCJzZXNzaW9uSWQiOjAsImV4cCI6MTYzNzc2NDg2N30.lc1SUeGj8oOvZsinKsK0alTkBWfeRohqItq9xNrjpz4


------------------------------------------------------
LOGIN WITH TOKEN (LoginWithToken)
------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m CreateToken -s localhost -p 8085 -u JDE -w Modus2020! -e JDV920 -r *ALL

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m ParseToken -s localhost -p 8085  -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NzY0NDU1LCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiJNb2R1czIwMjAhIiwiZW52aXJvbm1lbnQiOiJKRFY5MjAiLCJyb2xlIjoiKkFMTCIsInNlc3Npb25JZCI6MCwiZXhwIjoxNjM3NzY5MjU1fQ.kY126wyjEL0lk1q-9k9kMuPmzbaMmwvPdRKLIszRBo0

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m LoginWithToken -s localhost -p 8085  -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NzY0NDU1LCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiJNb2R1czIwMjAhIiwiZW52aXJvbm1lbnQiOiJKRFY5MjAiLCJyb2xlIjoiKkFMTCIsInNlc3Npb25JZCI6MCwiZXhwIjoxNjM3NzY5MjU1fQ.kY126wyjEL0lk1q-9k9kMuPmzbaMmwvPdRKLIszRBo0

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m IsConnectedWithToken -s localhost -p 8085  -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NzY0ODYxLCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiJNb2R1czIwMjAhIiwiZW52aXJvbm1lbnQiOiJKRFY5MjAiLCJyb2xlIjoiKkFMTCIsInNlc3Npb25JZCI6LTk2NTY5NjgxOSwiZXhwIjoxNjM3NzY5NjYxfQ.AC_bWQ9GphaHA62E9tKEqObU38DWoM9U-oOWPGhMPMM


------------------------------------------------------
LOGOUT WITH Token (LogoutWithSessionID)
------------------------------------------------------

Checkear si aun esta conectado
 
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -s localhost -p 8085 -m LogoutWithToken -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NzY0ODYxLCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiJNb2R1czIwMjAhIiwiZW52aXJvbm1lbnQiOiJKRFY5MjAiLCJyb2xlIjoiKkFMTCIsInNlc3Npb25JZCI6LTk2NTY5NjgxOSwiZXhwIjoxNjM3NzY5NjYxfQ.AC_bWQ9GphaHA62E9tKEqObU38DWoM9U-oOWPGhMPMM

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -s localhost -p 8085 -m ParseToken -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NzY4NTUwLCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiJNb2R1czIwMjAhIiwiZW52aXJvbm1lbnQiOiJKRFY5MjAiLCJyb2xlIjoiKkFMTCIsInNlc3Npb25JZCI6NDk2NDkxMDM1LCJleHAiOjE2Mzc3NzMzNTB9.CL3xzRV815RcsR6tb-hehMKCV7Uqn8wGtp2ihu6uUyA


------------------------------------------------------
Show Options
------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m ShowHidden 

-----------------------------------------------------------
Test WS USado para verificar que funcione el microservicio
-----------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s localhost -p 8085 -m TestGetAddressBookWSWithSessionId -a 28 -i 0

-----------------------------------------------------------
Get Logs
-----------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -s localhost -p 8085 -m GetLog -t  20211124115453

-----------------------------------------------------------
Get Metadata Operations
-----------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m GetMetadataOperations -s localhost -p 8085 -u JDE  -w Modus2020! -e JDV920 -r *ALL
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m GetMetadataOperations -s localhost -p 8085 -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NzY3MTU2LCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiJNb2R1czIwMjAhIiwiZW52aXJvbm1lbnQiOiJKRFY5MjAiLCJyb2xlIjoiKkFMTCIsInNlc3Npb25JZCI6MTY4MjE3NDkxMywiZXhwIjoxNjM3NzcxOTU2fQ.0OP5rJYoqnXRmZ5JKgdNXGWsMaDYW7uhFY9HKyTjZ1A

-----------------------------------------------------------
Get Metadata WS
-----------------------------------------------------------
 
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m GetMetadataWS -s localhost -p 8085 -u JDE  -w Modus2020! -e JDV920 -r *ALL -o "oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook"
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m GetMetadataWS -s localhost -p 8085 -o "oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook" -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NzY4MTg2LCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiJNb2R1czIwMjAhIiwiZW52aXJvbm1lbnQiOiJKRFY5MjAiLCJyb2xlIjoiKkFMTCIsInNlc3Npb25JZCI6LTE2MDM0MjM0MjN9.ePaqAQmvzoybFBvGjO1F6_bMkW-LFTJAvgi_uj2UK1Q

-----------------------------------------------------------
Get JSON Schema
-----------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m GetJsonWS -s localhost -p 8085 -u JDE -w Modus2020! -e JDV920 -r *ALL   -o "oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook"
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m GetJsonWS -s localhost -p 8085 -u JDE -w Modus2020! -e JDV920 -r *ALL   -o "oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook"



java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -u JDE -w Modus2020! -e JDV920 -r *ALL -s localhost -p 8085 -m GetMetadataWS -o "oracle.e1.bssv.JP430000.ProcurementManager.processPurchaseOrder"

oracle.e1.bssv.JP430000.ProcurementManager.processPurchaseOrder


The Atina JDE Web Service Connector provides access to all JDE web services pubbwith Oracle’s JD Edwards EnterpriseOne consuming all JDE Web Services published

The JDE Connector can be dropped into any Mule flow allowing you to easily integrate your JDE messages with all of the APIs, SaaS, Enterprise, and Legacy systems supported by the Anypoint Platform. The connector provides robust support for the entire range of JDE services including Financial Management, Project Management, Asset Life Cycle, Order Management, Manufacturing, Mobile, and Reporting solutions.


The Atina JDE Web Service Connector CE provides interoperability with Oracle’s JD Edwards EnterpriseOne™ allowing consume all JDE Web Services published

. The JDE Connector can be dropped into any Mule flow allowing you to easily integrate your JDE messages with all of the APIs, SaaS, Enterprise, and Legacy systems supported by the Anypoint Platform. The connector provides robust support for the entire range of JDE services including Financial Management, Project Management, Asset Life Cycle, Order Management, Manufacturing, Mobile, and Reporting solutions.



*************************************
LOGIN
*************************************

Input: 

  TransactionID:  Utilizado para poder capturar los logs
  User: Si no se informa Token
  Password: Si no se informa Token
  Environment: Si no se informa Token
  Role: Si no se informa Token
  Session ID: Utilizada para verificar si existe una sesion previa.
  Token: Si se desea ocultar credenciales y Session ID

Si se informa session ID se verifica que exista en el pool de conexiones y que aun la sesion este activa.
Si la sesion no esta activa, realiza un re-login.
Antes de realizar el login, verifica que no haya otra sesion abierta con las mismas credenciales.

Output:

  Session ID: 
  Token:      Con las credenciales y sesion guardadas.


*************************************
LOGOUT
*************************************

Input: 

  Session ID: Utilizada para verificar si existe una sesion previa.
  Token: Si se desea ocultar credenciales y Session ID
 
Toma el sesion ID y se desconecta. Luego borra la sesion del pool de conexiones.

Output:

  Session ID: 0
  Token:      Con las credenciales y sesion Id en cero
































