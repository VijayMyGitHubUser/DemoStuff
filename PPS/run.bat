@ECHO OFF
:BEGIN

@ECHO:
set currentDirectrory=%cd%
@ECHO:

javac -cp psjoa.jar PPSConnectionTest.java

echo "Java file has compiled sucessfully."

java -cp psjoa.jar;. PPSConnectionTest  PS Password CF-PPL850 9010

pause
EXIT
:END

