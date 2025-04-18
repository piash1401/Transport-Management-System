/**
 * Simple launcher for the Bangladesh Transport System
 * This class is used to launch the application from the command line
 */
public class RunBTS {
    public static void main(String[] args) {
        try {
            // Launch the demo application
            System.out.println("Starting Bangladesh Transport System...");
            BangladeshTransportDemo.main(args);
        } catch (Exception e) {
            System.err.println("Error starting the application: " + e.getMessage());
            e.printStackTrace();
            System.out.println("\nPress Enter to exit...");
            try {
                System.in.read();
            } catch (Exception ex) {
                // Ignore
            }
        }
    }
} 