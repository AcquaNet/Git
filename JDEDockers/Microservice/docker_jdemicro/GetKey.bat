java -jar CreadorDeLicencias-1.0.0-jar-with-dependencies.jar -codCliente BODY
for /f "tokens=*" %%a in ('java -jar CreadorDeLicencias-1.0.0-jar-with-dependencies.jar -codCliente CLIENTE') do set licence=%%a
echo %licence%
pause