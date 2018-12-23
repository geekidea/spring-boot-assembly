@echo off
rem ======================================================================
rem windows startup script
rem
rem author: geekidea
rem date: 2018-12-2
rem ======================================================================

rem Open in a browser
start "" "http://localhost:8080/example/hello?name=123"

rem startup jar
java -jar ../boot/spring-boot-assembly.jar --spring.config.location=../config/

pause