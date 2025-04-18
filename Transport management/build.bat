@echo off
echo Compiling RunBTS.java...
javac RunBTS.java
if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Creating JAR file...
jar cvfm BangladeshTransport.jar launcher.manifest RunBTS.class

echo.
echo JAR file created successfully: BangladeshTransport.jar
echo You can now run the application by double-clicking the JAR file
echo or by running: java -jar BangladeshTransport.jar
echo.
pause 