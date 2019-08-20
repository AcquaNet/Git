java -jar C:\_work\Acqua\DragonFish\projects\CreadorDeLicencias\target\CreadorDeLicencias-1.0.0-jar-with-dependencies.jar -codCliente CLIENTE
for /f "tokens=*" %%a in ('java -jar C:\_work\Acqua\DragonFish\projects\CreadorDeLicencias\target\CreadorDeLicencias-1.0.0-jar-with-dependencies.jar -codCliente CLIENTE') do set licence=%%a
echo %licence%
pause