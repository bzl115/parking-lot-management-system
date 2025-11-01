import java.awt.*;
import javax.swing.*;

public class MainUI {
    static Admin admin = new Admin("admin", "admin123");
    static ParkingSlot[] slots = new ParkingSlot[50];
    static ParkingRecord[] records = new ParkingRecord[50];
    static ParkingManager[] managers = new ParkingManager[50];
    static int recordCount = 0, managerCount = 0, vehicleCount = 0;
    static JFrame mainFrame;
    static ParkingManager loggedInUser = null;

    public static void start() {
        for (int i = 0; i < slots.length; i++)
            slots[i] = new ParkingSlot(i + 1);

        SwingUtilities.invokeLater(MainUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        mainFrame = new JFrame("Parking Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500, 400);
        showLoginPanel();
        mainFrame.setVisible(true);
    }

    static void showLoginPanel() {
        mainFrame.getContentPane().removeAll();
        mainFrame.setTitle("Parking Management System - Login");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; panel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; panel.add(passField, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(loginButton, gbc);

        loginButton.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (admin.login(user, pass)) {
                loggedInUser = admin;
                showAdminMenu();
            } else {
                boolean found = false;
                for (int i = 0; i < managerCount; i++) {
                    if (managers[i].login(user, pass)) {
                        loggedInUser = managers[i];
                        showManagerMenu();
                        found = true; break;
                    }
                }
                if (!found)
                    JOptionPane.showMessageDialog(mainFrame, "Invalid login!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainFrame.setContentPane(panel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    static void showAdminMenu() {
        mainFrame.getContentPane().removeAll();
        mainFrame.setTitle("Admin Menu - Logged in as " + admin.managerId);

        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        JButton assignSlotBtn = new JButton("1. Assign Slot");
        assignSlotBtn.addActionListener(e -> ParkingFunctions.assignSlot(admin));
        panel.add(assignSlotBtn);

        JButton releaseSlotBtn = new JButton("2. Release Slot");
        releaseSlotBtn.addActionListener(e -> ParkingFunctions.releaseSlot());
        panel.add(releaseSlotBtn);

        JButton viewStatusBtn = new JButton("3. View Slot Status");
        viewStatusBtn.addActionListener(e -> ParkingFunctions.viewSlotStatus());
        panel.add(viewStatusBtn);

        JButton viewVehiclesBtn = new JButton("4. View All Vehicle Details");
        viewVehiclesBtn.addActionListener(e -> ParkingFunctions.viewParkedVehicles());
        panel.add(viewVehiclesBtn);

        JButton registerMgrBtn = new JButton("5. Register Manager");
        registerMgrBtn.addActionListener(e -> ParkingFunctions.registerManager());
        panel.add(registerMgrBtn);

        JButton reportBtn = new JButton("6. View Final Report");
        reportBtn.addActionListener(e -> ParkingFunctions.viewFinalReport());
        panel.add(reportBtn);

        JButton logoutBtn = new JButton("7. Logout");
        logoutBtn.addActionListener(e -> showLoginPanel());
        panel.add(logoutBtn);

        mainFrame.setContentPane(new JScrollPane(panel));
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    static void showManagerMenu() {
        mainFrame.getContentPane().removeAll();
        mainFrame.setTitle("Manager Menu - Logged in as " + loggedInUser.managerId);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));

        JButton assignSlotBtn = new JButton("1. Assign Slot");
        assignSlotBtn.addActionListener(e -> ParkingFunctions.assignSlot(loggedInUser));
        panel.add(assignSlotBtn);

        JButton releaseSlotBtn = new JButton("2. Release Slot");
        releaseSlotBtn.addActionListener(e -> ParkingFunctions.releaseSlot());
        panel.add(releaseSlotBtn);

        JButton viewStatusBtn = new JButton("3. View Slot Status");
        viewStatusBtn.addActionListener(e -> ParkingFunctions.viewSlotStatus());
        panel.add(viewStatusBtn);

        JButton viewVehiclesBtn = new JButton("4. View All Vehicle Details");
        viewVehiclesBtn.addActionListener(e -> ParkingFunctions.viewParkedVehicles());
        panel.add(viewVehiclesBtn);

        JButton reportBtn = new JButton("5. View Final Report");
        reportBtn.addActionListener(e -> ParkingFunctions.viewFinalReport());
        panel.add(reportBtn);

        JButton logoutBtn = new JButton("6. Logout");
        logoutBtn.addActionListener(e -> showLoginPanel());
        panel.add(logoutBtn);

        mainFrame.setContentPane(new JScrollPane(panel));
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
