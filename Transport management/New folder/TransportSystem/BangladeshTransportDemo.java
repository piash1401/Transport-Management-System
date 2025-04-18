import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 * Model classes for the Bangladesh Transport System
 */

// Vehicle class to represent vehicles in the system
class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String vehicleId;
    private String registrationNumber;
    private String type; // Bus, Train, Taxi
    private String model;
    private int capacity;
    private String status; // Active, Maintenance, Out of Service
    private String assignedRoute;
    private String assignedDriverId;
    private LocalDate lastMaintenanceDate;
    private Map<String, Object> additionalDetails = new HashMap<>();
    
    public Vehicle() {
        this.vehicleId = "VEH" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.status = "Active";
        this.lastMaintenanceDate = LocalDate.now();
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedRoute() {
        return assignedRoute;
    }

    public void setAssignedRoute(String assignedRoute) {
        this.assignedRoute = assignedRoute;
    }

    public String getAssignedDriverId() {
        return assignedDriverId;
    }

    public void setAssignedDriverId(String assignedDriverId) {
        this.assignedDriverId = assignedDriverId;
    }

    public LocalDate getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(LocalDate lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public Map<String, Object> getAdditionalDetails() {
        return additionalDetails;
    }

    public void setAdditionalDetails(Map<String, Object> additionalDetails) {
        this.additionalDetails = additionalDetails;
    }
    
    public void addDetail(String key, Object value) {
        this.additionalDetails.put(key, value);
    }
    
    @Override
    public String toString() {
        return type + " - " + registrationNumber;
    }
}

// Staff class to represent employees in the system
class Staff implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String staffId;
    private String name;
    private String role; // Driver, Helper, Conductor, Manager
    private String contactNumber;
    private String email;
    private String address;
    private LocalDate joiningDate;
    private String licenseNumber; // For drivers
    private String assignedVehicleId;
    private boolean isActive;
    
    public Staff() {
        this.staffId = "STF" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.joiningDate = LocalDate.now();
        this.isActive = true;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getAssignedVehicleId() {
        return assignedVehicleId;
    }

    public void setAssignedVehicleId(String assignedVehicleId) {
        this.assignedVehicleId = assignedVehicleId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    @Override
    public String toString() {
        return name + " (" + role + ")";
    }
}

// Route class to represent transport routes
class Route implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String routeId;
    private String source;
    private String destination;
    private int distance;
    private List<String> intermediateStops = new ArrayList<>();
    private Map<String, Double> fareByTransportType = new HashMap<>();
    private String status; // Active, Suspended
    private List<String> assignedVehicleIds = new ArrayList<>();
    
    public Route() {
        this.routeId = "RTE" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.status = "Active";
    }
    
    public Route(String source, String destination, int distance) {
        this();
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public List<String> getIntermediateStops() {
        return intermediateStops;
    }

    public void setIntermediateStops(List<String> intermediateStops) {
        this.intermediateStops = intermediateStops;
    }
    
    public void addIntermediateStop(String stop) {
        this.intermediateStops.add(stop);
    }

    public Map<String, Double> getFareByTransportType() {
        return fareByTransportType;
    }

    public void setFareByTransportType(Map<String, Double> fareByTransportType) {
        this.fareByTransportType = fareByTransportType;
    }
    
    public void setFare(String transportType, double farePerKm) {
        this.fareByTransportType.put(transportType, farePerKm);
    }
    
    public double getFare(String transportType) {
        return this.fareByTransportType.getOrDefault(transportType, 0.0);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getAssignedVehicleIds() {
        return assignedVehicleIds;
    }

    public void setAssignedVehicleIds(List<String> assignedVehicleIds) {
        this.assignedVehicleIds = assignedVehicleIds;
    }
    
    public void assignVehicle(String vehicleId) {
        if (!this.assignedVehicleIds.contains(vehicleId)) {
            this.assignedVehicleIds.add(vehicleId);
        }
    }
    
    public void unassignVehicle(String vehicleId) {
        this.assignedVehicleIds.remove(vehicleId);
    }
    
    @Override
    public String toString() {
        return source + " to " + destination + " (" + distance + " km)";
    }
}

/**
 * Demo application for the Bangladesh Transport System Swing UI
 */
public class BangladeshTransportDemo {
    
    private static final Color PRIMARY_COLOR = new Color(0, 113, 188); // Blue
    private static final Color SECONDARY_COLOR = new Color(0, 147, 69); // Green
    private static final Color ACCENT_COLOR = new Color(214, 40, 40);  // Red
    private static final Color BG_COLOR = new Color(248, 249, 250);    // Light gray
    private static final Color ADMIN_COLOR = new Color(80, 40, 140);   // Purple for admin
    
    // Simple classes to handle user authentication without external dependencies
    public static class Booking implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String bookingId;
        private String userId;
        private String source;
        private String destination;
        private String transportType;
        private LocalDate journeyDate;
        private int seats;
        private double fare;
        private LocalDateTime bookingDateTime;
        private String status; // CONFIRMED, CANCELLED, COMPLETED
        private List<Integer> seatNumbers; // Add this field
        
        public Booking() {
            this.bookingDateTime = LocalDateTime.now();
            this.status = "CONFIRMED";
            this.seatNumbers = new ArrayList<>();
        }
        
        public Booking(String bookingId, String userId, String source, String destination, 
                      String transportType, LocalDate journeyDate, int seats, double fare) {
            this.bookingId = bookingId;
            this.userId = userId;
            this.source = source;
            this.destination = destination;
            this.transportType = transportType;
            this.journeyDate = journeyDate;
            this.seats = seats;
            this.fare = fare;
            this.bookingDateTime = LocalDateTime.now();
            this.status = "CONFIRMED";
        }
        
        public String getBookingId() { return bookingId; }
        public void setBookingId(String bookingId) { this.bookingId = bookingId; }
        
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
        
        public String getDestination() { return destination; }
        public void setDestination(String destination) { this.destination = destination; }
        
        public String getTransportType() { return transportType; }
        public void setTransportType(String transportType) { this.transportType = transportType; }
        
        public LocalDate getJourneyDate() { return journeyDate; }
        public void setJourneyDate(LocalDate journeyDate) { this.journeyDate = journeyDate; }
        
        public int getSeats() { return seats; }
        public void setSeats(int seats) { this.seats = seats; }
        
        public double getFare() { return fare; }
        public void setFare(double fare) { this.fare = fare; }
        
        public LocalDateTime getBookingDateTime() { return bookingDateTime; }
        public void setBookingDateTime(LocalDateTime bookingDateTime) { this.bookingDateTime = bookingDateTime; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        // Add getter and setter for seatNumbers
        public List<Integer> getSeatNumbers() { return seatNumbers; }
        public void setSeatNumbers(List<Integer> seatNumbers) { this.seatNumbers = seatNumbers; }
        
        @Override
        public String toString() {
            return "Booking{" +
                    "bookingId='" + bookingId + '\'' +
                    ", source='" + source + '\'' +
                    ", destination='" + destination + '\'' +
                    ", journeyDate=" + journeyDate +
                    ", seats=" + seats +
                    ", seatNumbers=" + seatNumbers +
                    ", status='" + status + '\'' +
                    '}';
        }
    }
    
    public static class User implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String userId;
        private String username;
        private String password;
        private String name;
        private String email;
        private String phone;
        private List<Booking> bookings;
        
        public User() {
            this.bookings = new ArrayList<>();
        }
        
        public User(String userId, String username, String password, String name, String email, String phone) {
            this.userId = userId;
            this.username = username;
            this.password = password;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.bookings = new ArrayList<>();
        }
        
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        
        public List<Booking> getBookings() { 
            if (bookings == null) {
                bookings = new ArrayList<>();
            }
            return bookings; 
        }
        
        public void addBooking(Booking booking) {
            if (bookings == null) {
                bookings = new ArrayList<>();
            }
            this.bookings.add(booking);
        }
        
        public boolean removeBooking(String bookingId) {
            if (bookings == null) {
                return false;
            }
            return bookings.removeIf(booking -> booking.getBookingId().equals(bookingId));
        }
        
        @Override
        public String toString() {
            return "User{" +
                    "userId='" + userId + '\'' +
                    ", username='" + username + '\'' +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }
    
    public static class Admin extends User {
        private static final long serialVersionUID = 1L;
        
        private String adminRole;
        
        public Admin() {
            super();
            this.adminRole = "SYSTEM_ADMIN";
        }
        
        public Admin(String userId, String username, String password, String name, String email, String phone, String adminRole) {
            super(userId, username, password, name, email, phone);
            this.adminRole = adminRole;
        }
        
        public String getAdminRole() { return adminRole; }
        public void setAdminRole(String adminRole) { this.adminRole = adminRole; }
    }
    
    public static class BookingService {
        private List<Booking> bookings = new ArrayList<>();
        private static final String BOOKING_FILE_PATH = "data/bookings.dat";
        
        // Define route distances
        private static final Map<String, Integer> ROUTE_DISTANCES = new HashMap<>();
        static {
            // From Dhaka
            ROUTE_DISTANCES.put("Dhaka-Chittagong", 250);
            ROUTE_DISTANCES.put("Dhaka-Sylhet", 240);
            ROUTE_DISTANCES.put("Dhaka-Rajshahi", 260);
            ROUTE_DISTANCES.put("Dhaka-Khulna", 280);
            ROUTE_DISTANCES.put("Dhaka-Barisal", 190);
            ROUTE_DISTANCES.put("Dhaka-Rangpur", 300);
            ROUTE_DISTANCES.put("Dhaka-Mymensingh", 120);
            ROUTE_DISTANCES.put("Dhaka-Tangail", 100);
            ROUTE_DISTANCES.put("Dhaka-Comilla", 100);
            
            // From Chittagong
            ROUTE_DISTANCES.put("Chittagong-Cox's Bazar", 150);
            ROUTE_DISTANCES.put("Chittagong-Bandarban", 90);
            ROUTE_DISTANCES.put("Chittagong-Rangamati", 80);
            
            // From Sylhet
            ROUTE_DISTANCES.put("Sylhet-Sunamganj", 70);
            
            // From Khulna
            ROUTE_DISTANCES.put("Khulna-Jessore", 80);
            
            // From Rajshahi
            ROUTE_DISTANCES.put("Rajshahi-Bogra", 90);
            
            // Add reverse routes
            Map<String, Integer> reverseRoutes = new HashMap<>();
            for (Map.Entry<String, Integer> entry : ROUTE_DISTANCES.entrySet()) {
                String[] parts = entry.getKey().split("-");
                reverseRoutes.put(parts[1] + "-" + parts[0], entry.getValue());
            }
            ROUTE_DISTANCES.putAll(reverseRoutes);
        }
        
        // Define transport types and fares per km
        private static final Map<String, Double> TRANSPORT_RATES = new HashMap<>();
        static {
            TRANSPORT_RATES.put("Bus", 2.0); // 2 BDT per km
        }
        
        public BookingService() {
            loadBookings();
        }
        
        @SuppressWarnings("unchecked")
        private void loadBookings() {
            // Ensure data directory exists
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                if (!dataDir.mkdirs()) {
                    System.err.println("Error creating data directory for bookings");
                }
            }
            
            boolean loadFailed = false;
            Object obj = null;
            
            try {
                obj = readFromFile(BOOKING_FILE_PATH);
                if (obj != null && obj instanceof List) {
                    bookings = (List<Booking>) obj;
                    System.out.println("Loaded " + bookings.size() + " bookings from file");
                    
                    // Initialize seat numbers list if null (for backward compatibility)
                    for (Booking booking : bookings) {
                        if (booking.getSeatNumbers() == null) {
                            booking.setSeatNumbers(new ArrayList<>());
                        }
                    }
                } else {
                    loadFailed = true;
                }
            } catch (Exception e) {
                System.err.println("Error loading bookings: " + e.getMessage());
                e.printStackTrace();
                loadFailed = true;
            }
            
            if (loadFailed) {
                bookings = new ArrayList<>();
                saveBookings();
            }
        }
        
        private void saveBookings() {
            try {
                boolean success = writeToFile(BOOKING_FILE_PATH, bookings);
                if (success) {
                    System.out.println("Saved " + bookings.size() + " bookings to file");
                } else {
                    System.err.println("Failed to save bookings to file");
                }
            } catch (Exception e) {
                System.err.println("Error saving bookings: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        public boolean createBooking(Booking booking) {
            if (booking != null) {
                bookings.add(booking);
                saveBookings();
                return true;
            }
            return false;
        }
        
        public List<Booking> getBookingsByUserId(String userId) {
            return bookings.stream()
                    .filter(booking -> booking.getUserId().equals(userId))
                    .toList();
        }
        
        public Optional<Booking> getBookingById(String bookingId) {
            return bookings.stream()
                    .filter(booking -> booking.getBookingId().equals(bookingId))
                    .findFirst();
        }
        
        public boolean cancelBooking(String bookingId) {
            Optional<Booking> bookingOpt = getBookingById(bookingId);
            if (bookingOpt.isPresent()) {
                Booking booking = bookingOpt.get();
                booking.setStatus("CANCELLED");
                saveBookings();
                return true;
            }
            return false;
        }
        
        public List<Booking> getAllBookings() {
            return new ArrayList<>(bookings);
        }
        
        public int getRouteDistance(String source, String destination) {
            String routeKey = source + "-" + destination;
            return ROUTE_DISTANCES.getOrDefault(routeKey, 0);
        }
        
        public double calculateFare(String source, String destination, String transportType, int seats) {
            int distance = getRouteDistance(source, destination);
            double ratePerKm = TRANSPORT_RATES.getOrDefault(transportType, 0.0);
            return distance * ratePerKm * seats;
        }
        
        // Force reload bookings from file
        public void reloadBookingsFromFile() {
            loadBookings();
        }
        
        // File operations
        private static boolean writeToFile(String filePath, Object object) {
            // Ensure directory exists
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            try (FileOutputStream fos = new FileOutputStream(filePath);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(object);
                return true;
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        
        private static Object readFromFile(String filePath) {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File does not exist: " + filePath);
                return null;
            }
            
            try (FileInputStream fis = new FileInputStream(filePath);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                return ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error reading from file: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }
    
    public static class UserService {
        private List<User> users = new ArrayList<>();
        private static final String USER_FILE_PATH = "data/users.dat";
        
        public UserService() {
            loadUsers();
        }
        
        @SuppressWarnings("unchecked")
        private void loadUsers() {
            // Ensure data directory exists
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                if (!dataDir.mkdirs()) {
                    System.err.println("Error creating data directory");
                }
            }
            
            boolean loadFailed = false;
            Object obj = null;
            
            try {
                obj = readFromFile(USER_FILE_PATH);
                if (obj != null && obj instanceof List) {
                    users = (List<User>) obj;
                    System.out.println("Loaded " + users.size() + " users from file");
                } else {
                    loadFailed = true;
                }
            } catch (Exception e) {
                System.err.println("Error loading users: " + e.getMessage());
                e.printStackTrace();
                loadFailed = true;
            }
            
            if (loadFailed || users.isEmpty()) {
                // Add default admin user if no users exist
                Admin admin = new Admin();
                admin.setUserId("ADMIN001");
                admin.setUsername("admin");
                admin.setPassword("admin123");
                admin.setName("System Admin");
                admin.setEmail("admin@transport.com");
                admin.setPhone("1234567890");
                admin.setAdminRole("SYSTEM_ADMIN");
                users.add(admin);
                
                // Add a default user for testing
                User user = new User();
                user.setUserId("USER001");
                user.setUsername("user");
                user.setPassword("user123");
                user.setName("Test User");
                user.setEmail("user@transport.com");
                user.setPhone("9876543210");
                users.add(user);
                
                System.out.println("Created default users");
                saveUsers();
            }
        }
        
        private void saveUsers() {
            try {
                boolean success = writeToFile(USER_FILE_PATH, users);
                if (success) {
                    System.out.println("Saved " + users.size() + " users to file");
                } else {
                    System.err.println("Failed to save users to file");
                }
            } catch (Exception e) {
                System.err.println("Error saving users: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        public User registerUser(String username, String password, String name, String email, String phone) {
            // Check if username already exists
            if (getUserByUsername(username).isPresent()) {
                throw new IllegalArgumentException("Username already exists");
            }
            
            User user = new User();
            user.setUserId("USER" + UUID.randomUUID().toString().substring(0, 8));
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            
            users.add(user);
            saveUsers();
            return user;
        }
        
        public Optional<User> login(String username, String password) {
            return users.stream()
                    .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                    .findFirst();
        }
        
        public Optional<User> getUserByUsername(String username) {
            return users.stream()
                    .filter(user -> user.getUsername().equals(username))
                    .findFirst();
        }
        
        public boolean isAdmin(User user) {
            return user instanceof Admin;
        }
        
        // Get all users in the system
        public List<User> getUsers() {
            return new ArrayList<>(users);
        }
        
        // Find a user by ID
        public Optional<User> getUserById(String userId) {
            return users.stream()
                    .filter(user -> user.getUserId().equals(userId))
                    .findFirst();
        }
        
        // Add the updateUser method to fix the linter errors
        public User updateUser(User user) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserId().equals(user.getUserId())) {
                    users.set(i, user);
                    saveUsers();
                    return user;
                }
            }
            throw new IllegalArgumentException("User not found");
        }
        
        // File operations
        private static boolean writeToFile(String filePath, Object object) {
            // Ensure directory exists
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            try (FileOutputStream fos = new FileOutputStream(filePath);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(object);
                return true;
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        
        private static Object readFromFile(String filePath) {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File does not exist: " + filePath);
                return null;
            }
            
            try (FileInputStream fis = new FileInputStream(filePath);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                return ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error reading from file: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }
    
    // Vehicle Service for managing vehicles
    class VehicleService {
        private static final String VEHICLES_FILE = "vehicles.dat";
        private static List<Vehicle> vehicles = new ArrayList<>();
        
        static {
            loadVehicles();
        }
        
        // Load vehicles from file
        @SuppressWarnings("unchecked")
        public static void loadVehicles() {
            File file = new File(VEHICLES_FILE);
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    vehicles = (List<Vehicle>) ois.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                    vehicles = new ArrayList<>();
                }
            } else {
                vehicles = new ArrayList<>();
                // Add some demo vehicles
                addDemoVehicles();
            }
        }
        
        // Save vehicles to file
        public static void saveVehicles() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(VEHICLES_FILE))) {
                oos.writeObject(vehicles);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Add a new vehicle
        public static boolean addVehicle(Vehicle vehicle) {
            if (vehicle != null) {
                vehicles.add(vehicle);
                saveVehicles();
                return true;
            }
            return false;
        }
        
        // Get all vehicles
        public static List<Vehicle> getAllVehicles() {
            return new ArrayList<>(vehicles);
        }
        
        // Get vehicle by ID
        public static Optional<Vehicle> getVehicleById(String vehicleId) {
            return vehicles.stream()
                    .filter(v -> v.getVehicleId().equals(vehicleId))
                    .findFirst();
        }
        
        // Get vehicles by type
        public static List<Vehicle> getVehiclesByType(String type) {
            return vehicles.stream()
                    .filter(v -> v.getType().equals(type))
                    .collect(Collectors.toList());
        }
        
        // Get vehicles by status
        public static List<Vehicle> getVehiclesByStatus(String status) {
            return vehicles.stream()
                    .filter(v -> v.getStatus().equals(status))
                    .collect(Collectors.toList());
        }
        
        // Update vehicle
        public static boolean updateVehicle(Vehicle updatedVehicle) {
            for (int i = 0; i < vehicles.size(); i++) {
                if (vehicles.get(i).getVehicleId().equals(updatedVehicle.getVehicleId())) {
                    vehicles.set(i, updatedVehicle);
                    saveVehicles();
                    return true;
                }
            }
            return false;
        }
        
        // Delete vehicle
        public static boolean deleteVehicle(String vehicleId) {
            boolean removed = vehicles.removeIf(v -> v.getVehicleId().equals(vehicleId));
            if (removed) {
                saveVehicles();
            }
            return removed;
        }
        
        // Add demo vehicles
        private static void addDemoVehicles() {
            Vehicle bus1 = new Vehicle();
            bus1.setRegistrationNumber("BD-1234");
            bus1.setType("Bus");
            bus1.setModel("Volvo 9400");
            bus1.setCapacity(40);
            
            Vehicle bus2 = new Vehicle();
            bus2.setRegistrationNumber("BD-5678");
            bus2.setType("Bus");
            bus2.setModel("Scania K410");
            bus2.setCapacity(50);
            
            Vehicle train = new Vehicle();
            train.setRegistrationNumber("TR-3456");
            train.setType("Train");
            train.setModel("Express 100");
            train.setCapacity(200);
            
            vehicles.add(bus1);
            vehicles.add(bus2);
            vehicles.add(train);
            
            saveVehicles();
        }
    }
    
    // Staff Service for managing staff
    class StaffService {
        private static final String STAFF_FILE = "staff.dat";
        private static List<Staff> staffList = new ArrayList<>();
        
        static {
            loadStaff();
        }
        
        // Load staff from file
        @SuppressWarnings("unchecked")
        public static void loadStaff() {
            File file = new File(STAFF_FILE);
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    staffList = (List<Staff>) ois.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                    staffList = new ArrayList<>();
                }
            } else {
                staffList = new ArrayList<>();
                // Add some demo staff
                addDemoStaff();
            }
        }
        
        // Save staff to file
        public static void saveStaff() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STAFF_FILE))) {
                oos.writeObject(staffList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Add a new staff member
        public static boolean addStaff(Staff staff) {
            if (staff != null) {
                staffList.add(staff);
                saveStaff();
                return true;
            }
            return false;
        }
        
        // Get all staff
        public static List<Staff> getAllStaff() {
            return new ArrayList<>(staffList);
        }
        
        // Get staff by ID
        public static Optional<Staff> getStaffById(String staffId) {
            return staffList.stream()
                    .filter(s -> s.getStaffId().equals(staffId))
                    .findFirst();
        }
        
        // Get staff by role
        public static List<Staff> getStaffByRole(String role) {
            return staffList.stream()
                    .filter(s -> s.getRole().equals(role))
                    .collect(Collectors.toList());
        }
        
        // Get available drivers (not assigned to a vehicle)
        public static List<Staff> getAvailableDrivers() {
            return staffList.stream()
                    .filter(s -> s.getRole().equals("Driver") && s.isActive() && 
                           (s.getAssignedVehicleId() == null || s.getAssignedVehicleId().isEmpty()))
                    .collect(Collectors.toList());
        }
        
        // Update staff
        public static boolean updateStaff(Staff updatedStaff) {
            for (int i = 0; i < staffList.size(); i++) {
                if (staffList.get(i).getStaffId().equals(updatedStaff.getStaffId())) {
                    staffList.set(i, updatedStaff);
                    saveStaff();
                    return true;
                }
            }
            return false;
        }
        
        // Delete staff
        public static boolean deleteStaff(String staffId) {
            boolean removed = staffList.removeIf(s -> s.getStaffId().equals(staffId));
            if (removed) {
                saveStaff();
            }
            return removed;
        }
        
        // Assign staff to vehicle
        public static boolean assignStaffToVehicle(String staffId, String vehicleId) {
            Optional<Staff> staffOpt = getStaffById(staffId);
            if (staffOpt.isPresent()) {
                Staff staff = staffOpt.get();
                staff.setAssignedVehicleId(vehicleId);
                updateStaff(staff);
                return true;
            }
            return false;
        }
        
        // Add demo staff
        private static void addDemoStaff() {
            Staff driver1 = new Staff();
            driver1.setName("Rahim Khan");
            driver1.setRole("Driver");
            driver1.setContactNumber("01712345678");
            driver1.setLicenseNumber("DL123456");
            
            Staff driver2 = new Staff();
            driver2.setName("Karim Ahmed");
            driver2.setRole("Driver");
            driver2.setContactNumber("01798765432");
            driver2.setLicenseNumber("DL789012");
            
            Staff helper1 = new Staff();
            helper1.setName("Jamal Uddin");
            helper1.setRole("Helper");
            helper1.setContactNumber("01656789012");
            
            Staff conductor1 = new Staff();
            conductor1.setName("Faruk Islam");
            conductor1.setRole("Conductor");
            conductor1.setContactNumber("01545678901");
            
            staffList.add(driver1);
            staffList.add(driver2);
            staffList.add(helper1);
            staffList.add(conductor1);
            
            saveStaff();
        }
    }
    
    // Route Service for managing routes
    class RouteService {
        private static final String ROUTES_FILE = "routes.dat";
        private static List<Route> routes = new ArrayList<>();
        
        static {
            loadRoutes();
        }
        
        // Load routes from file
        @SuppressWarnings("unchecked")
        public static void loadRoutes() {
            File file = new File(ROUTES_FILE);
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    routes = (List<Route>) ois.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                    routes = new ArrayList<>();
                }
            } else {
                routes = new ArrayList<>();
                // Add some demo routes
                addDemoRoutes();
            }
        }
        
        // Save routes to file
        public static void saveRoutes() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ROUTES_FILE))) {
                oos.writeObject(routes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Add a new route
        public static boolean addRoute(Route route) {
            if (route != null) {
                routes.add(route);
                saveRoutes();
                return true;
            }
            return false;
        }
        
        // Get all routes
        public static List<Route> getAllRoutes() {
            return new ArrayList<>(routes);
        }
        
        // Get route by ID
        public static Optional<Route> getRouteById(String routeId) {
            return routes.stream()
                    .filter(r -> r.getRouteId().equals(routeId))
                    .findFirst();
        }
        
        // Find route by source and destination
        public static Optional<Route> findRoute(String source, String destination) {
            return routes.stream()
                    .filter(r -> r.getSource().equalsIgnoreCase(source) && 
                           r.getDestination().equalsIgnoreCase(destination))
                    .findFirst();
        }
        
        // Update route
        public static boolean updateRoute(Route updatedRoute) {
            for (int i = 0; i < routes.size(); i++) {
                if (routes.get(i).getRouteId().equals(updatedRoute.getRouteId())) {
                    routes.set(i, updatedRoute);
                    saveRoutes();
                    return true;
                }
            }
            return false;
        }
        
        // Delete route
        public static boolean deleteRoute(String routeId) {
            boolean removed = routes.removeIf(r -> r.getRouteId().equals(routeId));
            if (removed) {
                saveRoutes();
            }
            return removed;
        }
        
        // Get fare for a specific route and transport type
        public static double getFare(String routeId, String transportType) {
            Optional<Route> routeOpt = getRouteById(routeId);
            if (routeOpt.isPresent()) {
                Route route = routeOpt.get();
                double farePerKm = route.getFare(transportType);
                return route.getDistance() * farePerKm;
            }
            return 0.0;
        }
        
        // Add demo routes
        private static void addDemoRoutes() {
            Route route1 = new Route("Dhaka", "Chittagong", 250);
            route1.setFare("Bus", 1.5);
            route1.setFare("Train", 1.2);
            route1.addIntermediateStop("Comilla");
            route1.addIntermediateStop("Feni");
            
            Route route2 = new Route("Dhaka", "Sylhet", 200);
            route2.setFare("Bus", 1.4);
            route2.setFare("Train", 1.1);
            route2.addIntermediateStop("Narsingdi");
            route2.addIntermediateStop("Bhairab");
            
            Route route3 = new Route("Dhaka", "Khulna", 280);
            route3.setFare("Bus", 1.6);
            route3.setFare("Train", 1.3);
            route3.addIntermediateStop("Faridpur");
            route3.addIntermediateStop("Jessore");
            
            routes.add(route1);
            routes.add(route2);
            routes.add(route3);
            
            saveRoutes();
        }
    }
    
    private static UserService userService = new UserService();
    private static BookingService bookingService = new BookingService();
    private static User currentUser = null;
    private static JLabel userLabel;
    private static JButton loginButton;
    private static JButton logoutButton;
    private static JButton myBookingsButton;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                // Create and display the demo frame
                JFrame frame = createMainFrame();
                frame.setVisible(true);
                
                System.out.println("Bangladesh Transport System UI Demo started!");
            } catch (Exception e) {
                System.err.println("Error starting UI demo: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
    
    private static JFrame createMainFrame() {
        // Create the main frame
        JFrame frame = new JFrame("Bangladesh Transport System");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Bangladesh Transport System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        // Create a logo
        ImageIcon logo = createLogo();
        if (logo != null) {
            titleLabel.setIcon(logo);
            titleLabel.setIconTextGap(15);
        }
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Login button and user info
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        loginPanel.setOpaque(false);
        
        userLabel = new JLabel("Guest");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        loginButton = new JButton("Login");
        loginButton.setBackground(SECONDARY_COLOR);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            showLoginDialog(parentFrame);
        });
        
        logoutButton = new JButton("Logout");
        logoutButton.setBackground(ACCENT_COLOR);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setVisible(false); // Initially hidden
        logoutButton.addActionListener(e -> {
            // Logout the user
            currentUser = null;
            userLabel.setText("Guest");
            
            // Reset login button
            loginButton.setText("Login");
            
            // Remove all action listeners
            ActionListener[] listeners = loginButton.getActionListeners();
            if (listeners != null) {
                for (ActionListener al : listeners) {
                    loginButton.removeActionListener(al);
                }
            }
            
            // Add fresh login action listener
            loginButton.addActionListener(evt -> {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor((Component) evt.getSource());
                showLoginDialog(parentFrame);
            });
            
            // Hide logout button
            logoutButton.setVisible(false);
            
            // Hide My Bookings button
            if (myBookingsButton != null) {
                myBookingsButton.setVisible(false);
            }
            
            // Update the parent frame
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            if (parentFrame != null) {
                parentFrame.revalidate();
                parentFrame.repaint();
                
                JOptionPane.showMessageDialog(parentFrame, 
                        "You have been logged out successfully.", 
                        "Logout Successful", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
            
            System.out.println("User logged out");
        });
        
        loginPanel.add(userLabel);
        loginPanel.add(Box.createHorizontalStrut(10));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createHorizontalStrut(5));
        loginPanel.add(logoutButton);
        
        headerPanel.add(loginPanel, BorderLayout.EAST);
        
        frame.add(headerPanel, BorderLayout.NORTH);
        
        // Main content
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        
        // Banner
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setBackground(PRIMARY_COLOR);
        bannerPanel.setPreferredSize(new Dimension(1000, 200));
        
        JLabel bannerLabel = new JLabel("Welcome to Bangladesh Transport System");
        bannerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        bannerLabel.setForeground(Color.WHITE);
        bannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bannerLabel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        
        JLabel subtitleLabel = new JLabel("Book your journey across Bangladesh with ease");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
        
        bannerPanel.add(bannerLabel, BorderLayout.CENTER);
        bannerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        mainPanel.add(bannerPanel, BorderLayout.NORTH);
        
        // Content with routes
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel routesTitle = new JLabel("Popular Routes in Bangladesh");
        routesTitle.setFont(new Font("Arial", Font.BOLD, 20));
        contentPanel.add(routesTitle, BorderLayout.NORTH);
        
        // Routes grid
        JPanel routesGrid = new JPanel(new GridLayout(2, 2, 15, 15));
        routesGrid.setBackground(Color.WHITE);
        
        // Sample routes
        routesGrid.add(createRouteCard("Dhaka", "Chittagong", "250"));
        routesGrid.add(createRouteCard("Dhaka", "Sylhet", "240"));
        routesGrid.add(createRouteCard("Chittagong", "Cox's Bazar", "150"));
        routesGrid.add(createRouteCard("Dhaka", "Khulna", "280"));
        
        contentPanel.add(routesGrid, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton loginBtn = new JButton("Login");
        styleButton(loginBtn, PRIMARY_COLOR);
        loginBtn.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            showLoginDialog(parentFrame);
        });
        
        JButton registerBtn = new JButton("Register");
        styleButton(registerBtn, SECONDARY_COLOR);
        registerBtn.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            showRegisterDialog(parentFrame);
        });
        
        JButton viewRoutesBtn = new JButton("View All Routes");
        styleButton(viewRoutesBtn, new Color(108, 117, 125));
        viewRoutesBtn.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            showAllRoutesDialog(parentFrame);
        });
        
        // My Bookings button - only visible when logged in
        myBookingsButton = new JButton("My Bookings");
        styleButton(myBookingsButton, ACCENT_COLOR);
        myBookingsButton.setVisible(currentUser != null); // Only visible when logged in
        myBookingsButton.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            if (currentUser != null) {
                showMyBookingsDialog(parentFrame);
            } else {
                // Shouldn't happen as button is only visible when logged in
                JOptionPane.showMessageDialog(
                    parentFrame,
                    "Please login to view your bookings",
                    "Login Required",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);
        buttonPanel.add(viewRoutesBtn);
        buttonPanel.add(myBookingsButton); // Add My Bookings button to panel
        
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Wrap content in a scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        frame.add(mainPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(PRIMARY_COLOR);
        footerPanel.setPreferredSize(new Dimension(1000, 30));
        
        JLabel copyrightLabel = new JLabel(" 2025 Bangladesh Transport System");
        copyrightLabel.setForeground(Color.WHITE);
        footerPanel.add(copyrightLabel);
        
        frame.add(footerPanel, BorderLayout.SOUTH);
        
        return frame;
    }
    
    private static JPanel createRouteCard(String source, String destination, String distance) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 113, 188), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15))
        );
        
        JLabel routeLabel = new JLabel(source + " to " + destination);
        routeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel distanceLabel = new JLabel("Distance: " + distance + " km");
        distanceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel transportLabel = new JLabel("Available transport: Bus, Train, Taxi");
        transportLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton bookButton = new JButton("Book Now");
        styleButton(bookButton, SECONDARY_COLOR);
        bookButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        bookButton.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            
            if (currentUser == null) {
                // Show login dialog if not logged in
                int option = JOptionPane.showConfirmDialog(
                    parentFrame,
                    "You need to login to book tickets. Would you like to login now?",
                    "Login Required",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                
                if (option == JOptionPane.YES_OPTION) {
                    showLoginDialog(parentFrame);
                    
                    // Check if login was successful
                    if (currentUser != null) {
                        showBookingDialog(parentFrame, source, destination);
                    }
                }
            } else {
                // User already logged in, show booking dialog directly
                showBookingDialog(parentFrame, source, destination);
            }
        });
        
        panel.add(routeLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(distanceLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(transportLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(bookButton);
        
        return panel;
    }
    
    private static void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 40));
    }
    
    private static ImageIcon createLogo() {
        try {
            int size = 40;
            BufferedImage logo = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = logo.createGraphics();
            
            // Set rendering hints for better quality
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw a circular background
            g2d.setColor(Color.WHITE);
            g2d.fillOval(0, 0, size, size);
            
            // Draw a stylized "BT" for Bangladesh Transport
            g2d.setColor(PRIMARY_COLOR);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g2d.drawString("BT", 7, 27);
            
            g2d.dispose();
            return new ImageIcon(logo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Show login dialog
    private static void showLoginDialog(JFrame parent) {
        // Create login dialog
        JDialog loginDialog = new JDialog(parent, "Login", true);
        loginDialog.setSize(400, 250);
        loginDialog.setLocationRelativeTo(parent);
        loginDialog.setLayout(new BorderLayout());
        
        // Create form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Login to Your Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(titleLabel);
        
        formPanel.add(Box.createVerticalStrut(20));
        
        // Username field
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.setOpaque(false);
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setPreferredSize(new Dimension(80, 25));
        JTextField usernameField = new JTextField(20);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        formPanel.add(usernamePanel);
        
        formPanel.add(Box.createVerticalStrut(10));
        
        // Password field
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setOpaque(false);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setPreferredSize(new Dimension(80, 25));
        JPasswordField passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        formPanel.add(passwordPanel);
        
        formPanel.add(Box.createVerticalStrut(20));
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        
        JButton loginButton = new JButton("Login");
        styleButton(loginButton, PRIMARY_COLOR);
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginDialog,
                        "Please enter both username and password",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                Optional<User> userOpt = userService.login(username, password);
                if (userOpt.isPresent()) {
                    currentUser = userOpt.get();
                    
                    // Check if user is admin
                    boolean isAdmin = userService.isAdmin(currentUser);
                    
                    // Update UI to reflect logged in user
                    updateUIForLoggedInUser(parent);
                    
                    loginDialog.dispose();
                    
                    // Show success message
                    JOptionPane.showMessageDialog(parent,
                            "Login successful!\nWelcome, " + currentUser.getName(),
                            "Login Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    
                    // If admin, directly open admin dashboard
                    if (isAdmin) {
                        AdminPanel adminPanel = new AdminPanel(parent, bookingService, userService);
                        adminPanel.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(loginDialog,
                            "Invalid username or password",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(loginDialog,
                        "An error occurred: " + ex.getMessage(),
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener(e -> loginDialog.dispose());
        
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(cancelButton);
        
        formPanel.add(buttonPanel);
        
        loginDialog.add(formPanel, BorderLayout.CENTER);
        loginDialog.setVisible(true);
    }
    
    // Show registration dialog
    private static void showRegisterDialog(JFrame parent) {
        // Create registration dialog
        JDialog registerDialog = new JDialog(parent, "Register", true);
        registerDialog.setSize(400, 400);
        registerDialog.setLocationRelativeTo(parent);
        registerDialog.setLayout(new BorderLayout());
        
        // Create form panel with scroll pane
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Create Your Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(titleLabel);
        
        formPanel.add(Box.createVerticalStrut(20));
        
        // Username field
        JTextField usernameField = new JTextField(20);
        formPanel.add(createFormField("Username:", usernameField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Password field
        JPasswordField passwordField = new JPasswordField(20);
        formPanel.add(createFormField("Password:", passwordField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Confirm password field
        JPasswordField confirmPasswordField = new JPasswordField(20);
        formPanel.add(createFormField("Confirm Password:", confirmPasswordField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Full name field
        JTextField nameField = new JTextField(20);
        formPanel.add(createFormField("Full Name:", nameField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Email field
        JTextField emailField = new JTextField(20);
        formPanel.add(createFormField("Email:", emailField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Phone field
        JTextField phoneField = new JTextField(20);
        formPanel.add(createFormField("Phone:", phoneField));
        formPanel.add(Box.createVerticalStrut(20));
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        
        JButton registerButton = new JButton("Register");
        styleButton(registerButton, SECONDARY_COLOR);
        registerButton.setPreferredSize(new Dimension(100, 30));
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            
            // Validation
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || 
                name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(registerDialog,
                        "Please fill in all fields",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(registerDialog,
                        "Passwords do not match",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                User user = userService.registerUser(username, password, name, email, phone);
                JOptionPane.showMessageDialog(registerDialog,
                        "Registration successful!\nPlease login with your new account.",
                        "Registration Success",
                        JOptionPane.INFORMATION_MESSAGE);
                registerDialog.dispose();
                
                // Show login dialog
                showLoginDialog(parent);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(registerDialog,
                        ex.getMessage(),
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(registerDialog,
                        "An error occurred: " + ex.getMessage(),
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener(e -> registerDialog.dispose());
        
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(cancelButton);
        
        formPanel.add(buttonPanel);
        
        // Add form to scroll pane
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        registerDialog.add(scrollPane, BorderLayout.CENTER);
        registerDialog.setVisible(true);
    }
    
    // Show all routes dialog
    private static void showAllRoutesDialog(JFrame parent) {
        // Create routes dialog
        JDialog routesDialog = new JDialog(parent, "All Routes", true);
        routesDialog.setSize(600, 500);
        routesDialog.setLocationRelativeTo(parent);
        routesDialog.setLayout(new BorderLayout());
        
        // Create content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("All Available Routes in Bangladesh");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Routes table
        String[] columns = {"Source", "Destination", "Distance (km)", "Transport Options"};
        Object[][] data = {
            {"Dhaka", "Chittagong", "250", "Bus, Train"},
            {"Dhaka", "Sylhet", "240", "Bus, Train"},
            {"Dhaka", "Rajshahi", "260", "Bus, Train"},
            {"Dhaka", "Khulna", "280", "Bus, Train"},
            {"Dhaka", "Barisal", "190", "Bus, Launch"},
            {"Dhaka", "Rangpur", "300", "Bus, Train"},
            {"Chittagong", "Cox's Bazar", "150", "Bus, Taxi"},
            {"Chittagong", "Bandarban", "90", "Bus, Taxi"},
            {"Chittagong", "Rangamati", "80", "Bus, Taxi"},
            {"Sylhet", "Sunamganj", "70", "Bus, Taxi"},
            {"Dhaka", "Mymensingh", "120", "Bus, Train"},
            {"Dhaka", "Tangail", "100", "Bus"},
            {"Dhaka", "Comilla", "100", "Bus, Train"},
            {"Khulna", "Jessore", "80", "Bus"},
            {"Rajshahi", "Bogra", "90", "Bus"}
        };
        
        JTable routesTable = new JTable(data, columns);
        routesTable.setRowHeight(25);
        routesTable.setFont(new Font("Arial", Font.PLAIN, 14));
        routesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        routesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(routesTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        
        JButton bookButton = new JButton("Book Selected Route");
        styleButton(bookButton, PRIMARY_COLOR);
        bookButton.addActionListener(e -> {
            int selectedRow = routesTable.getSelectedRow();
            if (selectedRow != -1) {
                String source = (String) routesTable.getValueAt(selectedRow, 0);
                String destination = (String) routesTable.getValueAt(selectedRow, 1);
                
                if (currentUser == null) {
                    // Show login dialog if not logged in
                    int option = JOptionPane.showConfirmDialog(
                        routesDialog,
                        "You need to login to book tickets. Would you like to login now?",
                        "Login Required",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                    
                    if (option == JOptionPane.YES_OPTION) {
                        routesDialog.dispose(); // Close routes dialog
                        showLoginDialog(parent);
                        
                        // Check if login was successful
                        if (currentUser != null) {
                            showBookingDialog(parent, source, destination);
                        }
                    }
                } else {
                    // User already logged in, show booking dialog directly
                    routesDialog.dispose(); // Close routes dialog
                    showBookingDialog(parent, source, destination);
                }
            } else {
                JOptionPane.showMessageDialog(routesDialog, 
                        "Please select a route first", 
                        "No Selection", 
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(Color.LIGHT_GRAY);
        closeButton.setForeground(Color.BLACK);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setPreferredSize(new Dimension(100, 30));
        closeButton.addActionListener(e -> routesDialog.dispose());
        
        buttonPanel.add(bookButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(closeButton);
        
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        routesDialog.add(contentPanel, BorderLayout.CENTER);
        routesDialog.setVisible(true);
    }
    
    // Helper method to create form fields
    private static JPanel createFormField(String label, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setPreferredSize(new Dimension(120, 25));
        
        panel.add(labelComponent);
        panel.add(field);
        
        return panel;
    }
    
    // Update UI to reflect logged in user
    private static void updateUIForLoggedInUser(JFrame parent) {
        if (currentUser != null) {
            // Update user label
            userLabel.setText(currentUser.getName());
            
            // Update login button to show account info
            loginButton.setText("My Account");
            
            // Remove existing action listeners
            ActionListener[] listeners = loginButton.getActionListeners();
            if (listeners != null) {
                for (ActionListener al : listeners) {
                    loginButton.removeActionListener(al);
                }
            }
            
            // Add new action for account menu
            loginButton.addActionListener(e -> {
                // Show account options menu
                JPopupMenu accountMenu = new JPopupMenu();
                
                // User info option
                JMenuItem profileItem = new JMenuItem("View Profile");
                profileItem.addActionListener(evt -> {
                    // Show user profile
                    String userType = userService.isAdmin(currentUser) ? "Admin" : "User";
                    
                    JOptionPane.showMessageDialog(parent, 
                            "User Profile (" + userType + "):\n" +
                            "Username: " + currentUser.getUsername() + "\n" +
                            "Name: " + currentUser.getName() + "\n" +
                            "Email: " + currentUser.getEmail() + "\n" +
                            "Phone: " + currentUser.getPhone(),
                            "My Profile",
                            JOptionPane.INFORMATION_MESSAGE);
                });
                
                // My bookings option
                JMenuItem bookingsItem = new JMenuItem("My Bookings");
                bookingsItem.addActionListener(evt -> {
                    showMyBookingsDialog(parent);
                });
                
                // Add items to menu
                accountMenu.add(profileItem);
                accountMenu.add(bookingsItem);
                
                // Add separator and admin options if user is admin
                if (userService.isAdmin(currentUser)) {
                    accountMenu.addSeparator();
                    
                    JMenuItem adminItem = new JMenuItem("Admin Dashboard");
                    adminItem.addActionListener(evt -> {
                        // Check if AdminPanel is already visible
                        boolean alreadyOpen = false;
                        Window[] windows = Window.getWindows();
                        for (Window window : windows) {
                            if (window instanceof AdminPanel && window.isVisible()) {
                                window.toFront();
                                alreadyOpen = true;
                                break;
                            }
                        }
                        
                        // Only create a new admin panel if one isn't already open
                        if (!alreadyOpen) {
                            AdminPanel adminPanel = new AdminPanel(parent, bookingService, userService);
                            adminPanel.setVisible(true);
                        }
                    });
                    
                    accountMenu.add(adminItem);
                }
                
                // Show menu below the button
                accountMenu.show(loginButton, 0, loginButton.getHeight());
            });
            
            // Show logout button
            logoutButton.setVisible(true);
            
            // Show My Bookings button
            if (myBookingsButton != null) {
                myBookingsButton.setVisible(true);
            }
            
            // Update the parent frame
            parent.revalidate();
            parent.repaint();
            
            System.out.println("UI updated for user: " + currentUser.getUsername());
        }
    }
    
    // Show booking dialog
    private static void showBookingDialog(JFrame parent, String source, String destination) {
        // Create booking dialog
        JDialog bookingDialog = new JDialog(parent, "Book Ticket: " + source + " to " + destination, true);
        bookingDialog.setSize(800, 700);
        bookingDialog.setLocationRelativeTo(parent);
        bookingDialog.setLayout(new BorderLayout());

        // Create main panel with scroll
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Book Your Ticket");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Route information panel
        JPanel routePanel = new JPanel(new GridLayout(3, 2, 10, 5));
        routePanel.setBorder(BorderFactory.createTitledBorder("Route Details"));
        routePanel.setBackground(Color.WHITE);

        routePanel.add(new JLabel("Source:"));
        routePanel.add(new JLabel(source));
        routePanel.add(new JLabel("Destination:"));
        routePanel.add(new JLabel(destination));
        
        int distance = bookingService.getRouteDistance(source, destination);
        routePanel.add(new JLabel("Distance:"));
        routePanel.add(new JLabel(distance + " km"));

        mainPanel.add(routePanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Booking details panel
        JPanel bookingDetailsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        bookingDetailsPanel.setBorder(BorderFactory.createTitledBorder("Booking Details"));
        bookingDetailsPanel.setBackground(Color.WHITE);

        // Transport type
        bookingDetailsPanel.add(new JLabel("Transport Type:"));
        JComboBox<String> transportCombo = new JComboBox<>(new String[]{"Bus"});
        bookingDetailsPanel.add(transportCombo);

        // Journey date
        bookingDetailsPanel.add(new JLabel("Journey Date:"));
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setValue(new Date());
        bookingDetailsPanel.add(dateSpinner);

        // Number of seats
        bookingDetailsPanel.add(new JLabel("Number of Seats:"));
        SpinnerNumberModel seatsModel = new SpinnerNumberModel(1, 1, 4, 1);
        JSpinner seatsSpinner = new JSpinner(seatsModel);
        bookingDetailsPanel.add(seatsSpinner);

        mainPanel.add(bookingDetailsPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Seat selection panel
        JPanel seatSelectionContainer = new JPanel(new BorderLayout());
        seatSelectionContainer.setBorder(BorderFactory.createTitledBorder("Select Seats"));
        seatSelectionContainer.setBackground(Color.WHITE);

        // Create seat selection panel (4x8 grid = 32 seats)
        SeatSelectionPanel seatSelection = new SeatSelectionPanel(4, 8, 4);
        
        // Add some demo booked seats
        List<Integer> bookedSeats = new ArrayList<>();
        bookedSeats.add(3);
        bookedSeats.add(7);
        bookedSeats.add(12);
        bookedSeats.add(25);
        seatSelection.setBookedSeats(bookedSeats);

        // Sync seat spinner with seat selection
        seatsSpinner.addChangeListener(e -> {
            int maxSeats = (Integer) seatsSpinner.getValue();
            seatSelection.setMaxSelectable(maxSeats);
        });

        // Sync seat selection with spinner
        seatSelection.addSeatSelectionListener(selectedCount -> {
            seatsSpinner.setValue(selectedCount);
        });

        seatSelectionContainer.add(seatSelection, BorderLayout.CENTER);
        mainPanel.add(seatSelectionContainer);
        mainPanel.add(Box.createVerticalStrut(15));

        // Fare panel
        JPanel farePanel = new JPanel();
        farePanel.setLayout(new BoxLayout(farePanel, BoxLayout.Y_AXIS));
        farePanel.setBorder(BorderFactory.createTitledBorder("Fare Details"));
        farePanel.setBackground(Color.WHITE);

        JLabel baseFareLabel = new JLabel("Base Fare: ");
        JLabel totalFareLabel = new JLabel("Total Fare: ");
        farePanel.add(baseFareLabel);
        farePanel.add(Box.createVerticalStrut(5));
        farePanel.add(totalFareLabel);

        // Update fare when transport type or number of seats changes
        ActionListener fareUpdater = e -> {
            String selectedTransport = (String) transportCombo.getSelectedItem();
            int selectedSeats = (Integer) seatsSpinner.getValue();
            double farePerKm = bookingService.TRANSPORT_RATES.get(selectedTransport);
            double baseFare = distance * farePerKm;
            double totalFare = baseFare * selectedSeats;

            baseFareLabel.setText(String.format("Base Fare: %.2f BDT per seat", baseFare));
            totalFareLabel.setText(String.format("Total Fare: %.2f BDT for %d seats", totalFare, selectedSeats));
        };

        transportCombo.addActionListener(fareUpdater);
        seatsSpinner.addChangeListener(e -> fareUpdater.actionPerformed(null));
        fareUpdater.actionPerformed(null);  // Initial update

        mainPanel.add(farePanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        JButton confirmButton = new JButton("Confirm Booking");
        styleButton(confirmButton, SECONDARY_COLOR);
        confirmButton.addActionListener(e -> {
            try {
                // Get booking details
                String selectedTransport = (String) transportCombo.getSelectedItem();
                int selectedSeats = (Integer) seatsSpinner.getValue();
                Date selectedDate = (Date) dateSpinner.getValue();
                LocalDate journeyDate = selectedDate.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();

                // Validate seat selection
                List<Integer> selectedSeatNumbers = seatSelection.getSelectedSeatNumbers();
                if (selectedSeatNumbers.size() != selectedSeats) {
                    JOptionPane.showMessageDialog(bookingDialog,
                        "Please select exactly " + selectedSeats + " seats.",
                        "Invalid Selection",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Calculate fare
                double farePerKm = bookingService.TRANSPORT_RATES.get(selectedTransport);
                double totalFare = distance * farePerKm * selectedSeats;

                // Create booking
                Booking booking = new Booking();
                booking.setBookingId("BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                booking.setUserId(currentUser.getUserId());
                booking.setSource(source);
                booking.setDestination(destination);
                booking.setTransportType(selectedTransport);
                booking.setJourneyDate(journeyDate);
                booking.setSeats(selectedSeats);
                booking.setFare(totalFare);
                booking.setStatus("CONFIRMED");
                booking.setSeatNumbers(selectedSeatNumbers);

                // Add booking to user and save
                currentUser.addBooking(booking);
                userService.updateUser(currentUser);

                // Show confirmation
                JOptionPane.showMessageDialog(bookingDialog,
                    "Booking confirmed!\n\n" +
                    "Booking ID: " + booking.getBookingId() + "\n" +
                    "Route: " + source + " to " + destination + "\n" +
                    "Transport: " + selectedTransport + "\n" +
                    "Journey Date: " + journeyDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) + "\n" +
                    "Seats: " + selectedSeats + "\n" +
                    "Seat Numbers: " + selectedSeatNumbers + "\n" +
                    "Total Fare: " + String.format("%.2f BDT", totalFare),
                    "Booking Successful",
                    JOptionPane.INFORMATION_MESSAGE);

                bookingDialog.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(bookingDialog,
                    "Error creating booking: " + ex.getMessage(),
                    "Booking Error",
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> bookingDialog.dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel);

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        bookingDialog.add(scrollPane);
        bookingDialog.setVisible(true);
    }
    
    // Show user's bookings dialog
    private static void showMyBookingsDialog(JFrame parent) {
        // Create bookings dialog
        JDialog bookingsDialog = new JDialog(parent, "My Bookings", true);
        bookingsDialog.setSize(1000, 500);
        bookingsDialog.setLocationRelativeTo(parent);
        bookingsDialog.setLayout(new BorderLayout());

        // Create content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        // Title
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("My Bookings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(PRIMARY_COLOR);

        // Filter options
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.setOpaque(false);

        JLabel filterLabel = new JLabel("Filter by status:");
        JComboBox<String> statusFilter = new JComboBox<>(new String[]{"All", "CONFIRMED", "CANCELLED"});
        statusFilter.setPreferredSize(new Dimension(120, 25));

        filterPanel.add(filterLabel);
        filterPanel.add(statusFilter);

        titlePanel.add(titleLabel, BorderLayout.WEST);
        titlePanel.add(filterPanel, BorderLayout.EAST);

        contentPanel.add(titlePanel, BorderLayout.NORTH);

        // Bookings table
        String[] columns = {
            "Booking ID", "Route", "Transport", "Journey Date", 
            "Seats", "Seat Numbers", "Fare", "Status"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable bookingsTable = new JTable(tableModel);
        bookingsTable.setRowHeight(30);
        bookingsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        bookingsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        bookingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookingsTable.setFillsViewportHeight(true);

        // Add custom renderer for row colors
        bookingsTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                
                String status = (String) table.getModel().getValueAt(row, 7); // Status is in column 7
                
                if (!isSelected) {
                    if ("CANCELLED".equals(status)) {
                        c.setBackground(new Color(255, 200, 200)); // Light red
                        c.setForeground(Color.BLACK);
                    } else if ("CONFIRMED".equals(status)) {
                        c.setBackground(new Color(200, 255, 200)); // Light green
                        c.setForeground(Color.BLACK);
                    } else {
                        c.setBackground(table.getBackground());
                        c.setForeground(table.getForeground());
                    }
                } else {
                    // Keep default selection colors
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                }
                
                return c;
            }
        });

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Load bookings - make it final to avoid linter errors
        final List<Booking> userBookings = currentUser != null ? 
                new ArrayList<>(currentUser.getBookings()) : new ArrayList<>();
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        
        // Load data into table
        for (Booking booking : userBookings) {
            String formattedDate = booking.getJourneyDate().format(dateFormatter);
            String formattedFare = String.format("%.2f BDT", booking.getFare());
            String route = booking.getSource() + " → " + booking.getDestination();
            String seatNumbers = booking.getSeatNumbers() != null ? 
                    booking.getSeatNumbers().toString().replaceAll("[\\[\\]]", "") : "";
            
            tableModel.addRow(new Object[]{
                booking.getBookingId(),
                route,
                booking.getTransportType(),
                formattedDate,
                booking.getSeats(),
                seatNumbers,
                formattedFare,
                booking.getStatus()
            });
        }
        
        // Add filter listener
        statusFilter.addActionListener(e -> {
            String selectedStatus = (String) statusFilter.getSelectedItem();
            tableModel.setRowCount(0); // Clear table
            
            for (Booking booking : userBookings) {
                if ("All".equals(selectedStatus) || booking.getStatus().equals(selectedStatus)) {
                    String formattedDate = booking.getJourneyDate().format(dateFormatter);
                    String formattedFare = String.format("%.2f BDT", booking.getFare());
                    String route = booking.getSource() + " → " + booking.getDestination();
                    String seatNumbers = booking.getSeatNumbers() != null ? 
                            booking.getSeatNumbers().toString().replaceAll("[\\[\\]]", "") : "";
                    
                    tableModel.addRow(new Object[]{
                        booking.getBookingId(),
                        route,
                        booking.getTransportType(),
                        formattedDate,
                        booking.getSeats(),
                        seatNumbers,
                        formattedFare,
                        booking.getStatus()
                    });
                }
            }
        });
        
        // Action panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.setOpaque(false);
        
        JButton cancelBookingBtn = new JButton("Cancel Selected Booking");
        styleButton(cancelBookingBtn, ACCENT_COLOR);
        cancelBookingBtn.addActionListener(e -> {
            int selectedRow = bookingsTable.getSelectedRow();
            if (selectedRow != -1) {
                String bookingId = (String) bookingsTable.getValueAt(selectedRow, 0);
                String status = (String) bookingsTable.getValueAt(selectedRow, 7); // Fix: Changed index from 6 to 7 to match column index
                
                if ("CONFIRMED".equals(status)) {
                    int option = JOptionPane.showConfirmDialog(
                        bookingsDialog,
                        "Are you sure you want to cancel this booking?",
                        "Cancel Booking",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                    
                    if (option == JOptionPane.YES_OPTION) {
                        // Find and update the booking in the user's bookings list
                        boolean cancelled = false;
                        if (currentUser != null) {
                            for (Booking booking : currentUser.getBookings()) {
                                if (booking.getBookingId().equals(bookingId)) {
                                    booking.setStatus("CANCELLED");
                                    cancelled = true;
                                    break;
                                }
                            }
                            
                            if (cancelled) {
                                // Update the user data
                                userService.updateUser(currentUser);
                                
                                // Update the booking service
                                bookingService.cancelBooking(bookingId);
                                
                                // Update table
                                tableModel.setValueAt("CANCELLED", selectedRow, 7); // Fix: Changed index from 6 to 7
                                
                                JOptionPane.showMessageDialog(
                                    bookingsDialog,
                                    "Booking cancelled successfully.",
                                    "Booking Cancelled",
                                    JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(
                        bookingsDialog,
                        "Only confirmed bookings can be cancelled.",
                        "Cannot Cancel",
                        JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(
                    bookingsDialog,
                    "Please select a booking to cancel.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBackground(new Color(108, 117, 125));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorderPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(e -> {
            // Reload table with the updated user data
            tableModel.setRowCount(0);
            
            List<Booking> refreshedBookings = currentUser != null ? 
                    currentUser.getBookings() : new ArrayList<>();
                    
            for (Booking booking : refreshedBookings) {
                if ("All".equals(statusFilter.getSelectedItem()) || 
                    booking.getStatus().equals(statusFilter.getSelectedItem())) {
                    
                    String formattedDate = booking.getJourneyDate().format(dateFormatter);
                    String formattedFare = String.format("%.2f BDT", booking.getFare());
                    String route = booking.getSource() + " → " + booking.getDestination();
                    
                    tableModel.addRow(new Object[]{
                        booking.getBookingId(),
                        route,
                        booking.getTransportType(),
                        formattedDate,
                        booking.getSeats(),
                        formattedFare,
                        booking.getStatus()
                    });
                }
            }
        });
        
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(Color.LIGHT_GRAY);
        closeButton.setForeground(Color.BLACK);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> bookingsDialog.dispose());
        
        actionPanel.add(cancelBookingBtn);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(refreshBtn);
        actionPanel.add(Box.createHorizontalStrut(10));
        actionPanel.add(closeButton);
        
        contentPanel.add(actionPanel, BorderLayout.SOUTH);
        
        // Add empty message if no bookings
        if (userBookings.isEmpty()) {
            JPanel emptyPanel = new JPanel(new BorderLayout());
            emptyPanel.setOpaque(false);
            
            JLabel emptyLabel = new JLabel("You don't have any bookings yet.");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            
            emptyPanel.add(emptyLabel, BorderLayout.CENTER);
            contentPanel.add(emptyPanel, BorderLayout.CENTER);
        }
        
        bookingsDialog.add(contentPanel, BorderLayout.CENTER);
        bookingsDialog.setVisible(true);
    }
}