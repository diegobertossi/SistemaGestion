@echo off
setlocal
del /q "%TEMP%\test_sources.txt" 2>nul
for /r test %%f in (*.java) do echo "%%f">>"%TEMP%\test_sources.txt"
type "%TEMP%\test_sources.txt"
