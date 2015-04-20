@echo off

javac -cp NCSO.jar TestLND.java

REM change this line:
REM java -cp .;NCSO.jar TestLND -o <domain> -h <hostname:port> -u <username> -p <password> -c <cert.id location> -cp <cert.id password> -t <mail template> -a <account to be created>


REM For example
REM java -cp .;NCSO.jar TestLND -o caorg -h myhost:63148 -u Administrator -p pwd -c "c:\\\\Program Files\\IBM\\Lotus\\Domino\\data\\cert.id" -cp pwd -t mail4.ntf -a account1


java -cp .;NCSO.jar TestLND -o caorg -h imwinlnd9rs:63148 -u Administrator -p AdminQA1 -c "c:\\\\Program Files (x86)\\IBM\\Notes\\Data\\cert.id" -cp AdminQA1  -t mail9.ntf -a intADD05 -m imwinlnd9rs

pause


