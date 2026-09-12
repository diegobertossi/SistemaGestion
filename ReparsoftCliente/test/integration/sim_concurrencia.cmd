@echo off
REM ============================================================
REM sim_concurrencia.cmd - simulacion masiva concurrente (RDP-like)
REM Uso:
REM   sim_concurrencia.cmd [workers=2] [ciclos=3] [rapido|humano]
REM Requiere:
REM   - MySQL local con ordenesbrc_sim + seeds (sql/simulacion_preparar.sql)
REM   - config.properties con db.database.bariloche=ordenesbrc_sim
REM   - test-bin compilado (build_test.cmd compile-tests)
REM   - Correr desde ReparsoftCliente (este script se ubica solo)
REM Cada worker = un proceso JVM (como cada sesion RDP). Los logs van a
REM %TEMP%\sim_reparsoft\. NO toca datos reales (guardarrailes incluidos).
REM ============================================================
setlocal enabledelayedexpansion
cd /d "%~dp0..\.."

set WORKERS=%1
if "%WORKERS%"=="" set WORKERS=2
set CICLOS=%2
if "%CICLOS%"=="" set CICLOS=3
set MODO=%3
if "%MODO%"=="" set MODO=rapido

set CP=test-bin;bin;lib/*;lib-test/*
set LOGDIR=%TEMP%\sim_reparsoft
if not exist "%LOGDIR%" mkdir "%LOGDIR%"
del /q "%LOGDIR%\worker*.log" "%LOGDIR%\worker*.done" 2>nul

if not exist "test-bin\integration\SimuladorConcurrencia.class" (
  echo [ERROR] Falta test-bin. Ejecute antes: build_test.cmd compile-tests
  exit /b 1
)
if not exist "reportes\Presupuesto.jasper" (
  echo [ERROR] Correr desde ReparsoftCliente ^(CWD=%CD%^). Falta reportes\Presupuesto.jasper
  exit /b 1
)

set JAVA=C:\jdk8u422-b05\bin\java.exe
if not exist "%JAVA%" set JAVA=java

echo === Simulacion: %WORKERS% workers x %CICLOS% ciclos [%MODO%] ===
echo === Logs en %LOGDIR% ===

for /L %%w in (1,1,%WORKERS%) do (
  echo Lanzando worker %%w ...
  start "" /b cmd /v:on /c ""%JAVA%" -Djava.awt.headless=true -cp "%CP%" integration.SimuladorConcurrencia %%w %CICLOS% %MODO% > "%LOGDIR%\worker%%w.log" 2>&1 & echo DONE ^!ERRORLEVEL^! > "%LOGDIR%\worker%%w.done""
 REM Espera escalonada compatible con stdin redirigido (timeout.exe exige consola)
  ping -n 4 127.0.0.1 >nul
)

echo Esperando workers ^(timeout 25 min^) ...
set ESPERADOS=%WORKERS%
set /a LIMITE=25*60/5
set /a PASADAS=0
:espera
set /a HECHOS=0
for /L %%w in (1,1,%WORKERS%) do (
  if exist "%LOGDIR%\worker%%w.done" set /a HECHOS+=1
)
if %HECHOS%==%ESPERADOS% goto finworkers
set /a PASADAS+=1
if %PASADAS% GEQ %LIMITE% (
  echo [ERROR] Timeout esperando workers ^(%HECHOS%/%ESPERADOS%^)
  exit /b 1
)
REM ping en vez de timeout.exe: funciona con stdin redirigido
ping -n 6 127.0.0.1 >nul
goto espera

:finworkers
echo.
echo === Resultado por worker ===
findstr "SIM_DONE" "%LOGDIR%\worker*.log"
echo.
set /a TOTAL=%WORKERS%*%CICLOS%
echo === Verificador: %TOTAL% ciclos esperados ===
"%JAVA%" -Djava.awt.headless=true -cp "%CP%" integration.VerificadorSim %TOTAL%
exit /b %ERRORLEVEL%
