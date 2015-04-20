@ECHO OFF
:BEGIN

@ECHO:
set currentDirectrory=%cd%
@ECHO:

javac -cp sapjco3.jar;. PreparedCodetoconnect.java

echo "Java file has compiled sucessfully."

java -cp ".;./*;sapjco3.jar" PreparedCodetoconnect

pause
EXIT
:END