@echo off
REM ============================================================
REM build_test.cmd  -  utilidades de compilacion y testing
REM Uso:
REM   build_test.cmd compile        compila src -> bin
REM   build_test.cmd compile-tests  compila test -> test-bin
REM   build_test.cmd test           compila todo y corre la suite JUnit
REM ============================================================
setlocal enabledelayedexpansion
set JAVAC=C:\jdk8u422-b05\bin\javac.exe
set JAVA=C:\jdk8u422-b05\bin\java.exe
set CP=test-bin;bin;lib/*;lib-test/*;miglayout15-swing.jar;javax.mail.jar;jgoodies-forms-1.8.0.jar;Fonts/Roboto.jar;Fonts/MyfuenteCambria.jar

if "%1"=="compile" goto compile
if "%1"=="compile-tests" goto compile-tests
if "%1"=="test" goto test
echo Uso: build_test.cmd compile ^| compile-tests ^| test
exit /b 1

:compile
echo === Compilando src -^> bin ===
"%JAVAC%" -encoding UTF-8 -d bin -cp "%CP%" -sourcepath src src\main\Main.java 2>&1
exit /b %errorlevel%

:compile-tests
if not exist test-bin mkdir test-bin
echo === Compilando test -^> test-bin ===
del /q "%TEMP%\test_sources.txt" 2>nul
for /r test %%f in (*.java) do (
  set "SRC=%%f"
  set "SRC=!SRC:\=/!"
  echo !SRC!>>"%TEMP%\test_sources.txt"
)
"%JAVAC%" -encoding UTF-8 -d test-bin -cp "%CP%" -sourcepath src;test @"%TEMP%\test_sources.txt" 2>&1
exit /b %errorlevel%

:test
call %0 compile
if errorlevel 1 exit /b 1
call %0 compile-tests
if errorlevel 1 exit /b 1
echo === Ejecutando suite JUnit ===
set CLASSES=
for /r test-bin %%f in (*Test.class) do (
  set "P=%%f"
  set "P=!P:%CD%\test-bin\=!"
  set "P=!P:\=.!"
  set "P=!P:.class=!"
  set "CLASSES=!CLASSES! !P!"
)
"%JAVA%" -cp "%CP%" org.junit.runner.JUnitCore !CLASSES!
exit /b %errorlevel%
