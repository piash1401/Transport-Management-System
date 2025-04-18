# Bangladesh Transport System Web Interface

This is a simple web interface for the Bangladesh Transport System, allowing users to view routes, book tickets, and manage their bookings.

## Features

- View popular routes in Bangladesh
- Search for routes between cities
- Book tickets for buses
- User registration and login
- Responsive design for mobile and desktop

## How to Run

### Option 1: Using a Simple HTTP Server

1. Navigate to the `web` directory:

   ```
   cd TransportSystem/web
   ```

2. If you have Python installed, you can use its built-in HTTP server:

   For Python 3:

   ```
   python -m http.server
   ```

   For Python 2:

   ```
   python -m SimpleHTTPServer
   ```

3. Open your browser and navigate to:
   ```
   http://localhost:8000
   ```

### Option 2: Using VS Code Live Server

1. Install the "Live Server" extension in VS Code
2. Open the `web` directory in VS Code
3. Right-click on `index.html` and select "Open with Live Server"

### Option 3: Simply Opening the HTML File

1. Navigate to the `web` directory
2. Double-click on `index.html` to open it in your default browser

## Default Admin Login

- Username: admin
- Password: admin123

## Notes

- This is a frontend-only implementation. In a real application, it would connect to the Java backend.
- The booking functionality is simulated for demonstration purposes.
- The routes and fare calculations are simplified examples based on the Bangladesh Transport System Java application.

## Future Improvements

- Connect to the Java backend using REST APIs
- Add user authentication with JWT
- Implement real-time availability checking
- Add payment gateway integration
- Enhance the UI with more interactive elements
