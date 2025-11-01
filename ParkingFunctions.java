import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class ParkingFunctions {
    static void assignSlot(ParkingManager manager) {
        JTextField numField = new JTextField();
        JTextField ownerField = new JTextField();
        JTextField typeField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Vehicle Number:")); inputPanel.add(numField);
        inputPanel.add(new JLabel("Owner Name:")); inputPanel.add(ownerField);
        inputPanel.add(new JLabel("Vehicle Type:")); inputPanel.add(typeField);

        int result = JOptionPane.showConfirmDialog(MainUI.mainFrame, inputPanel, "Assign Slot", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String num = numField.getText();
            String owner = ownerField.getText();
            String type = typeField.getText();

            boolean slotFound = false;
            for (int j = 0; j < MainUI.slots.length; j++) {
                if (MainUI.slots[j].checkAvailability()) {
                    Vehicle v = new Vehicle(MainUI.vehicleCount + 1, num, owner, type);
                    MainUI.vehicleCount++;
                    manager.assignSlotToVehicle(MainUI.slots[j], v);
                    MainUI.records[MainUI.recordCount] = new ParkingRecord(MainUI.recordCount + 1, v, MainUI.slots[j]);
                    MainUI.recordCount++;
                    slotFound = true;

                    try (Connection con = DatabaseConnection.getConnection()) {
                        String query = "INSERT INTO parking_records(vehicle_number, owner_name, vehicle_type, slot_no) VALUES (?, ?, ?, ?)";
                        PreparedStatement ps = con.prepareStatement(query);
                        ps.setString(1, num);
                        ps.setString(2, owner);
                        ps.setString(3, type);
                        ps.setInt(4, MainUI.slots[j].slotNumber);
                        ps.executeUpdate();
                        ps.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(MainUI.mainFrame, "Slot assigned successfully!");
                    break;
                }
            }
            if (!slotFound)
                JOptionPane.showMessageDialog(MainUI.mainFrame, "No slots available!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void releaseSlot() {
        String releaseNum = JOptionPane.showInputDialog(MainUI.mainFrame, "Enter Vehicle Number to release slot:");
        if (releaseNum != null && !releaseNum.trim().isEmpty()) {
            boolean released = false;
            for (int j = 0; j < MainUI.recordCount; j++) {
                if (MainUI.records[j].vehicle.vehicleNumber.equals(releaseNum) && MainUI.records[j].exitTime == 0) {
                    MainUI.records[j].closeRecord();
                    MainUI.records[j].slot.freeSlot();
                    try (Connection con = DatabaseConnection.getConnection()) {
                        String query = "UPDATE parking_records SET exit_time = NOW(), fee = ? WHERE vehicle_number = ? AND exit_time IS NULL";
                        PreparedStatement ps = con.prepareStatement(query);
                        ps.setDouble(1, MainUI.records[j].fee);
                        ps.setString(2, releaseNum);
                        ps.executeUpdate();
                        ps.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(MainUI.mainFrame, "Vehicle exited. Fee = Rs. " + String.format("%.2f", MainUI.records[j].fee));
                    released = true;
                    break;
                }
            }
            if (!released)
                JOptionPane.showMessageDialog(MainUI.mainFrame, "Vehicle not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void viewSlotStatus() {
        StringBuilder status = new StringBuilder();
        for (ParkingSlot slot : MainUI.slots)
            status.append(slot.getStatus()).append("\n");
        JTextArea area = new JTextArea(status.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(MainUI.mainFrame, new JScrollPane(area), "Slot Status", JOptionPane.INFORMATION_MESSAGE);
    }

    static void viewParkedVehicles() {
        StringBuilder details = new StringBuilder("--- All Parked Vehicles ---\n");
        try (Connection con = DatabaseConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM parking_records WHERE exit_time IS NULL");
            boolean found = false;
            while (rs.next()) {
                details.append("Vehicle No: ").append(rs.getString("vehicle_number")).append("\n")
                       .append("Owner: ").append(rs.getString("owner_name")).append("\n")
                       .append("Slot No: ").append(rs.getInt("slot_no")).append("\n\n");
                found = true;
            }
            if (!found) details.append("No vehicles are currently parked.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        JTextArea area = new JTextArea(details.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(MainUI.mainFrame, new JScrollPane(area), "Parked Vehicles", JOptionPane.INFORMATION_MESSAGE);
    }

    static void registerManager() {
        JTextField idField = new JTextField();
        JPasswordField passField = new JPasswordField();

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Manager ID:")); inputPanel.add(idField);
        inputPanel.add(new JLabel("Password:")); inputPanel.add(passField);

        int result = JOptionPane.showConfirmDialog(MainUI.mainFrame, inputPanel, "Register Manager", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            MainUI.managers[MainUI.managerCount++] = MainUI.admin.register(idField.getText(), new String(passField.getPassword()));
            JOptionPane.showMessageDialog(MainUI.mainFrame, "Manager registered successfully!");
        }
    }

    static void viewFinalReport() {
        try (Connection con = DatabaseConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM parking_records WHERE exit_time IS NOT NULL");
            StringBuilder report = new StringBuilder("----- Final Report -----\n");
            double total = 0;
            boolean found = false;
            while (rs.next()) {
                found = true;
                report.append("Record ID: ").append(rs.getInt("record_id")).append("\n")
                      .append("Vehicle No: ").append(rs.getString("vehicle_number")).append("\n")
                      .append("Owner: ").append(rs.getString("owner_name")).append("\n")
                      .append("Slot No: ").append(rs.getInt("slot_no")).append("\n")
                      .append("Fee: Rs. ").append(rs.getDouble("fee")).append("\n")
                      .append("-----------------------------------\n");
                total += rs.getDouble("fee");
            }
            if (!found)
                report.append("No vehicles have exited yet.\n");
            else
                report.append("Total Revenue Collected: Rs. ").append(String.format("%.2f", total)).append("\n");

            JTextArea area = new JTextArea(report.toString());
            area.setEditable(false);
            JOptionPane.showMessageDialog(MainUI.mainFrame, new JScrollPane(area), "Final Report", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
