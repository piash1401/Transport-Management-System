# Bangladesh Transport System

A simple Java console application for managing a transport system in Bangladesh. This application allows users to register, login, search for routes between Bangladesh cities, book tickets, and manage their bookings. Administrators can manage vehicles, routes, and view all bookings and users.

## Features

- User Registration & Login
- Add Vehicles (buses)
- Search for Routes between Bangladesh cities
- View Popular Bangladesh Routes
- Book Tickets
- View Bookings
- Cancel Booking
- Admin Dashboard

## Bangladesh Routes

The system comes pre-loaded with popular routes in Bangladesh:

### Major Routes from Dhaka

- Dhaka to Chittagong (250 km)
- Dhaka to Sylhet (240 km)
- Dhaka to Rajshahi (260 km)
- Dhaka to Khulna (280 km)
- Dhaka to Barisal (190 km)
- Dhaka to Rangpur (300 km)

### Tourist Routes

- Chittagong to Cox's Bazar (150 km)
- Chittagong to Bandarban (90 km)
- Chittagong to Rangamati (80 km)
- Sylhet to Sunamganj (70 km)

### Local Routes

- Dhaka to Mymensingh (120 km)
- Dhaka to Tangail (100 km)
- Dhaka to Comilla (100 km)
- Khulna to Jessore (80 km)
- Rajshahi to Bogra (90 km)

## Available Transport Options

The system includes various bus transport options commonly found in Bangladesh:

### Buses

- Hanif Enterprise (AC)
- Shyamoli Paribahan (Non-AC)
- Green Line (AC)
- Ena Transport (AC)
- Sohag Paribahan (Non-AC)

## Project Structure

The project is organized into the following packages:

- `com.transport.model`: Contains the model classes (User, Admin, Vehicle, Bus, Route, Booking)
- `com.transport.service`: Contains the service classes (UserService, VehicleService, RouteService, BookingService)
- `com.transport.util`: Contains utility classes (FileUtil, DataInitializer)
- `com.transport`: Contains the main application class (TransportApp)

## How to Run

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Java IDE (Eclipse, IntelliJ IDEA, etc.) or command line

### Running from IDE

1. Open the project in your IDE
2. Navigate to `RunBTS.java`
3. Run the main method

### Running from Command Line

1. Navigate to the project root directory
2. Run the batch file to compile and run:
   ```
   run.bat
   ```
   
3. Alternatively, compile manually:
   ```
   javac *.java
   ```
   
4. Then run the application:
   ```
   java RunBTS
   ```

### Running from Terminal in Linux/Mac
1. Navigate to the project root directory
2. Compile the Java files:
   ```
   javac *.java
   ```
3. Run the application:
   ```
   java RunBTS
   ```

## Default Admin Account

The system comes with a default admin account:

- Username: admin
- Password: admin123

## Usage

### Guest Functions

1. View available routes in Bangladesh without logging in

### User Functions

1. Register a new account
2. Login with your credentials
3. Search for routes by entering source and destination (e.g., "Dhaka" to "Chittagong")
4. View popular Bangladesh routes with details
5. Book tickets by selecting a route and vehicle
6. View your bookings
7. Cancel a booking if needed

### Admin Functions

1. Login with admin credentials
2. Manage vehicles (add, view, delete)
3. Manage routes (add, view, add vehicles to routes, add intermediate stops, delete)
4. View all bookings
5. View all users

## Booking a Ticket

1. Login to your account
2. Select "Search Routes" from the menu
3. Enter your source city (e.g., "Dhaka") and destination (e.g., "Chittagong")
4. View the available routes and transport options
5. Select "Book a Ticket" from the menu
6. Choose a route, vehicle, journey date/time, and number of seats
7. Confirm your booking
8. The system will display your booking details and total fare

## Data Persistence

The application uses file-based storage to persist data:

- `users.dat`: Stores user information
- `vehicles.dat`: Stores vehicle information
- `routes.dat`: Stores route information
- `bookings.dat`: Stores booking information

These files are automatically created in the project root directory when the application is run for the first time.

## License

This project is open source and available under the MIT License.
