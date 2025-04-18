@echo off
echo Compiling RunBTS.java...
javac RunBTS.java
if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Starting Bangladesh Transport System...
java RunBTS
pause 