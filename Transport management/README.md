# Bangladesh Transport System

## Quick Start (NEW - Recommended Method)

1. Double-click on `install.bat` to set up the application and create a desktop shortcut
2. Use the desktop shortcut to run the application
3. Alternatively, run `fix_and_run.bat` directly

## Default Login Credentials

### Admin User
- Username: `admin`
- Password: `admin123`

### Regular User
- Username: `user`
- Password: `user123`

## Run Options

Several methods are provided to run the application:

1. **NEW - RECOMMENDED:** Run `install.bat` to set up everything and create a desktop shortcut
2. Use `fix_and_run.bat` - handles directory changes and proper execution
3. Use `fix_and_run.ps1` - PowerShell version with better error handling
4. Use `run_with_classpath.bat` - runs with explicit classpath setting

## Troubleshooting

If you encounter issues:

1. **PowerShell Script Execution Issues**:
   - If PowerShell scripts won't run, you may need to change the execution policy:
   - Open PowerShell as Administrator and run: `Set-ExecutionPolicy RemoteSigned -Scope CurrentUser`

2. **Java Not Found**:
   - Make sure Java is installed and in your PATH
   - You can download Java from: https://www.oracle.com/java/technologies/downloads/

3. **Class Not Found Errors**:
   - Use `run_with_classpath.bat` which explicitly sets the classpath

4. **File Structure Issues**:
   - Ensure the following structure exists:
     ```
     YourFolder/
     ├── New folder/
     │   └── TransportSystem/
     │       └── BangladeshTransportDemo.class
     ├── install.bat
     ├── fix_and_run.bat
     └── README.md
     ```

5. **Permission Issues**:
   - Right-click on batch files and select "Run as administrator" if you encounter permission issues

## Data Storage

User data is stored in the `data` directory that will be created automatically. The data files include:

- `data/users.dat` - User credentials and profiles

## Keeping Your Data

If you need to reinstall or move the application, make sure to copy the `data` folder to preserve your user accounts and settings. 