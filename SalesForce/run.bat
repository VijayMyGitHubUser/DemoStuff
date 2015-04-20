@ECHO OFF
:BEGIN

@ECHO:
set currentDirectrory=%cd%
@ECHO:

REM javac -cp sapjco3.jar;. PreparedCodetoconnect.java

echo "Java file has compiled sucessfully."

java -cp .;"C:\Users\kuovi01\Desktop\ForDemo\SalesForce\axis2-1.4.1\lib\*" SFDCConnection

pause
EXIT
:END