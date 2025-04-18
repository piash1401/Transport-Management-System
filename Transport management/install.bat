@echo off
color 0A
echo ===== Bangladesh Transport System - Installer =====
echo.

REM Check Java installation
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    color 0C
    echo ERROR: Java is not installed or not in your PATH.
    echo Please install Java and try again.
    pause
    exit /b 1
)
echo [OK] Java detected

REM Check if required directories exist
if not exist "New folder\TransportSystem\BangladeshTransportDemo.class" (
    color 0C
    echo ERROR: Could not find required class files.
    echo Please ensure the application is correctly installed.
    pause
    exit /b 1
)
echo [OK] Application files found

REM Create data directory
if not exist "data" (
    mkdir "data" 2>nul
    if not exist "data" (
        color 0C
        echo ERROR: Could not create data directory.
        pause
        exit /b 1
    )
    echo [OK] Data directory created
) else (
    echo [OK] Data directory exists
)

REM Create desktop shortcut
echo Creating desktop shortcut...
set DESKTOP=%USERPROFILE%\Desktop
echo Set oWS = WScript.CreateObject("WScript.Shell") > CreateShortcut.vbs
echo sLinkFile = "%DESKTOP%\Bangladesh Transport System.lnk" >> CreateShortcut.vbs
echo Set oLink = oWS.CreateShortcut(sLinkFile) >> CreateShortcut.vbs
echo oLink.TargetPath = "%CD%\fix_and_run.bat" >> CreateShortcut.vbs
echo oLink.WorkingDirectory = "%CD%" >> CreateShortcut.vbs
echo oLink.IconLocation = "%SystemRoot%\System32\SHELL32.dll,174" >> CreateShortcut.vbs
echo oLink.Description = "Launch Bangladesh Transport System" >> CreateShortcut.vbs
echo oLink.Save >> CreateShortcut.vbs
cscript //nologo CreateShortcut.vbs
if %ERRORLEVEL% NEQ 0 (
    echo [WARNING] Failed to create desktop shortcut.
) else (
    echo [OK] Desktop shortcut created
    del CreateShortcut.vbs >nul 2>&1
)

echo.
echo Installation completed successfully!
echo.
echo Available run options:
echo 1. Use the desktop shortcut
echo 2. Run fix_and_run.bat
echo 3. Run run_with_classpath.bat
echo.
echo Default login credentials:
echo Admin: username=admin, password=admin123
echo User:  username=user,  password=user123
echo.

REM Ask to run the application now
set /p RUNAPP=Would you like to run the application now? (Y/N): 
if /i "%RUNAPP%"=="Y" (
    call fix_and_run.bat
) else (
    echo.
    echo You can run the application later using the desktop shortcut or fix_and_run.bat
    pause
) 