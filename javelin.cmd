@echo off
setlocal
set "JAVELIN_HOME=%~dp0"
java -jar "%JAVELIN_HOME%modules\javelin-console\target\javelin-console-0.1.0-SNAPSHOT-all.jar" %*
