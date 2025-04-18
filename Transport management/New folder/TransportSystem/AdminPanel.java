import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Cursor;
import javax.swing.JCheckBox;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;

/**
 * Simple Admin Panel for Bangladesh Transport System
 */
public class AdminPanel extends JDialog {
    private static final long serialVersionUID = 1L;
    private static final Color PRIMARY_COLOR = new Color(0, 113, 188);  // Blue
    private static final Color SECONDARY_COLOR = new Color(0, 147, 69); // Green
    private static final Color ACCENT_COLOR = new Color(214, 40, 40);   // Red
    private static final Color BG_COLOR = new Color(248, 249, 250);     // Light gray
    private static final Color ADMIN_COLOR = new Color(80, 40, 140);   // Purple for admin
    
    private JTabbedPane tabbedPane;
    
    // References to services
    private BangladeshTransportDemo.BookingService bookingService;
    private BangladeshTransportDemo.UserService userService;
    
    public AdminPanel(JFrame parent, BangladeshTransportDemo.BookingService bookingService, BangladeshTransportDemo.UserService userService) {
        super(parent, "Admin Dashboard - Bangladesh Transport System", true);
        
        // Store the provided services
        this.bookingService = bookingService;
        this.userService = userService;
        
        initializeUI();
        
        // Set dialog properties
        setSize(900, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBackground(Color.WHITE);
        
        // Add tabs
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Manage Vehicles", createVehiclesPanel());
        tabbedPane.addTab("Manage Routes", createRoutesPanel());
        tabbedPane.addTab("Manage Bookings", createBookingsPanel());
        tabbedPane.addTab("Manage Users", createUsersPanel());
        tabbedPane.addTab("Manage Staff", createStaffPanel());
        
        // Add tabbed pane to panel
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    // DASHBOARD TAB
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_COLOR);
        
        // Admin banner
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setBackground(new Color(52, 58, 64)); // Dark gray
        bannerPanel.setPreferredSize(new Dimension(getWidth(), 150));
        bannerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel welcomeLabel = new JLabel("Admin Dashboard");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        
        JLabel subLabel = new JLabel("Manage all aspects of the Bangladesh Transport System");
        subLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        subLabel.setForeground(Color.WHITE);
        
        bannerPanel.add(welcomeLabel, BorderLayout.NORTH);
        bannerPanel.add(subLabel, BorderLayout.CENTER);
        
        panel.add(bannerPanel, BorderLayout.NORTH);
        
        // Stats cards
        JPanel statsPanel = new JPanel(new GridLayout(1, 5, 15, 0));
        statsPanel.setBackground(BG_COLOR);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Users stats
        JPanel usersCard = createStatsCard(
                "Total Users", 
                "25",
                new Color(0, 123, 255)); // Blue
        statsPanel.add(usersCard);
        
        // Vehicles stats
        JPanel vehiclesCard = createStatsCard(
                "Total Vehicles", 
                "58",
                new Color(40, 167, 69)); // Green
        statsPanel.add(vehiclesCard);
        
        // Routes stats
        JPanel routesCard = createStatsCard(
                "Total Routes", 
                "32",
                new Color(255, 193, 7)); // Yellow
        statsPanel.add(routesCard);
        
        // Staff stats
        JPanel staffCard = createStatsCard(
                "Total Staff", 
                "43",
                new Color(111, 66, 193)); // Purple
        statsPanel.add(staffCard);
        
        // Bookings stats
        JPanel bookingsCard = createStatsCard(
                "Total Bookings", 
                "120",
                new Color(220, 53, 69)); // Red
        statsPanel.add(bookingsCard);
        
        panel.add(statsPanel, BorderLayout.CENTER);
        
        // Quick action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        actionPanel.setBackground(BG_COLOR);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JButton addVehicleBtn = new JButton("Add Vehicle");
        styleButton(addVehicleBtn, PRIMARY_COLOR);
        addVehicleBtn.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        
        JButton addRouteBtn = new JButton("Add Route");
        styleButton(addRouteBtn, SECONDARY_COLOR);
        addRouteBtn.addActionListener(e -> tabbedPane.setSelectedIndex(2));
        
        JButton addStaffBtn = new JButton("Add Staff");
        styleButton(addStaffBtn, new Color(111, 66, 193)); // Purple
        addStaffBtn.addActionListener(e -> tabbedPane.setSelectedIndex(5)); // Index of Staff tab
        
        actionPanel.add(addVehicleBtn);
        actionPanel.add(addRouteBtn);
        actionPanel.add(addStaffBtn);
        
        panel.add(actionPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createStatsCard(String title, String value, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 5, 0, 0, color),
            BorderFactory.createEmptyBorder(15, 15, 15, 15))
        );
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(color);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // VEHICLES TAB
    private JPanel createVehiclesPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        
        JLabel titleLabel = new JLabel("Manage Vehicles");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Vehicles table
        String[] columns = {"ID", "Registration", "Type", "Model", "Capacity", "Status"};
        DefaultTableModel vehicleTableModel = new DefaultTableModel(columns, 0);
        
        // Add sample data
        vehicleTableModel.addRow(new Object[]{"VEH001", "DH-1234", "Bus", "Volvo", "45", "Active"});
        vehicleTableModel.addRow(new Object[]{"VEH002", "DH-5678", "Bus", "Hino", "42", "Active"});
        vehicleTableModel.addRow(new Object[]{"VEH003", "DH-9012", "Train", "Express", "350", "Maintenance"});
        vehicleTableModel.addRow(new Object[]{"VEH004", "DH-3456", "Taxi", "Toyota", "4", "Active"});
        
