import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
interface SeatSelectionListener {
    void onSelectionChanged(int selectedCount);
}

public class SeatSelectionPanel extends JPanel {
    private static final int SEAT_WIDTH = 50;
    private static final int SEAT_HEIGHT = 50;
    private static final int SEAT_SPACING = 10;
    private static final Color AVAILABLE_COLOR = new Color(0, 147, 69);  // Green
    private static final Color SELECTED_COLOR = new Color(0, 113, 188);  // Blue
    private static final Color BOOKED_COLOR = new Color(214, 40, 40);    // Red
    private static final Color DISABLED_COLOR = Color.GRAY;

    private int rows;
    private int columns;
    private int totalSeats;
    private int maxSelectable;
    private boolean[][] seatStates;  // true = selected
    private boolean[][] bookedSeats; // true = booked
    private JLabel selectedSeatsLabel;
    private List<SeatSelectionListener> selectionListeners;

    public SeatSelectionPanel(int rows, int columns, int maxSelectable) {
        this.rows = rows;
        this.columns = columns;
        this.totalSeats = rows * columns;
        this.maxSelectable = maxSelectable;
        this.seatStates = new boolean[rows][columns];
        this.bookedSeats = new boolean[rows][columns];
        this.selectionListeners = new ArrayList<>();

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(
            columns * (SEAT_WIDTH + SEAT_SPACING) + SEAT_SPACING,
            rows * (SEAT_HEIGHT + SEAT_SPACING) + SEAT_SPACING + 50
        ));

        // Add legend panel at the top
        add(createLegendPanel(), BorderLayout.NORTH);

        // Add selected seats label at the bottom
        selectedSeatsLabel = new JLabel("Selected seats: None");
        selectedSeatsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        selectedSeatsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        add(selectedSeatsLabel, BorderLayout.SOUTH);

        // Add mouse listener for seat selection
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = (e.getX() - SEAT_SPACING) / (SEAT_WIDTH + SEAT_SPACING);
                int row = (e.getY() - SEAT_SPACING) / (SEAT_HEIGHT + SEAT_SPACING);

                if (row >= 0 && row < rows && col >= 0 && col < columns) {
                    if (!bookedSeats[row][col]) {  // Only toggle if seat is not booked
                        toggleSeat(row, col, maxSelectable);
                        repaint();
                        updateSelectedSeatsLabel();
                        notifySelectionListeners();
                    }
                }
            }
        });
    }

    private JPanel createLegendPanel() {
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        legendPanel.setOpaque(false);

        // Available seats
        addLegendItem(legendPanel, AVAILABLE_COLOR, "Available");
        
        // Selected seats
        addLegendItem(legendPanel, SELECTED_COLOR, "Selected");
        
        // Booked seats
        addLegendItem(legendPanel, BOOKED_COLOR, "Booked");

        return legendPanel;
    }

    private void addLegendItem(JPanel panel, Color color, String text) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        item.setOpaque(false);

        JPanel colorBox = new JPanel();
        colorBox.setPreferredSize(new Dimension(20, 20));
        colorBox.setBackground(color);
        
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));

        item.add(colorBox);
        item.add(label);
        panel.add(item);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw seats
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int x = col * (SEAT_WIDTH + SEAT_SPACING) + SEAT_SPACING;
                int y = row * (SEAT_HEIGHT + SEAT_SPACING) + SEAT_SPACING;

                // Choose color based on seat state
                if (bookedSeats[row][col]) {
                    g2d.setColor(BOOKED_COLOR);
                } else if (seatStates[row][col]) {
                    g2d.setColor(SELECTED_COLOR);
                } else {
                    g2d.setColor(AVAILABLE_COLOR);
                }

                // Draw seat
                g2d.fillRoundRect(x, y, SEAT_WIDTH, SEAT_HEIGHT, 10, 10);
                g2d.setColor(Color.WHITE);
                g2d.drawRoundRect(x, y, SEAT_WIDTH, SEAT_HEIGHT, 10, 10);

                // Draw seat number
                String seatNum = String.valueOf(row * columns + col + 1);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = x + (SEAT_WIDTH - fm.stringWidth(seatNum)) / 2;
                int textY = y + (SEAT_HEIGHT - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(seatNum, textX, textY);
            }
        }
    }

    private void toggleSeat(int row, int col, int maxSelectable) {
        if (!seatStates[row][col]) {
            // Check if we've reached the maximum number of selections
            int currentSelected = getSelectedSeatCount();
            if (currentSelected >= maxSelectable) {
                JOptionPane.showMessageDialog(this,
                    "You can only select " + maxSelectable + " seats.",
                    "Maximum Seats Reached",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        seatStates[row][col] = !seatStates[row][col];
    }

    private int getSelectedSeatCount() {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (seatStates[i][j]) count++;
            }
        }
        return count;
    }

    private void updateSelectedSeatsLabel() {
        List<Integer> selectedSeats = getSelectedSeatNumbers();
        if (selectedSeats.isEmpty()) {
            selectedSeatsLabel.setText("Selected seats: None");
        } else {
            selectedSeatsLabel.setText("Selected seats: " + selectedSeats.toString());
        }
    }

    public List<Integer> getSelectedSeatNumbers() {
        List<Integer> selected = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (seatStates[i][j]) {
                    selected.add(i * columns + j + 1);
                }
            }
        }
        return selected;
    }

    public void setBookedSeats(List<Integer> bookedSeatNumbers) {
        // Reset booked seats
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                bookedSeats[i][j] = false;
            }
        }

        // Set new booked seats
        for (int seatNum : bookedSeatNumbers) {
            if (seatNum > 0 && seatNum <= totalSeats) {
                int row = (seatNum - 1) / columns;
                int col = (seatNum - 1) % columns;
                bookedSeats[row][col] = true;
                // If a booked seat was selected, unselect it
                if (seatStates[row][col]) {
                    seatStates[row][col] = false;
                }
            }
        }
        repaint();
        updateSelectedSeatsLabel();
    }

    public void setMaxSelectable(int maxSelectable) {
        this.maxSelectable = maxSelectable;
        // If current selection exceeds new max, deselect seats from the end
        if (getSelectedSeatCount() > maxSelectable) {
            int toDeselect = getSelectedSeatCount() - maxSelectable;
            List<Integer> selected = getSelectedSeatNumbers();
            for (int i = selected.size() - 1; i >= selected.size() - toDeselect; i--) {
                int seatNum = selected.get(i);
                int row = (seatNum - 1) / columns;
                int col = (seatNum - 1) % columns;
                seatStates[row][col] = false;
            }
            repaint();
            updateSelectedSeatsLabel();
            notifySelectionListeners();
        }
    }

    public void addSeatSelectionListener(SeatSelectionListener listener) {
        selectionListeners.add(listener);
    }

    private void notifySelectionListeners() {
        int selectedCount = getSelectedSeatCount();
        for (SeatSelectionListener listener : selectionListeners) {
            listener.onSelectionChanged(selectedCount);
        }
    }
} 