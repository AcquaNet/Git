
======================================
OPTIONS
======================================

------------------------------------------------------
Permite Logearse, invocar a un WS y deslogearse.
------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m TestLoggindAndGetAddressBookWS  -s localhost -p 8085 -u JDE -w Modus2020! -e JDV920 -r *ALL -a 28

------------------------------------------------------
LOGIN With Credential
------------------------------------------------------
  
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m Login -s localhost -p 8085 -u JDE -w Modus2020! -e JDV920 -r *ALL

------------------------------------------------------
PARSE TOKEN
------------------------------------------------------
 
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m ParseToken -s localhost -p 8085 -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NzU2MzY5LCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiJNb2R1czIwMjAhIiwiZW52aXJvbm1lbnQiOiJKRFY5MjAiLCJyb2xlIjoiKkFMTCIsInNlc3Npb25JZCI6MTg5NjI2NjIwMSwiZXhwIjoxNjM3NzYxMTY5fQ.sMheTRywMR_0Bb6RoaeAMPx9cZlh9MdC_uSw6M2hMIM

------------------------------------------------------
CHECK Is Connected
------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m IsConnected -s localhost -p 8085  -i 94311738

------------------------------------------------------
LOGOUT With Session Id
------------------------------------------------------
  
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m Logout -s localhost -p 8085  -i 385635919
 
------------------------------------------------------
LOGIN With Token
------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m CreateToken -s localhost -p 8085 -u JDE -w Modus2020! -e JDV920 -r *ALL
 
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m Login -s localhost -p 8085  -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3Nzc4MjIzLCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiJNb2R1czIwMjAhIiwiZW52aXJvbm1lbnQiOiJKRFY5MjAiLCJyb2xlIjoiKkFMTCIsInNlc3Npb25JZCI6MCwiZXhwIjoxNjM3NzgzMDIzfQ.bnk8qOGfs2t87QK8jjGMOi5LOUC4t7z7Yr42gmlo9YQ
 
------------------------------------------------------
LOGOUT With Token
------------------------------------------------------
  
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m Logout -s localhost -p 8085 -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NzY0ODYxLCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiJNb2R1czIwMjAhIiwiZW52aXJvbm1lbnQiOiJKRFY5MjAiLCJyb2xlIjoiKkFMTCIsInNlc3Npb25JZCI6LTk2NTY5NjgxOSwiZXhwIjoxNjM3NzY5NjYxfQ.AC_bWQ9GphaHA62E9tKEqObU38DWoM9U-oOWPGhMPMM
 
------------------------------------------------------
Show Options
------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m ShowHidden 

-----------------------------------------------------------
Test WS USado para verificar que funcione el microservicio
-----------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m TestGetAddressBookWSWithSessionId -s localhost -p 8085 -u JDE -w Modus2020! -e JDV920 -r *ALL -a 28

-----------------------------------------------------------
Get Logs
-----------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m GetLog -s localhost -p 8085 -t 20211124115453

-----------------------------------------------------------
Get Metadata Operations
-----------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m GetMetadataOperations -s localhost -p 8085 -u JDE  -w Modus2020! -e JDV920 -r *ALL
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m GetMetadataOperations -s localhost -p 8085 -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NzY3MTU2LCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiJNb2R1czIwMjAhIiwiZW52aXJvbm1lbnQiOiJKRFY5MjAiLCJyb2xlIjoiKkFMTCIsInNlc3Npb25JZCI6MTY4MjE3NDkxMywiZXhwIjoxNjM3NzcxOTU2fQ.0OP5rJYoqnXRmZ5JKgdNXGWsMaDYW7uhFY9HKyTjZ1A

-----------------------------------------------------------
Get Metadata Web Service
-----------------------------------------------------------
 
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m GetMetadataWS -s localhost -p 8085 -u JDE  -w Modus2020! -e JDV920 -r *ALL -o "oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook"
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m GetMetadataWS -s localhost -p 8085 -o "oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook" -k eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMxMjMxIiwiaWF0IjoxNjM3NzY4MTg2LCJzdWIiOiJTdWJqZWN0IiwiaXNzIjoiSXNzdWUiLCJ1c2VyIjoiSkRFIiwicGFzc3dvcmQiOiJNb2R1czIwMjAhIiwiZW52aXJvbm1lbnQiOiJKRFY5MjAiLCJyb2xlIjoiKkFMTCIsInNlc3Npb25JZCI6LTE2MDM0MjM0MjN9.ePaqAQmvzoybFBvGjO1F6_bMkW-LFTJAvgi_uj2UK1Q

-----------------------------------------------------------
Get JSON Schema
-----------------------------------------------------------

java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m GetJsonWS -s localhost -p 8085 -u JDE -w Modus2020! -e JDV920 -r *ALL   -o "oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook"
java -jar target/jd-check-microservice-1.0.0-jar-with-dependencies.jar -m GetJsonWS -s localhost -p 8085 -o "oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook" -k 



  
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
