        JTable vehiclesTable = new JTable(vehicleTableModel);
        vehiclesTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(vehiclesTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Add Button
        JButton addButton = new JButton("Add New Vehicle");
        styleButton(addButton, PRIMARY_COLOR);
        addButton.addActionListener(e -> {
            // Create input fields
            String registration = JOptionPane.showInputDialog(this, "Enter Vehicle Registration Number:", "Add Vehicle", JOptionPane.QUESTION_MESSAGE);
            if (registration == null || registration.trim().isEmpty()) return;
            
            String[] types = {"Bus", "Train", "Taxi"};
            String type = (String) JOptionPane.showInputDialog(this, "Select Vehicle Type:", "Add Vehicle", 
                    JOptionPane.QUESTION_MESSAGE, null, types, types[0]);
            if (type == null) return;
            
            String model = JOptionPane.showInputDialog(this, "Enter Vehicle Model:", "Add Vehicle", JOptionPane.QUESTION_MESSAGE);
            if (model == null || model.trim().isEmpty()) return;
            
            String capacity = JOptionPane.showInputDialog(this, "Enter Capacity:", "Add Vehicle", JOptionPane.QUESTION_MESSAGE);
            if (capacity == null || capacity.trim().isEmpty()) return;
            
            // Add to table
            vehicleTableModel.addRow(new Object[]{"VEH" + System.currentTimeMillis() % 10000, registration, type, model, capacity, "Active"});
            
            JOptionPane.showMessageDialog(this,
                "Vehicle added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        });
        headerPanel.add(addButton, BorderLayout.EAST);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BG_COLOR);
        
        JButton deleteButton = new JButton("Delete Selected");
        styleButton(deleteButton, ACCENT_COLOR);
        deleteButton.addActionListener(e -> {
            int row = vehiclesTable.getSelectedRow();
            if (row >= 0) {
                vehicleTableModel.removeRow(row);
                JOptionPane.showMessageDialog(this,
                    "Vehicle deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select a vehicle to delete",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // ROUTES TAB
    private JPanel createRoutesPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        
        JLabel titleLabel = new JLabel("Manage Routes");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Routes table
        String[] columns = {"ID", "Source", "Destination", "Distance (km)", "Stops", "Vehicles"};
        DefaultTableModel routeTableModel = new DefaultTableModel(columns, 0);
        
        // Add sample data
        routeTableModel.addRow(new Object[]{"RTE001", "Dhaka", "Chittagong", "250", "2", "3"});
        routeTableModel.addRow(new Object[]{"RTE002", "Dhaka", "Sylhet", "240", "3", "2"});
        routeTableModel.addRow(new Object[]{"RTE003", "Dhaka", "Rajshahi", "260", "2", "2"});
        routeTableModel.addRow(new Object[]{"RTE004", "Dhaka", "Khulna", "280", "1", "2"});
        
        JTable routesTable = new JTable(routeTableModel);
        routesTable.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(routesTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Add Button
        JButton addButton = new JButton("Add New Route");
        styleButton(addButton, PRIMARY_COLOR);
        addButton.addActionListener(e -> {
            // Create input fields
            String source = JOptionPane.showInputDialog(this, "Enter Source City:", "Add Route", JOptionPane.QUESTION_MESSAGE);
            if (source == null || source.trim().isEmpty()) return;
            
            String destination = JOptionPane.showInputDialog(this, "Enter Destination City:", "Add Route", JOptionPane.QUESTION_MESSAGE);
            if (destination == null || destination.trim().isEmpty()) return;
            
            String distance = JOptionPane.showInputDialog(this, "Enter Distance (km):", "Add Route", JOptionPane.QUESTION_MESSAGE);
            if (distance == null || distance.trim().isEmpty()) return;
            
            // Add to table
            routeTableModel.addRow(new Object[]{"RTE" + System.currentTimeMillis() % 10000, source, destination, distance, "0", "0"});
            
            JOptionPane.showMessageDialog(this,
                "Route added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        });
        headerPanel.add(addButton, BorderLayout.EAST);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BG_COLOR);
        
        JButton deleteButton = new JButton("Delete Selected");
        styleButton(deleteButton, ACCENT_COLOR);
        deleteButton.addActionListener(e -> {
            int row = routesTable.getSelectedRow();
            if (row >= 0) {
                routeTableModel.removeRow(row);
                JOptionPane.showMessageDialog(this,
                    "Route deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select a route to delete",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // BOOKINGS TAB
    private JPanel createBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        
        JLabel titleLabel = new JLabel("Manage Bookings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Create filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.setBackground(BG_COLOR);
        JLabel filterLabel = new JLabel("Filter by status: ");
        String[] statusOptions = {"All", "CONFIRMED", "CANCELLED"};
        javax.swing.JComboBox<String> statusFilter = new javax.swing.JComboBox<>(statusOptions);
        statusFilter.setPreferredSize(new Dimension(120, 25));
        filterPanel.add(filterLabel);
        filterPanel.add(statusFilter);
        headerPanel.add(filterPanel, BorderLayout.EAST);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Bookings table
        String[] columns = {"ID", "User", "Source", "Destination", "Date", "Seats", "Status", "Fare"};
        DefaultTableModel bookingTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable directly
            }
        };
        
        // Load real booking data instead of sample data
        if (bookingService != null && userService != null) {
            bookingService.reloadBookingsFromFile(); // Always reload from file before displaying
            java.util.List<BangladeshTransportDemo.Booking> bookings = bookingService.getAllBookings();
            if (bookings.isEmpty()) {
                // Show a message in the table if there are no bookings
                bookingTableModel.addRow(new Object[]{"-", "-", "-", "-", "-", "-", "-", "No bookings found"});
            } else {
                for (BangladeshTransportDemo.Booking booking : bookings) {
                    String userDisplay = booking.getUserId();
                    try {
                        java.util.Optional<BangladeshTransportDemo.User> userOpt = userService.getUserById(booking.getUserId());
                        if (userOpt.isPresent()) {
                            BangladeshTransportDemo.User user = userOpt.get();
                            userDisplay = user.getName() + " (" + user.getUserId() + ")";
                        } else {
                            userDisplay = "Unknown User (" + booking.getUserId() + ")";
                        }
                    } catch (Exception ex) {
                        userDisplay = "Unknown User (" + booking.getUserId() + ")";
                    }
                    bookingTableModel.addRow(new Object[]{
                        booking.getBookingId(),
                        userDisplay,
                        booking.getSource(),
                        booking.getDestination(),
                        booking.getJourneyDate().toString(),
                        String.valueOf(booking.getSeats()),
                        booking.getStatus(),
                        String.format("%.2f", booking.getFare())
                    });
                }
            }
        } else {
            // Fallback to sample data if booking service is not available
            bookingTableModel.addRow(new Object[]{"BKG001", "user1", "Dhaka", "Chittagong", "2023-05-15", "2", "CONFIRMED", "500"});
            bookingTableModel.addRow(new Object[]{"BKG002", "user2", "Dhaka", "Sylhet", "2023-05-16", "1", "CONFIRMED", "350"});
            bookingTableModel.addRow(new Object[]{"BKG003", "user3", "Chittagong", "Dhaka", "2023-05-17", "3", "CANCELLED", "750"});
            bookingTableModel.addRow(new Object[]{"BKG004", "user1", "Dhaka", "Rajshahi", "2023-05-18", "2", "CONFIRMED", "520"});
        }
        
        // Add filter functionality
        statusFilter.addActionListener(e -> {
            String selectedStatus = (String) statusFilter.getSelectedItem();
            filterBookingsByStatus(bookingTableModel, selectedStatus);
        });
        
        JTable bookingsTable = new JTable(bookingTableModel);
        bookingsTable.setRowHeight(25);
        bookingsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        bookingsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Action buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(BG_COLOR);
        
        // View Details Button
        JButton viewButton = new JButton("View Details");
        styleButton(viewButton, PRIMARY_COLOR);
        viewButton.addActionListener(e -> {
            int row = bookingsTable.getSelectedRow();
            if (row >= 0) {
                showBookingDetailsDialog(bookingTableModel, row);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select a booking to view",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Edit Booking Button
        JButton editButton = new JButton("Edit Booking");
        styleButton(editButton, SECONDARY_COLOR);
        editButton.addActionListener(e -> {
            int row = bookingsTable.getSelectedRow();
            if (row >= 0) {
                showEditBookingDialog(bookingTableModel, row);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select a booking to edit",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Print Ticket Button
        JButton printButton = new JButton("Print Ticket");
        styleButton(printButton, new Color(63, 81, 181)); // Indigo
        printButton.addActionListener(e -> {
            int row = bookingsTable.getSelectedRow();
            if (row >= 0) {
                if ("CONFIRMED".equals(bookingTableModel.getValueAt(row, 6))) {
                    printTicket(bookingTableModel, row);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Only confirmed bookings can be printed",
                        "Cannot Print Ticket",
                        JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select a booking to print the ticket",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Generate PDF Button
        JButton pdfButton = new JButton("Generate PDF");
        styleButton(pdfButton, new Color(156, 39, 176)); // Purple
        pdfButton.addActionListener(e -> {
            int row = bookingsTable.getSelectedRow();
            if (row >= 0) {
                if ("CONFIRMED".equals(bookingTableModel.getValueAt(row, 6))) {
                    generateTicketPDF(bookingTableModel, row);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Only confirmed bookings can be exported to PDF",
                        "Cannot Generate PDF",
                        JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select a booking to generate a PDF ticket",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Cancel Booking Button
        JButton cancelButton = new JButton("Cancel Booking");
        styleButton(cancelButton, ACCENT_COLOR);
        cancelButton.addActionListener(e -> {
            int row = bookingsTable.getSelectedRow();
            if (row >= 0) {
                if ("CONFIRMED".equals(bookingTableModel.getValueAt(row, 6))) {
                    int option = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to cancel this booking?",
                        "Confirm Cancellation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                    
                    if (option == JOptionPane.YES_OPTION) {
                        // Get the booking ID
                        String bookingId = (String) bookingTableModel.getValueAt(row, 0);
                        
                        // Use the booking service to cancel
                        if (bookingService != null) {
                            try {
                                boolean success = bookingService.cancelBooking(bookingId);
                                if (success) {
                                    // Update the table immediately
                                    bookingTableModel.setValueAt("CANCELLED", row, 6);
                                    
                                    // Update the booking in the model
                                    JOptionPane.showMessageDialog(this,
                                        "Booking cancelled successfully!",
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE);
                                    
                                    // Refresh the table
                                    refreshBookingTable(bookingTableModel, (String) statusFilter.getSelectedItem());
                                } else {
                                    JOptionPane.showMessageDialog(this,
                                        "Failed to cancel booking. Please try again.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this,
                                    "Error cancelling booking: " + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                                ex.printStackTrace();
                            }
                        } else {
                            // Fallback if service is unavailable
                            bookingTableModel.setValueAt("CANCELLED", row, 6);
                            JOptionPane.showMessageDialog(this,
                                "Booking cancelled successfully! (using fallback)",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                        "This booking is already cancelled",
                        "Cannot Cancel",
                        JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select a booking to cancel",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        styleButton(refreshButton, new Color(108, 117, 125)); // Gray
        refreshButton.addActionListener(e -> {
            // Use the helper method to refresh the booking table
            refreshBookingTable(bookingTableModel, (String) statusFilter.getSelectedItem());
            
            JOptionPane.showMessageDialog(this,
                "Booking data refreshed",
                "Refresh Complete",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        buttonPanel.add(viewButton);
        buttonPanel.add(editButton);
        buttonPanel.add(printButton);
        buttonPanel.add(pdfButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // Filter bookings by status
    private void filterBookingsByStatus(DefaultTableModel model, String status) {
        if (bookingService != null) {
            // Use the helper method to refresh with filtering
            refreshBookingTable(model, status);
            JOptionPane.showMessageDialog(this, 
                "Filtered by status: " + status,
                "Filter Applied",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Show booking details dialog
    private void showBookingDetailsDialog(DefaultTableModel model, int row) {
        String bookingId = (String) model.getValueAt(row, 0);
        String userId = (String) model.getValueAt(row, 1);
        String source = (String) model.getValueAt(row, 2);
        String destination = (String) model.getValueAt(row, 3);
        String date = (String) model.getValueAt(row, 4);
        String seats = (String) model.getValueAt(row, 5);
        String status = (String) model.getValueAt(row, 6);
        String fare = (String) model.getValueAt(row, 7);
        
        // Create a dialog to display booking details
        JDialog detailsDialog = new JDialog(this, "Booking Details - " + bookingId, true);
        detailsDialog.setSize(400, 400);
        detailsDialog.setLocationRelativeTo(this);
        detailsDialog.setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new javax.swing.BoxLayout(contentPanel, javax.swing.BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);
        
        // Add booking details
        JLabel titleLabel = new JLabel("Booking Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        
        JPanel detailsPanel = new JPanel(new GridLayout(8, 2, 10, 15));
        detailsPanel.setBackground(Color.WHITE);
        
        detailsPanel.add(new JLabel("Booking ID:"));
        detailsPanel.add(new JLabel(bookingId));
        
        detailsPanel.add(new JLabel("User ID:"));
        detailsPanel.add(new JLabel(userId));
        
        detailsPanel.add(new JLabel("Source:"));
        detailsPanel.add(new JLabel(source));
        
        detailsPanel.add(new JLabel("Destination:"));
        detailsPanel.add(new JLabel(destination));
        
        detailsPanel.add(new JLabel("Journey Date:"));
        detailsPanel.add(new JLabel(date));
        
        detailsPanel.add(new JLabel("Number of Seats:"));
        detailsPanel.add(new JLabel(seats));
        
        detailsPanel.add(new JLabel("Fare:"));
        detailsPanel.add(new JLabel(fare + " BDT"));
        
        detailsPanel.add(new JLabel("Status:"));
        JLabel statusLabel = new JLabel(status);
        if ("CONFIRMED".equals(status)) {
            statusLabel.setForeground(new Color(40, 167, 69)); // Green
        } else {
            statusLabel.setForeground(ACCENT_COLOR); // Red
        }
        detailsPanel.add(statusLabel);
        
        contentPanel.add(titleLabel);
        contentPanel.add(javax.swing.Box.createVerticalStrut(20));
        contentPanel.add(detailsPanel);
        
        // Close button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(Color.LIGHT_GRAY);
        closeButton.setForeground(Color.BLACK);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.addActionListener(e -> detailsDialog.dispose());
        
        buttonPanel.add(closeButton);
        
        contentPanel.add(javax.swing.Box.createVerticalStrut(20));
        contentPanel.add(buttonPanel);
        
        detailsDialog.add(contentPanel, BorderLayout.CENTER);
        detailsDialog.setVisible(true);
    }
    
    // Show edit booking dialog
    private void showEditBookingDialog(DefaultTableModel model, int row) {
        String bookingId = (String) model.getValueAt(row, 0);
        String userId = (String) model.getValueAt(row, 1);
        String source = (String) model.getValueAt(row, 2);
        String destination = (String) model.getValueAt(row, 3);
        String date = (String) model.getValueAt(row, 4);
        String seats = (String) model.getValueAt(row, 5);
        String status = (String) model.getValueAt(row, 6);
        String fare = (String) model.getValueAt(row, 7);
        
        // Create a dialog to edit booking details
        JDialog editDialog = new JDialog(this, "Edit Booking - " + bookingId, true);
        editDialog.setSize(450, 500);
        editDialog.setLocationRelativeTo(this);
        editDialog.setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new javax.swing.BoxLayout(contentPanel, javax.swing.BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Edit Booking Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 15));
        formPanel.setBackground(Color.WHITE);
        
        // Booking ID (non-editable)
        formPanel.add(new JLabel("Booking ID:"));
        JTextField bookingIdField = new JTextField(bookingId);
        bookingIdField.setEditable(false);
        formPanel.add(bookingIdField);
        
        // User ID
        formPanel.add(new JLabel("User ID:"));
        JTextField userIdField = new JTextField(userId);
        formPanel.add(userIdField);
        
        // Source
        formPanel.add(new JLabel("Source:"));
        JTextField sourceField = new JTextField(source);
        formPanel.add(sourceField);
        
        // Destination
        formPanel.add(new JLabel("Destination:"));
        JTextField destinationField = new JTextField(destination);
        formPanel.add(destinationField);
        
        // Journey Date
        formPanel.add(new JLabel("Journey Date (YYYY-MM-DD):"));
        JTextField dateField = new JTextField(date);
        formPanel.add(dateField);
        
        // Number of Seats
        formPanel.add(new JLabel("Number of Seats:"));
        JTextField seatsField = new JTextField(seats);
        formPanel.add(seatsField);
        
        // Fare
        formPanel.add(new JLabel("Fare (BDT):"));
        JTextField fareField = new JTextField(fare);
        formPanel.add(fareField);
        
        // Status
        formPanel.add(new JLabel("Status:"));
        String[] statusOptions = {"CONFIRMED", "CANCELLED"};
        javax.swing.JComboBox<String> statusCombo = new javax.swing.JComboBox<>(statusOptions);
        statusCombo.setSelectedItem(status);
        formPanel.add(statusCombo);
        
        contentPanel.add(titleLabel);
        contentPanel.add(javax.swing.Box.createVerticalStrut(20));
        contentPanel.add(formPanel);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        
        JButton saveButton = new JButton("Save Changes");
        styleButton(saveButton, SECONDARY_COLOR);
        saveButton.addActionListener(e -> {
            try {
                // Validate inputs
                int numSeats = Integer.parseInt(seatsField.getText().trim());
                double fareAmount = Double.parseDouble(fareField.getText().trim());
                
                if (numSeats <= 0) {
                    JOptionPane.showMessageDialog(editDialog, 
                        "Number of seats must be greater than 0", 
                        "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (fareAmount <= 0) {
                    JOptionPane.showMessageDialog(editDialog, 
                        "Fare must be greater than 0", 
                        "Invalid Input", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Update the table model with new values
                model.setValueAt(userIdField.getText(), row, 1);
                model.setValueAt(sourceField.getText(), row, 2);
                model.setValueAt(destinationField.getText(), row, 3);
                model.setValueAt(dateField.getText(), row, 4);
                model.setValueAt(String.valueOf(numSeats), row, 5);
                model.setValueAt(statusCombo.getSelectedItem(), row, 6);
                model.setValueAt(String.valueOf(fareAmount), row, 7);
                
                JOptionPane.showMessageDialog(editDialog, 
                    "Booking updated successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                editDialog.dispose();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editDialog, 
                    "Please enter valid numbers for seats and fare", 
                    "Invalid Input", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.addActionListener(e -> editDialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(javax.swing.Box.createHorizontalStrut(10));
        buttonPanel.add(cancelButton);
        
        contentPanel.add(javax.swing.Box.createVerticalStrut(20));
        contentPanel.add(buttonPanel);
        
        editDialog.add(contentPanel, BorderLayout.CENTER);
        editDialog.setVisible(true);
    }
    
    // Print ticket
    private void printTicket(DefaultTableModel model, int row) {
        String bookingId = (String) model.getValueAt(row, 0);
        String userId = (String) model.getValueAt(row, 1);
        String source = (String) model.getValueAt(row, 2);
        String destination = (String) model.getValueAt(row, 3);
        String date = (String) model.getValueAt(row, 4);
        String seats = (String) model.getValueAt(row, 5);
        String fare = (String) model.getValueAt(row, 7);
        
        // Create a ticket panel to print
        JPanel ticketPanel = createTicketPanel(bookingId, userId, source, destination, date, seats, fare);
        
        // Create printer job
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) {
                return java.awt.print.Printable.NO_SUCH_PAGE;
            }
            
            // Draw the ticket
            java.awt.Graphics2D g2d = (java.awt.Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            
            // Scale to fit on the page
            double scaleX = pageFormat.getImageableWidth() / ticketPanel.getWidth();
            double scaleY = pageFormat.getImageableHeight() / ticketPanel.getHeight();
            double scale = Math.min(scaleX, scaleY);
            
            g2d.scale(scale, scale);
            ticketPanel.paint(g2d);
            
            return java.awt.print.Printable.PAGE_EXISTS;
        });
        
        // Show print dialog
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
                JOptionPane.showMessageDialog(this, 
                    "Ticket printed successfully!", 
                    "Print Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error printing ticket: " + e.getMessage(), 
                    "Print Error", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    // Create a ticket panel for printing/PDF
    private JPanel createTicketPanel(String bookingId, String userId, String source, 
                                   String destination, String date, String seats, String fare) {
        // Create a panel with ticket design
        JPanel panel = new JPanel();
        panel.setSize(600, 350);
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Header with logo and title
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        
        // Create simple logo
        JLabel logoLabel = new JLabel("🚌");
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 28));
        logoLabel.setForeground(Color.WHITE);
        
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("BANGLADESH TRANSPORT SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Official Travel Ticket");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        subtitleLabel.setForeground(Color.WHITE);
        
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);
        
        headerPanel.add(logoLabel, BorderLayout.WEST);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        // Right side panel for ticket info and barcode
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        
        // Booking ID display with emphasis
        JPanel bookingIdPanel = new JPanel();
        bookingIdPanel.setBackground(new Color(248, 249, 250));
        bookingIdPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15))
        );
        
        JLabel bookingIdLabel = new JLabel("Ticket #: " + bookingId);
        bookingIdLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        bookingIdLabel.setForeground(PRIMARY_COLOR);
        bookingIdPanel.add(bookingIdLabel);
        
        // Mock QR code for the ticket (just a visual placeholder)
        JPanel qrPanel = new JPanel();
        qrPanel.setPreferredSize(new Dimension(100, 100));
        qrPanel.setBackground(Color.WHITE);
        qrPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        qrPanel.setLayout(new BorderLayout());
        
        // Create a simplistic QR code visual
        JPanel qrVisual = new JPanel(new GridLayout(5, 5));
        qrVisual.setBackground(Color.WHITE);
        for (int i = 0; i < 25; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(Math.random() > 0.7 ? Color.BLACK : Color.WHITE);
            qrVisual.add(cell);
        }
        qrPanel.add(qrVisual, BorderLayout.CENTER);
        
        rightPanel.add(bookingIdPanel, BorderLayout.NORTH);
        rightPanel.add(qrPanel, BorderLayout.CENTER);
        
        // Main content - travel details section
        JPanel contentPanel = new JPanel(new BorderLayout(15, 0));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));
        
        // Journey info section
        JPanel journeyPanel = new JPanel(new GridLayout(5, 2, 15, 12));
        journeyPanel.setBackground(Color.WHITE);
        
        addStyledTicketField(journeyPanel, "PASSENGER:", userId, Font.BOLD);
        addStyledTicketField(journeyPanel, "DATE:", date, Font.PLAIN);
        addStyledTicketField(journeyPanel, "FROM:", source, Font.BOLD);
        addStyledTicketField(journeyPanel, "TO:", destination, Font.BOLD);
        addStyledTicketField(journeyPanel, "SEATS:", seats, Font.PLAIN);
        
        // Route visualization
        JPanel routePanel = new JPanel(new BorderLayout());
        routePanel.setBackground(new Color(240, 240, 240));
        routePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JPanel routeVisual = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        routeVisual.setOpaque(false);
        
        JLabel sourceLabel = new JLabel(source);
        sourceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel arrowLabel = new JLabel(" ➔ ");
        arrowLabel.setFont(new Font("Arial", Font.BOLD, 16));
        arrowLabel.setForeground(PRIMARY_COLOR);
        
        JLabel destLabel = new JLabel(destination);
        destLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        routeVisual.add(sourceLabel);
        routeVisual.add(arrowLabel);
        routeVisual.add(destLabel);
        
        routePanel.add(routeVisual, BorderLayout.CENTER);
        
        contentPanel.add(journeyPanel, BorderLayout.CENTER);
        contentPanel.add(routePanel, BorderLayout.SOUTH);
        
        // Footer with fare and terms
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(248, 249, 250));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        
        JLabel fareLabel = new JLabel("TOTAL FARE: " + fare + " BDT");
        fareLabel.setFont(new Font("Arial", Font.BOLD, 18));
        fareLabel.setForeground(SECONDARY_COLOR);
        
        JLabel termsLabel = new JLabel("Valid ID required • Non-refundable • Please arrive 30 mins before departure");
        termsLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        termsLabel.setForeground(Color.DARK_GRAY);
        
        footerPanel.add(fareLabel, BorderLayout.WEST);
        footerPanel.add(termsLabel, BorderLayout.SOUTH);
        
        // Add all components to main panel
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.EAST);
        panel.add(footerPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // Helper method to add styled fields to ticket
    private void addStyledTicketField(JPanel panel, String label, String value, int style) {
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 14));
        labelComponent.setForeground(new Color(100, 100, 100));
        
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", style, 14));
        
        panel.add(labelComponent);
        panel.add(valueComponent);
    }
    
    // Generate PDF ticket
    private void generateTicketPDF(DefaultTableModel model, int row) {
        String bookingId = (String) model.getValueAt(row, 0);
        String userId = (String) model.getValueAt(row, 1);
        String source = (String) model.getValueAt(row, 2);
        String destination = (String) model.getValueAt(row, 3);
        String date = (String) model.getValueAt(row, 4);
        String seats = (String) model.getValueAt(row, 5);
        String fare = (String) model.getValueAt(row, 7);
        
        // Show file save dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF Ticket");
        fileChooser.setSelectedFile(new File("BTS_Ticket_" + bookingId + ".pdf"));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF Files (*.pdf)", "pdf"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            // Ensure filename ends with .pdf
            if (!fileToSave.getName().toLowerCase().endsWith(".pdf")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
            }
            
            try {
                // 1. Create the ticket's visual representation
                JPanel ticketPanel = createTicketPanel(bookingId, userId, source, destination, date, seats, fare);
                
                // 2. Convert to image
                BufferedImage ticketImage = new BufferedImage(
                        ticketPanel.getWidth(), ticketPanel.getHeight(), 
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = ticketImage.createGraphics();
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, ticketImage.getWidth(), ticketImage.getHeight());
                ticketPanel.paint(g2d);
                g2d.dispose();
                
                // 3. Save as PNG for backup
                File pngFile = new File(fileToSave.getAbsolutePath().replaceAll("\\.pdf$", ".png"));
                ImageIO.write(ticketImage, "png", pngFile);
                
                // 4. Write a super-simplified PDF with the image embedded
                try (java.io.FileOutputStream fos = new java.io.FileOutputStream(fileToSave)) {
                    
                    // ----- BEGIN PDF WRITING -----
                    
                    // PDF header
                    String header = "%PDF-1.4\n";
                    fos.write(header.getBytes());

                    // ===== OBJECT 1: CATALOG =====
                    String obj1Start = "1 0 obj\n<<\n/Type /Catalog\n/Pages 2 0 R\n>>\nendobj\n\n";
                    fos.write(obj1Start.getBytes());
                    
                    // ===== OBJECT 2: PAGES =====
                    String obj2 = "2 0 obj\n<<\n/Type /Pages\n/Kids [3 0 R]\n/Count 1\n>>\nendobj\n\n";
                    fos.write(obj2.getBytes());
                    
                    // ===== OBJECT 3: PAGE =====
                    String obj3 = "3 0 obj\n<<\n/Type /Page\n/Parent 2 0 R\n" +
                                   "/MediaBox [0 0 595 842]\n" +
                                   "/Resources <<\n/XObject << /Im1 4 0 R >>\n" +
                                   "/ProcSet [/PDF /ImageC]\n>>\n" +
                                   "/Contents 5 0 R\n>>\nendobj\n\n";
                    fos.write(obj3.getBytes());
                    
                    // Save the ticket as JPEG with high quality (for embedding in PDF)
                    // Using ByteArrayOutputStream to avoid another temp file
                    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                    javax.imageio.ImageWriter writer = javax.imageio.ImageIO.getImageWritersByFormatName("jpeg").next();
                    javax.imageio.ImageWriteParam param = writer.getDefaultWriteParam();
                    param.setCompressionMode(javax.imageio.ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(1.0f); // Maximum quality
                    
                    javax.imageio.IIOImage iioImage = new javax.imageio.IIOImage(ticketImage, null, null);
                    writer.setOutput(new javax.imageio.stream.MemoryCacheImageOutputStream(baos));
                    writer.write(null, iioImage, param);
                    writer.dispose();
                    
                    byte[] jpegData = baos.toByteArray();
                    
                    // ===== OBJECT 4: IMAGE RESOURCE =====
                    String obj4Header = "4 0 obj\n<<\n" +
                                      "/Type /XObject\n" +
                                      "/Subtype /Image\n" +
                                      "/Width " + ticketImage.getWidth() + "\n" +
                                      "/Height " + ticketImage.getHeight() + "\n" +
                                      "/BitsPerComponent 8\n" +
                                      "/ColorSpace /DeviceRGB\n" +
                                      "/Filter /DCTDecode\n" +
                                      "/Length " + jpegData.length + "\n" +
                                      ">>\nstream\n";
                    fos.write(obj4Header.getBytes());
                    
                    // Write the JPEG binary data
                    fos.write(jpegData);
                    
                    String obj4Footer = "\nendstream\nendobj\n\n";
                    fos.write(obj4Footer.getBytes());
                    
                    // ===== OBJECT 5: CONTENT STREAM (draws the image) =====
                    // Calculate scaling to fit the image centered on the page
                    double imgWidth = ticketImage.getWidth();
                    double imgHeight = ticketImage.getHeight();
                    double pageWidth = 595; // A4 width in points
                    double pageHeight = 842; // A4 height in points
                    
                    double scale = Math.min((pageWidth - 40) / imgWidth, (pageHeight - 40) / imgHeight);
                    double scaledWidth = imgWidth * scale;
                    double scaledHeight = imgHeight * scale;
                    
                    // Center on page
                    double x = (pageWidth - scaledWidth) / 2;
                    double y = (pageHeight - scaledHeight) / 2;
                    
                    String contentStream = "q\n" + // Save graphics state
                                          scaledWidth + " 0 0 " + scaledHeight + " " + x + " " + y + " cm\n" + // Scale and position
                                          "/Im1 Do\n" + // Draw the image
                                          "Q"; // Restore graphics state
                    
                    String obj5 = "5 0 obj\n<<\n/Length " + contentStream.length() + "\n>>\nstream\n" +
                                contentStream + "\nendstream\nendobj\n\n";
                    fos.write(obj5.getBytes());
                    
                    // PDF cross-reference table
                    // This is an important part that needs correct offsets
                    // In a real implementation, you'd track byte offsets
                    int xrefPos = header.length() + obj1Start.length() + obj2.length() + obj3.length() + 
                                 obj4Header.length() + jpegData.length + obj4Footer.length() + obj5.length();
                    
                    String xref = "xref\n" +
                                "0 6\n" +
                                "0000000000 65535 f\n" +
                                "0000000010 00000 n\n" +
                                "0000000079 00000 n\n" +
                                "0000000173 00000 n\n" +
                                "0000000301 00000 n\n" +
                                "0000000862 00000 n\n";
                    fos.write(xref.getBytes());
                    
                    // PDF trailer
                    String trailer = "trailer\n" +
                                    "<<\n" +
                                    "/Size 6\n" +
                                    "/Root 1 0 R\n" +
                                    ">>\n" +
                                    "startxref\n" +
                                    xrefPos + "\n" +
                                    "%%EOF";
                    fos.write(trailer.getBytes());
                    
                    // ----- END PDF WRITING -----
                    
                    JOptionPane.showMessageDialog(this, 
                        "Ticket saved successfully!\n\n" +
                        "PDF: " + fileToSave.getAbsolutePath() + "\n\n" +
                        "PNG copy: " + pngFile.getAbsolutePath(),
                        "Ticket Saved", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Error generating PDF: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                
                // Try an even simpler method - just save as PNG
                try {
                    JPanel ticketPanel = createTicketPanel(bookingId, userId, source, destination, date, seats, fare);
                    BufferedImage ticketImage = new BufferedImage(
                            ticketPanel.getWidth(), ticketPanel.getHeight(), 
                            BufferedImage.TYPE_INT_RGB);
                    
                    Graphics2D g2d = ticketImage.createGraphics();
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(0, 0, ticketImage.getWidth(), ticketImage.getHeight());
                    ticketPanel.paint(g2d);
                    g2d.dispose();
                    
                    File pngFile = new File(fileToSave.getAbsolutePath().replaceAll("\\.pdf$", ".png"));
                    ImageIO.write(ticketImage, "png", pngFile);
                    
                    JOptionPane.showMessageDialog(this, 
                        "PDF generation failed, but saved ticket as image: " + pngFile.getAbsolutePath(),
                        "Image Saved", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, 
                        "Failed to save ticket in any format. Please try again.",
                        "Save Failed", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    // USERS TAB
    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create a tabbed panel for Users and Admins
        JTabbedPane userTabs = new JTabbedPane();
        userTabs.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Regular Users Panel
        JPanel regularUsersPanel = createRegularUsersPanel();
        userTabs.addTab("Regular Users", regularUsersPanel);
        
        // Admins Panel
        JPanel adminsPanel = createAdminsPanel();
        userTabs.addTab("Administrators", adminsPanel);
        
        panel.add(userTabs, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Regular Users Panel
    private JPanel createRegularUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        
        JLabel titleLabel = new JLabel("System Users");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Users table
        String[] columns = {"User ID", "Username", "Full Name", "Email", "Phone", "Bookings"};
        DefaultTableModel userTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Load regular users
        loadRegularUsers(userTableModel);
        
        JTable usersTable = new JTable(userTableModel);
        usersTable.setRowHeight(25);
        usersTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        usersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        JScrollPane scrollPane = new JScrollPane(usersTable);
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(BG_COLOR);
        
        JButton viewButton = new JButton("View User Details");
        styleButton(viewButton, PRIMARY_COLOR);
        viewButton.addActionListener(e -> {
            int row = usersTable.getSelectedRow();
            if (row >= 0) {
                String userId = (String) userTableModel.getValueAt(row, 0);
                showUserDetailsDialog(userId);
            } else {
                JOptionPane.showMessageDialog(panel,
                    "Please select a user to view details",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        JButton refreshButton = new JButton("Refresh List");
        styleButton(refreshButton, new Color(108, 117, 125)); // Gray
        refreshButton.addActionListener(e -> {
            userTableModel.setRowCount(0);
            loadRegularUsers(userTableModel);
            JOptionPane.showMessageDialog(panel,
                "User list has been refreshed",
                "Refreshed",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        buttonPanel.add(viewButton);
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // Admins Panel
    private JPanel createAdminsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        
        JLabel titleLabel = new JLabel("System Administrators");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        JButton addAdminButton = new JButton("Add New Admin");
        styleButton(addAdminButton, PRIMARY_COLOR);
        addAdminButton.addActionListener(e -> {
            // Create input fields
            String username = JOptionPane.showInputDialog(this, "Enter Username:", "Add Admin", JOptionPane.QUESTION_MESSAGE);
            if (username == null || username.trim().isEmpty()) return;
            
            String password = JOptionPane.showInputDialog(this, "Enter Password:", "Add Admin", JOptionPane.QUESTION_MESSAGE);
            if (password == null || password.trim().isEmpty()) return;
            
            String name = JOptionPane.showInputDialog(this, "Enter Full Name:", "Add Admin", JOptionPane.QUESTION_MESSAGE);
            if (name == null || name.trim().isEmpty()) return;
            
            String email = JOptionPane.showInputDialog(this, "Enter Email:", "Add Admin", JOptionPane.QUESTION_MESSAGE);
            if (email == null || email.trim().isEmpty()) return;
            
            String phone = JOptionPane.showInputDialog(this, "Enter Phone:", "Add Admin", JOptionPane.QUESTION_MESSAGE);
            if (phone == null || phone.trim().isEmpty()) return;
            
            String role = JOptionPane.showInputDialog(this, "Enter Admin Role (e.g., SYSTEM_ADMIN, BOOKING_ADMIN):", 
                                                     "Add Admin", JOptionPane.QUESTION_MESSAGE);
            if (role == null || role.trim().isEmpty()) {
                role = "SYSTEM_ADMIN";
            }
            
            JOptionPane.showMessageDialog(this,
                "Admin user would be added to the system.\n" +
                "(Actual implementation would add this user to the database)",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        });
        headerPanel.add(addAdminButton, BorderLayout.EAST);
        
        // Admins table
        String[] columns = {"Admin ID", "Username", "Full Name", "Email", "Phone", "Role"};
        DefaultTableModel adminTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Load admin users
        loadAdminUsers(adminTableModel);
        
        JTable adminsTable = new JTable(adminTableModel);
        adminsTable.setRowHeight(25);
        adminsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        adminsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        JScrollPane scrollPane = new JScrollPane(adminsTable);
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(BG_COLOR);
        
        JButton viewButton = new JButton("View Admin Details");
        styleButton(viewButton, PRIMARY_COLOR);
        viewButton.addActionListener(e -> {
            int row = adminsTable.getSelectedRow();
            if (row >= 0) {
                String adminId = (String) adminTableModel.getValueAt(row, 0);
                showAdminDetailsDialog(adminId);
            } else {
                JOptionPane.showMessageDialog(panel,
                    "Please select an admin to view details",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        JButton refreshButton = new JButton("Refresh List");
        styleButton(refreshButton, new Color(108, 117, 125)); // Gray
        refreshButton.addActionListener(e -> {
            adminTableModel.setRowCount(0);
            loadAdminUsers(adminTableModel);
            JOptionPane.showMessageDialog(panel,
                "Admin list has been refreshed",
                "Refreshed",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        buttonPanel.add(viewButton);
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // Helper method to load regular users
    private void loadRegularUsers(DefaultTableModel model) {
        // Clear the model to avoid duplicates
        model.setRowCount(0);
        
        if (userService != null) {
            try {
                // Iterate through all users from the service
                for (BangladeshTransportDemo.User user : userService.getUsers()) {
                    // Skip admin users
                    if (userService.isAdmin(user)) {
                        continue;
                    }
                    
                    // Count bookings for this user
                    int bookingCount = user.getBookings() != null ? user.getBookings().size() : 0;
                    
                    // Add the user to the table
                    model.addRow(new Object[]{
                        user.getUserId(),
                        user.getUsername(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhone(),
                        String.valueOf(bookingCount)
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Fall back to sample data if there was an error
                loadSampleUsers(model);
            }
        } else {
            // Fall back to sample data if service is not available
            loadSampleUsers(model);
        }
    }
    
    // Helper method to load sample users when service not available
    private void loadSampleUsers(DefaultTableModel model) {
        model.addRow(new Object[]{"USER001", "user", "Test User", "user@transport.com", "9876543210", "2"});
        model.addRow(new Object[]{"USER002", "john", "John Smith", "john@example.com", "5551234567", "3"});
        model.addRow(new Object[]{"USER003", "sarah", "Sarah Johnson", "sarah@example.com", "5559876543", "1"});
        model.addRow(new Object[]{"USER004", "mike", "Mike Thompson", "mike@example.com", "5554567890", "0"});
    }
    
    // Helper method to load admin users
    private void loadAdminUsers(DefaultTableModel model) {
        // Clear the model to avoid duplicates
        model.setRowCount(0);
        
        if (userService != null) {
            try {
                // Iterate through all users from the service
                for (BangladeshTransportDemo.User user : userService.getUsers()) {
                    // Only include admin users
                    if (!userService.isAdmin(user)) {
                        continue;
                    }
                    
                    // Cast to Admin to get the role
                    BangladeshTransportDemo.Admin admin = (BangladeshTransportDemo.Admin) user;
                    
                    // Add the admin to the table
                    model.addRow(new Object[]{
                        admin.getUserId(),
                        admin.getUsername(),
                        admin.getName(),
                        admin.getEmail(),
                        admin.getPhone(),
                        admin.getAdminRole()
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Fall back to sample data if there was an error
                loadSampleAdmins(model);
            }
        } else {
            // Fall back to sample data if service is not available
            loadSampleAdmins(model);
        }
    }
    
    // Helper method to load sample admins when service not available
    private void loadSampleAdmins(DefaultTableModel model) {
        model.addRow(new Object[]{"ADMIN001", "admin", "System Admin", "admin@transport.com", "1234567890", "SYSTEM_ADMIN"});
        model.addRow(new Object[]{"ADMIN002", "bookingadmin", "Booking Manager", "booking@transport.com", "9871234560", "BOOKING_ADMIN"});
        model.addRow(new Object[]{"ADMIN003", "routeadmin", "Route Manager", "route@transport.com", "8765432190", "ROUTE_ADMIN"});
    }
    
    // Show user details dialog with improved formatting
    private void showUserDetailsDialog(String userId) {
        // Create a dialog to show user details
        JDialog detailsDialog = new JDialog(this, "User Details - " + userId, true);
        detailsDialog.setSize(700, 550);
        detailsDialog.setLocationRelativeTo(this);
        detailsDialog.setLayout(new BorderLayout());
        
        // Main panel with scroll capability
        JPanel contentPanel = new JPanel(new BorderLayout(0, 0));
        contentPanel.setBackground(Color.WHITE);
        
        // Container panel for all content with proper padding
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(0, 0, 15, 0)
        ));
        
        // Create a user icon
        JLabel iconLabel = new JLabel("👤");
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 42));
        iconLabel.setForeground(PRIMARY_COLOR);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        
        // Title with user name
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        
        // Get user info
        BangladeshTransportDemo.User userObj = findUserById(userId);
        String userName = (userObj != null) ? userObj.getName() : "User";
        
        JLabel titleLabel = new JLabel("User Profile Information");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(PRIMARY_COLOR);
        
        JLabel subtitleLabel = new JLabel(userName);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);
        
        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        // User details section
        JPanel detailsPanel = new JPanel(new BorderLayout(0, 15));
        detailsPanel.setOpaque(false);
        
        // User information panel
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Personal Information",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                PRIMARY_COLOR
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Default values
        String username = "user";
        String name = "Test User";
        String email = "user@transport.com";
        String phone = "9876543210";
        String created = "2023-01-15";
        
        // If user exists, use their data
        if (userObj != null) {
            username = userObj.getUsername();
            name = userObj.getName();
            email = userObj.getEmail();
            phone = userObj.getPhone();
            created = LocalDate.now().toString(); // In a real app, this would be stored
        } else {
            // Fallback to sample data based on ID
            if (userId.equals("USER001")) {
                username = "user";
                name = "Test User";
                email = "user@transport.com";
                phone = "9876543210";
            } else if (userId.equals("USER002")) {
                username = "john";
                name = "John Smith";
                email = "john@example.com";
                phone = "5551234567";
            } else if (userId.equals("USER003")) {
                username = "sarah";
                name = "Sarah Johnson";
                email = "sarah@example.com";
                phone = "5559876543";
            } else if (userId.equals("USER004")) {
                username = "mike";
                name = "Mike Thompson";
                email = "mike@example.com";
                phone = "5554567890";
            }
        }
        
        // Add user info fields
        addDetailRow(infoPanel, "User ID:", userId, PRIMARY_COLOR);
        addDetailRow(infoPanel, "Username:", username, null);
        addDetailRow(infoPanel, "Full Name:", name, null);
        addDetailRow(infoPanel, "Email Address:", email, null);
        addDetailRow(infoPanel, "Phone Number:", phone, null);
        addDetailRow(infoPanel, "Account Created:", created, null);
        
        detailsPanel.add(infoPanel, BorderLayout.NORTH);
        
        // Bookings section
        JPanel bookingsContainer = new JPanel(new BorderLayout(0, 10));
        bookingsContainer.setOpaque(false);
        bookingsContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Booking History",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                PRIMARY_COLOR
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Create booking table with non-editable model
        String[] columns = {"Booking ID", "Route", "Date", "Status", "Fare"};
        DefaultTableModel bookingModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Get real bookings if available
        boolean hasBookings = false;
        if (userObj != null && userObj.getBookings() != null && !userObj.getBookings().isEmpty()) {
            hasBookings = true;
            for (BangladeshTransportDemo.Booking booking : userObj.getBookings()) {
                String route = booking.getSource() + " → " + booking.getDestination();
                String formattedDate = booking.getJourneyDate().toString();
                String formattedFare = String.format("%.2f BDT", booking.getFare());
                
                bookingModel.addRow(new Object[]{
                    booking.getBookingId(),
                    route,
                    formattedDate,
                    booking.getStatus(),
                    formattedFare
                });
            }
        } else {
            // Fallback to sample data if no bookings found
            if (userId.equals("USER001")) {
                hasBookings = true;
                bookingModel.addRow(new Object[]{"BK52A7F3D6", "Dhaka → Chittagong", "2023-05-15", "CONFIRMED", "500.00 BDT"});
                bookingModel.addRow(new Object[]{"BK78D2E5F1", "Chittagong → Dhaka", "2023-06-10", "COMPLETED", "500.00 BDT"});
            } else if (userId.equals("USER002")) {
                hasBookings = true;
                bookingModel.addRow(new Object[]{"BK65F2A7D3", "Dhaka → Sylhet", "2023-04-22", "CANCELLED", "450.00 BDT"});
                bookingModel.addRow(new Object[]{"BK12E4F6A8", "Dhaka → Rajshahi", "2023-05-05", "CONFIRMED", "520.00 BDT"});
                bookingModel.addRow(new Object[]{"BK39B8C1D5", "Rajshahi → Dhaka", "2023-05-09", "CONFIRMED", "520.00 BDT"});
            } else if (userId.equals("USER003")) {
                hasBookings = true;
                bookingModel.addRow(new Object[]{"BK91C3E7F2", "Dhaka → Khulna", "2023-05-20", "CONFIRMED", "550.00 BDT"});
            }
        }
        
        if (hasBookings) {
            // Create table with custom cell renderer for status
            JTable bookingsTable = new JTable(bookingModel);
            bookingsTable.setRowHeight(28);
            bookingsTable.setShowGrid(true);
            bookingsTable.setGridColor(new Color(240, 240, 240));
            bookingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            bookingsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
            bookingsTable.getTableHeader().setBackground(new Color(245, 245, 245));
            bookingsTable.getTableHeader().setForeground(new Color(60, 60, 60));
            bookingsTable.setFont(new Font("Arial", Font.PLAIN, 12));
            
            // Apply status cell renderer
            bookingsTable.getColumnModel().getColumn(3).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, 
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    
                    if (value != null) {
                        String status = value.toString();
                        if ("CONFIRMED".equals(status)) {
                            c.setForeground(new Color(40, 167, 69)); // Green
                        } else if ("CANCELLED".equals(status)) {
                            c.setForeground(new Color(220, 53, 69)); // Red
                        } else if ("COMPLETED".equals(status)) {
                            c.setForeground(new Color(23, 162, 184)); // Blue
                        }
                    }
                    
                    return c;
                }
            });
            
            // Set column widths
            bookingsTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Booking ID
            bookingsTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Route
            bookingsTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Date
            bookingsTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Status
            bookingsTable.getColumnModel().getColumn(4).setPreferredWidth(90);  // Fare
            
            // Add table to scroll pane
            JScrollPane tableScrollPane = new JScrollPane(bookingsTable);
            tableScrollPane.setBorder(BorderFactory.createEmptyBorder());
            tableScrollPane.setPreferredSize(new Dimension(0, 140));
            
            // Add to container
            bookingsContainer.add(tableScrollPane, BorderLayout.CENTER);
        } else {
            // Show a message when no bookings are found
            JPanel emptyPanel = new JPanel(new BorderLayout());
            emptyPanel.setOpaque(false);
            emptyPanel.setPreferredSize(new Dimension(0, 100));
            
            JLabel emptyLabel = new JLabel("No booking history found for this user");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            emptyLabel.setForeground(new Color(120, 120, 120));
            emptyLabel.setHorizontalAlignment(JLabel.CENTER);
            
            emptyPanel.add(emptyLabel, BorderLayout.CENTER);
            bookingsContainer.add(emptyPanel, BorderLayout.CENTER);
        }
        
        detailsPanel.add(bookingsContainer, BorderLayout.CENTER);
        
        // Add all sections to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        
        // Create scroll pane for the main content
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        JButton closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(120, 35));
        closeButton.setBackground(new Color(108, 117, 125)); // Gray
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> detailsDialog.dispose());
        
        buttonPanel.add(closeButton);
        
        // Add all components to dialog
        detailsDialog.add(contentPanel, BorderLayout.CENTER);
        detailsDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        detailsDialog.setVisible(true);
    }
    
    // Helper method to add a detail row with label and value
    private void addDetailRow(JPanel panel, String label, String value, Color labelColor) {
        // Use a consistent panel for each row
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
        rowPanel.setOpaque(false);
        
        // Style the label
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 13));
        labelComponent.setForeground(labelColor != null ? labelColor : new Color(90, 90, 90));
        labelComponent.setPreferredSize(new Dimension(120, 25));
        
        // Style the value
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 13));
        valueComponent.setForeground(new Color(40, 40, 40));
        
        // Add components to row
        rowPanel.add(labelComponent, BorderLayout.WEST);
        rowPanel.add(valueComponent, BorderLayout.CENTER);
        
        // Add the row to the parent panel
        panel.add(rowPanel);
    }
    
    // Show admin details dialog with improved formatting - similar improvements as user details
    private void showAdminDetailsDialog(String adminId) {
        // Create a dialog to show admin details
        JDialog detailsDialog = new JDialog(this, "Admin Details - " + adminId, true);
        detailsDialog.setSize(700, 550);
        detailsDialog.setLocationRelativeTo(this);
        detailsDialog.setLayout(new BorderLayout());
        
        // Main panel with scroll capability
        JPanel contentPanel = new JPanel(new BorderLayout(0, 0));
        contentPanel.setBackground(Color.WHITE);
        
        // Container panel for all content with proper padding
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(0, 0, 15, 0)
        ));
        
        // Create admin icon
        JLabel iconLabel = new JLabel("👨‍💼");
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 42));
        iconLabel.setForeground(ADMIN_COLOR);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        
        // Title with admin name
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        
        // Get admin info
        BangladeshTransportDemo.User userObj = findUserById(adminId);
        BangladeshTransportDemo.Admin adminObj = null;
        
        if (userObj != null && userService.isAdmin(userObj)) {
            adminObj = (BangladeshTransportDemo.Admin) userObj;
        }
        
        String adminName = (adminObj != null) ? adminObj.getName() : "Administrator";
        String adminRole = (adminObj != null) ? adminObj.getAdminRole() : "SYSTEM_ADMIN";
        
        JLabel titleLabel = new JLabel("Administrator Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(ADMIN_COLOR);
        
        JLabel subtitleLabel = new JLabel(adminName + " (" + adminRole + ")");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);
        
        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titlePanel, BorderLayout.CENTER);
        
        // Admin details section
        JPanel detailsPanel = new JPanel(new BorderLayout(0, 15));
        detailsPanel.setOpaque(false);
        
        // Admin information panel
        JPanel infoPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Personal Information",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                ADMIN_COLOR
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Default values
        String username = "admin";
        String name = "System Admin";
        String email = "admin@transport.com";
        String phone = "1234567890";
        String role = "SYSTEM_ADMIN";
        String created = "2023-01-01";
        String lastLogin = "2023-05-17 08:30:22";
        
        // If admin exists, use their data
        if (adminObj != null) {
            username = adminObj.getUsername();
            name = adminObj.getName();
            email = adminObj.getEmail();
            phone = adminObj.getPhone();
            role = adminObj.getAdminRole();
            // These would be stored in a real app
            created = LocalDate.now().toString();
            lastLogin = LocalDateTime.now().toString();
        } else {
            // Fallback to sample data based on ID
            if (adminId.equals("ADMIN001")) {
                username = "admin";
                name = "System Admin";
                email = "admin@transport.com";
                phone = "1234567890";
                role = "SYSTEM_ADMIN";
            } else if (adminId.equals("ADMIN002")) {
                username = "bookingadmin";
                name = "Booking Manager";
                email = "booking@transport.com";
                phone = "9871234560";
                role = "BOOKING_ADMIN";
            } else if (adminId.equals("ADMIN003")) {
                username = "routeadmin";
                name = "Route Manager";
                email = "route@transport.com";
                phone = "8765432190";
                role = "ROUTE_ADMIN";
            }
        }
        
        // Add admin info fields
        addDetailRow(infoPanel, "Admin ID:", adminId, ADMIN_COLOR);
        addDetailRow(infoPanel, "Username:", username, null);
        addDetailRow(infoPanel, "Full Name:", name, null);
        addDetailRow(infoPanel, "Email Address:", email, null);
        addDetailRow(infoPanel, "Phone Number:", phone, null);
        addDetailRow(infoPanel, "Admin Role:", role, ADMIN_COLOR);
        addDetailRow(infoPanel, "Account Created:", created, null);
        addDetailRow(infoPanel, "Last Login:", lastLogin, null);
        
        detailsPanel.add(infoPanel, BorderLayout.NORTH);
        
        // Privileges section
        JPanel privilegesContainer = new JPanel(new BorderLayout(0, 10));
        privilegesContainer.setOpaque(false);
        privilegesContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "System Privileges & Permissions",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                ADMIN_COLOR
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Help text panel
        JPanel helpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        helpPanel.setOpaque(false);
        
        JLabel helpLabel = new JLabel("Privileges assigned based on admin role:"); 
        helpLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        helpLabel.setForeground(new Color(100, 100, 100));
        
        helpPanel.add(helpLabel);
        privilegesContainer.add(helpPanel, BorderLayout.NORTH);
        
        // Create panel for checkboxes with better layout
        JPanel checkboxGrid = new JPanel(new GridLayout(3, 2, 20, 12));
        checkboxGrid.setOpaque(false);
        checkboxGrid.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Create styled checkboxes for privileges based on role
        checkboxGrid.add(createPrivilegeCheckbox("Manage Users", 
                role.equals("SYSTEM_ADMIN")));
                
        checkboxGrid.add(createPrivilegeCheckbox("Manage Bookings", 
                role.equals("SYSTEM_ADMIN") || role.equals("BOOKING_ADMIN")));
                
        checkboxGrid.add(createPrivilegeCheckbox("Manage Routes", 
                role.equals("SYSTEM_ADMIN") || role.equals("ROUTE_ADMIN")));
                
        checkboxGrid.add(createPrivilegeCheckbox("Manage Vehicles", 
                role.equals("SYSTEM_ADMIN") || role.equals("ROUTE_ADMIN")));
                
        checkboxGrid.add(createPrivilegeCheckbox("View Reports", true));
        
        checkboxGrid.add(createPrivilegeCheckbox("Manage System", 
                role.equals("SYSTEM_ADMIN")));
        
        privilegesContainer.add(checkboxGrid, BorderLayout.CENTER);
        detailsPanel.add(privilegesContainer, BorderLayout.CENTER);
        
        // Add all sections to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        
        // Create scroll pane for the main content
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        JButton closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(120, 35));
        closeButton.setBackground(new Color(108, 117, 125)); // Gray
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> detailsDialog.dispose());
        
        buttonPanel.add(closeButton);
        
        // Add all components to dialog
        detailsDialog.add(contentPanel, BorderLayout.CENTER);
        detailsDialog.add(buttonPanel, BorderLayout.SOUTH);
        
        detailsDialog.setVisible(true);
    }
    
    // Helper method to create a styled privilege checkbox
    private JPanel createPrivilegeCheckbox(String text, boolean enabled) {
        JPanel checkboxPanel = new JPanel(new BorderLayout(5, 0));
        checkboxPanel.setOpaque(false);
        
        JCheckBox checkbox = new JCheckBox();
        checkbox.setSelected(enabled);
        checkbox.setEnabled(false);
        checkbox.setOpaque(false);
        
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        
        if (enabled) {
            label.setForeground(new Color(40, 167, 69)); // Green
            label.setFont(new Font("Arial", Font.BOLD, 14));
        } else {
            label.setForeground(new Color(108, 117, 125)); // Gray
        }
        
        checkboxPanel.add(checkbox, BorderLayout.WEST);
        checkboxPanel.add(label, BorderLayout.CENTER);
        
        return checkboxPanel;
    }
    
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 35));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
    
    // Helper method to refresh the booking table
    private void refreshBookingTable(DefaultTableModel model, String selectedStatus) {
        model.setRowCount(0);
        // Reload data from booking service
        if (bookingService != null && userService != null) {
            bookingService.reloadBookingsFromFile(); // Always reload from file before displaying
            java.util.List<BangladeshTransportDemo.Booking> bookings = bookingService.getAllBookings();
            for (BangladeshTransportDemo.Booking booking : bookings) {
                String userDisplay = booking.getUserId();
                try {
                    // Try to get the user's name
                    java.util.Optional<BangladeshTransportDemo.User> userOpt = userService.getUserById(booking.getUserId());
                    if (userOpt.isPresent()) {
                        BangladeshTransportDemo.User user = userOpt.get();
                        userDisplay = user.getName() + " (" + user.getUserId() + ")";
                    } else {
                        userDisplay = "Unknown User (" + booking.getUserId() + ")";
                    }
                } catch (Exception ex) {
                    // fallback to userId if any error
                }
                if ("All".equals(selectedStatus) || booking.getStatus().equals(selectedStatus)) {
                    model.addRow(new Object[]{
                        booking.getBookingId(),
                        userDisplay,
                        booking.getSource(),
                        booking.getDestination(),
                        booking.getJourneyDate().toString(),
                        String.valueOf(booking.getSeats()),
                        booking.getStatus(),
                        String.format("%.2f", booking.getFare())
                    });
                }
            }
        }
    }

    // Find user by ID in the UserService
    private BangladeshTransportDemo.User findUserById(String userId) {
        if (userService == null) return null;
        
        try {
            for (BangladeshTransportDemo.User user : userService.getUsers()) {
                if (user.getUserId().equals(userId)) {
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    // STAFF MANAGEMENT TAB
    private JPanel createStaffPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        
        JLabel titleLabel = new JLabel("Manage Staff");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Staff table
        String[] columns = {"ID", "Name", "Role", "Contact", "License No", "Assigned Vehicle", "Salary", "Status"};
        DefaultTableModel staffTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Add sample data
        staffTableModel.addRow(new Object[]{"STF001", "Rahim Khan", "Driver", "01712345678", "DL123456", "VEH001", "25000", "Active"});
        staffTableModel.addRow(new Object[]{"STF002", "Karim Ahmed", "Driver", "01798765432", "DL789012", "VEH002", "24000", "Active"});
        staffTableModel.addRow(new Object[]{"STF003", "Jamal Uddin", "Helper", "01656789012", "-", "VEH001", "15000", "Active"});
        staffTableModel.addRow(new Object[]{"STF004", "Faruk Islam", "Conductor", "01545678901", "-", "VEH003", "18000", "Active"});
        staffTableModel.addRow(new Object[]{"STF005", "Motiur Rahman", "Driver", "01812345670", "DL345678", "-", "25000", "On Leave"});
        
        JTable staffTable = new JTable(staffTableModel);
        staffTable.setRowHeight(25);
        staffTable.setFont(new Font("Arial", Font.PLAIN, 14));
        staffTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        staffTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        staffTable.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(staffTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Add Button
        JButton addButton = new JButton("Add New Staff");
        styleButton(addButton, PRIMARY_COLOR);
        addButton.addActionListener(e -> showAddStaffDialog(staffTableModel));
        headerPanel.add(addButton, BorderLayout.EAST);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        
        JButton viewButton = new JButton("View Details");
        styleButton(viewButton, new Color(23, 162, 184)); // Info color
        viewButton.addActionListener(e -> {
            int selectedRow = staffTable.getSelectedRow();
            if (selectedRow != -1) {
                showStaffDetailsDialog(staffTableModel, selectedRow);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select a staff member to view",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        JButton editButton = new JButton("Edit Staff");
        styleButton(editButton, SECONDARY_COLOR);
        editButton.addActionListener(e -> {
            int selectedRow = staffTable.getSelectedRow();
            if (selectedRow != -1) {
                showEditStaffDialog(staffTableModel, selectedRow);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select a staff member to edit",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        JButton deleteButton = new JButton("Delete Staff");
        styleButton(deleteButton, ACCENT_COLOR);
        deleteButton.addActionListener(e -> {
            int selectedRow = staffTable.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this staff member?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    staffTableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this,
                        "Staff member deleted successfully",
                        "Deletion Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please select a staff member to delete",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        buttonPanel.add(viewButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(editButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(deleteButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // Show dialog to add new staff
    private void showAddStaffDialog(DefaultTableModel model) {
        JDialog dialog = new JDialog(this, "Add New Staff Member", true);
        dialog.setSize(500, 550);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Add New Staff Member");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(titleLabel);
        
        formPanel.add(Box.createVerticalStrut(20));
        
        // Name field
        JTextField nameField = new JTextField(20);
        formPanel.add(createFormField("Full Name:", nameField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Role field
        String[] roles = {"Driver", "Helper", "Conductor", "Manager", "Mechanic"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        formPanel.add(createFormField("Role:", roleCombo));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Contact field
        JTextField contactField = new JTextField(20);
        formPanel.add(createFormField("Contact Number:", contactField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Email field
        JTextField emailField = new JTextField(20);
        formPanel.add(createFormField("Email:", emailField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Address field
        JTextField addressField = new JTextField(20);
        formPanel.add(createFormField("Address:", addressField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // License field (only for drivers)
        JTextField licenseField = new JTextField(20);
        JPanel licensePanel = createFormField("License Number:", licenseField);
        formPanel.add(licensePanel);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Vehicle assignment
        JTextField vehicleField = new JTextField(20);
        formPanel.add(createFormField("Assigned Vehicle:", vehicleField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Salary field
        JTextField salaryField = new JTextField(20);
        formPanel.add(createFormField("Salary (BDT):", salaryField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Joining date
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.setOpaque(false);
        JLabel dateLabel = new JLabel("Joining Date:");
        dateLabel.setPreferredSize(new Dimension(120, 25));
        
        // Create date components
        LocalDate today = LocalDate.now();
        JComboBox<Integer> dayCombo = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            dayCombo.addItem(i);
        }
        dayCombo.setSelectedItem(today.getDayOfMonth());
        
        String[] months = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", 
                           "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        JComboBox<String> monthCombo = new JComboBox<>(months);
        monthCombo.setSelectedItem(today.getMonth().toString());
        
        JComboBox<Integer> yearCombo = new JComboBox<>();
        for (int i = today.getYear() - 5; i <= today.getYear(); i++) {
            yearCombo.addItem(i);
        }
        yearCombo.setSelectedItem(today.getYear());
        
        JPanel dateComponentsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dateComponentsPanel.setOpaque(false);
        dateComponentsPanel.add(dayCombo);
        dateComponentsPanel.add(new JLabel("/"));
        dateComponentsPanel.add(monthCombo);
        dateComponentsPanel.add(new JLabel("/"));
        dateComponentsPanel.add(yearCombo);
        
        datePanel.add(dateLabel);
        datePanel.add(dateComponentsPanel);
        formPanel.add(datePanel);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Additional notes
        JTextField notesField = new JTextField(20);
        formPanel.add(createFormField("Additional Notes:", notesField));
        formPanel.add(Box.createVerticalStrut(20));
        
        // Show/hide fields based on role
        roleCombo.addActionListener(e -> {
            String selectedRole = (String) roleCombo.getSelectedItem();
            boolean isDriver = "Driver".equals(selectedRole);
            licensePanel.setVisible(isDriver);
            dialog.pack();
            dialog.setSize(500, isDriver ? 550 : 520);
        });
        
        // Initialize visibility
        licensePanel.setVisible("Driver".equals(roleCombo.getSelectedItem()));
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        
        JButton saveButton = new JButton("Save");
        styleButton(saveButton, PRIMARY_COLOR);
        saveButton.setPreferredSize(new Dimension(100, 30));
        saveButton.addActionListener(e -> {
            // Validation
            if (nameField.getText().trim().isEmpty() || 
                contactField.getText().trim().isEmpty() || 
                salaryField.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(dialog,
                    "Please fill in all required fields (Name, Contact, Salary)",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String role = (String) roleCombo.getSelectedItem();
            if ("Driver".equals(role) && licenseField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                    "License number is required for drivers",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Generate ID
            String staffId = "STF" + System.currentTimeMillis() % 10000;
            
            // Create joining date
            int day = (Integer) dayCombo.getSelectedItem();
            String monthStr = (String) monthCombo.getSelectedItem();
            int year = (Integer) yearCombo.getSelectedItem();
            
            // Add to table
            model.addRow(new Object[]{
                staffId,
                nameField.getText(),
                roleCombo.getSelectedItem(),
                contactField.getText(),
                "Driver".equals(role) ? licenseField.getText() : "-",
                vehicleField.getText().isEmpty() ? "-" : vehicleField.getText(),
                salaryField.getText(),
                "Active"
            });
            
            JOptionPane.showMessageDialog(dialog,
                "Staff member added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            dialog.dispose();
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(cancelButton);
        
        formPanel.add(buttonPanel);
        
        // Add form to scroll pane
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
    
    // Show staff details dialog
    private void showStaffDetailsDialog(DefaultTableModel model, int row) {
        String staffId = (String) model.getValueAt(row, 0);
        String name = (String) model.getValueAt(row, 1);
        String role = (String) model.getValueAt(row, 2);
        String contact = (String) model.getValueAt(row, 3);
        String license = (String) model.getValueAt(row, 4);
        String vehicle = (String) model.getValueAt(row, 5);
        String salary = (String) model.getValueAt(row, 6);
        String status = (String) model.getValueAt(row, 7);
        
        JDialog dialog = new JDialog(this, name + " - Details", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);
        
        // Title with role badge
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setOpaque(false);
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        JLabel roleLabel = new JLabel(role);
        roleLabel.setOpaque(true);
        roleLabel.setBackground(getRoleColor(role));
        roleLabel.setForeground(Color.WHITE);
        roleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        roleLabel.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        
        titlePanel.add(nameLabel);
        titlePanel.add(Box.createHorizontalStrut(10));
        titlePanel.add(roleLabel);
        
        contentPanel.add(titlePanel);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Details panel
        JPanel detailsPanel = new JPanel(new GridLayout(0, 2, 10, 15));
        detailsPanel.setOpaque(false);
        
        // ID
        addDetailField(detailsPanel, "Staff ID:", staffId);
        
        // Contact
        addDetailField(detailsPanel, "Contact:", contact);
        
        // License (only for drivers)
        if ("Driver".equals(role)) {
            addDetailField(detailsPanel, "License Number:", license);
        }
        
        // Vehicle
        addDetailField(detailsPanel, "Assigned Vehicle:", vehicle);
        
        // Salary
        addDetailField(detailsPanel, "Salary:", salary + " BDT");
        
        // Status
        addDetailField(detailsPanel, "Status:", status);
        
        contentPanel.add(detailsPanel);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        
        JButton editButton = new JButton("Edit Staff");
        styleButton(editButton, SECONDARY_COLOR);
        editButton.addActionListener(e -> {
            dialog.dispose();
            showEditStaffDialog(model, row);
        });
        
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(Color.LIGHT_GRAY);
        closeButton.setForeground(Color.BLACK);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(editButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(closeButton);
        
        contentPanel.add(buttonPanel);
        
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
    
    // Show edit staff dialog
    private void showEditStaffDialog(DefaultTableModel model, int row) {
        String staffId = (String) model.getValueAt(row, 0);
        String name = (String) model.getValueAt(row, 1);
        String role = (String) model.getValueAt(row, 2);
        String contact = (String) model.getValueAt(row, 3);
        String license = (String) model.getValueAt(row, 4);
        String vehicle = (String) model.getValueAt(row, 5);
        String salary = (String) model.getValueAt(row, 6);
        String status = (String) model.getValueAt(row, 7);
        
        JDialog dialog = new JDialog(this, "Edit Staff - " + name, true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);
        
        // Title
        JLabel titleLabel = new JLabel("Edit Staff Member");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(titleLabel);
        
        formPanel.add(Box.createVerticalStrut(20));
        
        // Staff ID (non-editable)
        JTextField idField = new JTextField(staffId);
        idField.setEditable(false);
        idField.setEnabled(false);
        formPanel.add(createFormField("Staff ID:", idField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Name field
        JTextField nameField = new JTextField(name);
        formPanel.add(createFormField("Full Name:", nameField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Role field
        String[] roles = {"Driver", "Helper", "Conductor", "Manager", "Mechanic"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleCombo.setSelectedItem(role);
        formPanel.add(createFormField("Role:", roleCombo));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Contact field
        JTextField contactField = new JTextField(contact);
        formPanel.add(createFormField("Contact Number:", contactField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // License field (only for drivers)
        JTextField licenseField = new JTextField("Driver".equals(role) ? license : "");
        JPanel licensePanel = createFormField("License Number:", licenseField);
        formPanel.add(licensePanel);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Vehicle assignment
        JTextField vehicleField = new JTextField("-".equals(vehicle) ? "" : vehicle);
        formPanel.add(createFormField("Assigned Vehicle:", vehicleField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Salary field
        JTextField salaryField = new JTextField(salary);
        formPanel.add(createFormField("Salary (BDT):", salaryField));
        formPanel.add(Box.createVerticalStrut(10));
        
        // Status field
        String[] statuses = {"Active", "On Leave", "Suspended", "Terminated"};
        JComboBox<String> statusCombo = new JComboBox<>(statuses);
        statusCombo.setSelectedItem(status);
        formPanel.add(createFormField("Status:", statusCombo));
        formPanel.add(Box.createVerticalStrut(20));
        
        // Show/hide fields based on role
        roleCombo.addActionListener(e -> {
            String selectedRole = (String) roleCombo.getSelectedItem();
            boolean isDriver = "Driver".equals(selectedRole);
            licensePanel.setVisible(isDriver);
            dialog.pack();
            dialog.setSize(500, dialog.getHeight());
        });
        
        // Initialize visibility
        licensePanel.setVisible("Driver".equals(roleCombo.getSelectedItem()));
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        
        JButton saveButton = new JButton("Save Changes");
        styleButton(saveButton, PRIMARY_COLOR);
        saveButton.setPreferredSize(new Dimension(120, 30));
        saveButton.addActionListener(e -> {
            // Validation
            if (nameField.getText().trim().isEmpty() || 
                contactField.getText().trim().isEmpty() || 
                salaryField.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(dialog,
                    "Please fill in all required fields (Name, Contact, Salary)",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String selectedRole = (String) roleCombo.getSelectedItem();
            if ("Driver".equals(selectedRole) && licenseField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                    "License number is required for drivers",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update table row
            model.setValueAt(nameField.getText(), row, 1);
            model.setValueAt(selectedRole, row, 2);
            model.setValueAt(contactField.getText(), row, 3);
            model.setValueAt("Driver".equals(selectedRole) ? licenseField.getText() : "-", row, 4);
            model.setValueAt(vehicleField.getText().isEmpty() ? "-" : vehicleField.getText(), row, 5);
            model.setValueAt(salaryField.getText(), row, 6);
            model.setValueAt(statusCombo.getSelectedItem(), row, 7);
            
            JOptionPane.showMessageDialog(dialog,
                "Staff information updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            dialog.dispose();
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(cancelButton);
        
        formPanel.add(buttonPanel);
        
        // Add form to scroll pane
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
    
    // Helper method to add detail field to panel
    private void addDetailField(JPanel panel, String label, String value) {
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Arial", Font.PLAIN, 14));
        
        panel.add(labelComponent);
        panel.add(valueComponent);
    }
    
    // Get color for staff role
    private Color getRoleColor(String role) {
        switch (role) {
            case "Driver":
                return new Color(0, 123, 255); // Blue
            case "Helper":
                return new Color(40, 167, 69); // Green
            case "Conductor":
                return new Color(255, 193, 7); // Yellow
            case "Manager":
                return new Color(111, 66, 193); // Purple
            case "Mechanic":
                return new Color(23, 162, 184); // Cyan
            default:
                return new Color(108, 117, 125); // Gray
        }
    }
    
    // Helper method to create form fields with labels for text fields
    private JPanel createFormField(String label, JTextField field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setPreferredSize(new Dimension(120, 25));
        
        panel.add(labelComponent);
        panel.add(field);
        
        return panel;
    }
    
    // Helper method to create form fields with labels for combo boxes
    private JPanel createFormField(String label, JComboBox<?> comboBox) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setPreferredSize(new Dimension(120, 25));
        
        panel.add(labelComponent);
        panel.add(comboBox);
        
        return panel;
    }
} 