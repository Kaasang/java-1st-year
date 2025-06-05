/**
 * The GymGUI class represents the graphical user interface (GUI) for the gym management system.
 * It allows the user to interact with the system by adding members, activating/deactivating memberships,
 * marking attendance, and displaying member details. This class uses Java Swing components for the interface
 * and contains multiple buttons and input fields to collect member information.
 * 
 * The following actions are implemented in the GUI:
 * - Add Regular Member
 * - Add Premium Member
 * - Activate Membership
 * - Deactivate Membership
 * - Mark Attendance
 * - Revert Member
 * - Display Member Details
 * - Clear Form Fields
 * 
 * The GUI includes form fields for entering member details such as ID, name, location, phone, email, gender,
 * date of birth, and membership plan. 
 * 
 * The members' data is stored in an ArrayList and can be used to manage member information throughout the system.
 * 
 * This class extends JFrame to create a window and uses various Swing components such as JPanel, JButton, JTextField, 
 * JLabel, JComboBox, JRadioButton, and JOptionPane to provide user input and display information.
 * @author(Nipekshya Shakya)
 * @version(13 April 2025)
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;




public class GymGUI extends JFrame {

    private static ArrayList<GymMember> members = new ArrayList<>();
    private JFrame frame;
    RegularMember regularMember;

    // Form field components
    private static JTextField IDField;
    private static JTextField NameField;
    private static JTextField LocationField;
    private static JTextField PhoneField;
    private static JTextField EmailField;
    private static JTextField ReferralSourceField;
    private static JTextField RemovalReasonField;
    private static JTextField TrainerField;
    private static JTextField PremiumPlanField;
    private static JComboBox<String> memberComboBox;
    private static JComboBox<String> yearComboBox;
    private static JComboBox<String> dobMonthComboBox;
    private static JComboBox<String> dayComboBox;
    private static JComboBox<String> MembershipYearComboBox;
    private static JComboBox<String> MembershipmonthsComboBox;
    private static JComboBox<String> MembershipdayComboBox;
    private static ButtonGroup genderGroup;

    private static final String REGULAR_DB_NAME = "regular_members.txt";
    private static final String PREMIUM_DB_NAME = "premium_members.txt";
/**
 * Saves the members' data to external files. It writes regular members to 
 * "regular_members.txt" and premium members to "premium_members.txt". 
 * The data is formatted in a table structure for easy reading.
 * 
 * @throws IOException if an error occurs while writing to the files.
 */

    private void saveMembersToFile() {
        try {
            // Save Regular Members
            FileWriter regularFileWriter = new FileWriter(REGULAR_DB_NAME);
            BufferedWriter regularBufferedWriter = new BufferedWriter(regularFileWriter);

            // Table border constants
            String regularTableBorder = "+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+\n";
            String regularTableWidth = "| %-83s |";

            // Write header for regular members
            regularBufferedWriter.write("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+\n");
            regularBufferedWriter.write("|                                     REGULAR MEMBERS LIST                                                                                                                |\n");
            regularBufferedWriter.write("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+\n");
            
            // Write table header with proper formatting
            regularBufferedWriter.write(String.format("| %-5s | %-15s | %-12s | %-10s | %-20s | %-15s | %-8s | %-7s | %-5s | %-8s |\n",
                "ID", "Name", "Location", "Phone", "Email", "Start Date", "Plan", "Price(£)", "Att.", "Status"));
            regularBufferedWriter.write(regularTableBorder + "\n");

            int regularCount = 0;
            // Write data rows for regular members
            for (GymMember member : members) {
                if (member instanceof RegularMember) {
                    regularCount++;
                    RegularMember regularMember = (RegularMember) member;
                    String name = regularMember.getName();
                    if (name != null && name.length() > 15) {
                        name = name.substring(0, 12) + "...";
                    }
                    
                    String location = regularMember.getLocation();
                    if (location != null && location.length() > 12) {
                        location = location.substring(0, 9) + "...";
                    }
                    
                    String phone = regularMember.getPhone();
                    if (phone != null && phone.length() >= 10) {
                        phone = phone.substring(0, 7) + "...";
                    }
                    
                    String email = regularMember.getEmail();
                    if (email != null && email.length() > 20) {
                        email = email.substring(0, 17) + "...";
                    }
                    
                    String startDate = regularMember.getMembershipStartDate();
                    if (startDate != null && startDate.length() > 15) {
                        startDate = startDate.substring(0, 12) + "...";
                    }
                    
                    String plan = regularMember.getPlan();
                    if (plan != null && plan.length() > 8) {
                        plan = plan.substring(0, 5) + "...";
                    }
                    
                    regularBufferedWriter.write(String.format("| %-5d | %-15s | %-12s | %-10s | %-20s | %-15s | %-8s | %-7.2f | %-5d | %-8s |\n",
                        regularMember.getId(),
                        name,
                        location,
                        phone,
                        email,
                        startDate,
                        plan,
                        regularMember.getPrice(),
                        regularMember.getAttendance(),
                        regularMember.isActiveStatus() ? "Active" : "Inactive"));
                }
            }
            
            // Write footer
            regularBufferedWriter.write(regularTableBorder + "\n");
            regularBufferedWriter.write(String.format(regularTableWidth + "\n", "Total Regular Members: " + regularCount));
            regularBufferedWriter.write("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+\n");
            regularBufferedWriter.close();

            // Save Premium Members
            FileWriter premiumFileWriter = new FileWriter(PREMIUM_DB_NAME);
            BufferedWriter premiumBufferedWriter = new BufferedWriter(premiumFileWriter);

            // Table border constants
            String premiumTableBorder = "+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+\n";
            String premiumTableWidth = "| %-93s |";

            // Write header for premium members
            premiumBufferedWriter.write("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+\n");
            premiumBufferedWriter.write("|                                       PREMIUM MEMBERS LIST                                                                                                                |\n");
            premiumBufferedWriter.write("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+\n");
            
            // Write table header with proper formatting
            premiumBufferedWriter.write(String.format("| %-5s | %-15s | %-12s | %-10s | %-15s | %-15s | %-8s | %-10s | %-7s | %-7s |\n",
                "ID", "Name", "Location", "Phone", "Trainer", "Start Date", "Plan", "Charge(£)", "Points", "Status"));
            premiumBufferedWriter.write(premiumTableBorder + "\n");

            int premiumCount = 0;
            // Write data rows for premium members
            for (GymMember member : members) {
                if (member instanceof PremiumMember) {
                    premiumCount++;
                    PremiumMember premiumMember = (PremiumMember) member;
                    String name = premiumMember.getName();
                    if (name != null && name.length() > 15) {
                        name = name.substring(0, 12) + "...";
                    }
                    
                    String location = premiumMember.getLocation();
                    if (location != null && location.length() > 12) {
                        location = location.substring(0, 9) + "...";
                    }
                    
                    String phone = premiumMember.getPhone();
                    if (phone != null && phone.length() > 10) {
                        phone = phone.substring(0, 7) + "...";
                    }
                    
                    String trainer = premiumMember.getPersonalTrainer();
                    if (trainer != null && trainer.length() > 15) {
                        trainer = trainer.substring(0, 12) + "...";
                    }
                    
                    String startDate = premiumMember.getMembershipStartDate();
                    if (startDate != null && startDate.length() > 15) {
                        startDate = startDate.substring(0, 12) + "...";
                    }
                    
                    String plan = premiumMember.getPlan();
                    if (plan != null && plan.length() > 8) {
                        plan = plan.substring(0, 5) + "...";
                    }
                    
                    premiumBufferedWriter.write(String.format("| %-5d | %-15s | %-12s | %-10s | %-15s | %-15s | %-8s | %-10.2f | %-7.0f | %-7s |\n",
                        premiumMember.getId(),
                        name,
                        location,
                        phone,
                        trainer,
                        startDate,
                        plan,
                        premiumMember.getPremiumCharge(),
                        premiumMember.getLoyaltyPoints(),
                        premiumMember.isActiveStatus() ? "Active" : "Inactive"));
                }
            }
            
            // Write footer
            premiumBufferedWriter.write(premiumTableBorder + "\n");
            premiumBufferedWriter.write(String.format(premiumTableWidth + "\n", "Total Premium Members: " + premiumCount));
            premiumBufferedWriter.write("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+\n");
            premiumBufferedWriter.close();

            JOptionPane.showMessageDialog(frame, 
                "Data saved successfully !\n" +
                "Regular members: " + REGULAR_DB_NAME + "\n" +
                "Premium members: " + PREMIUM_DB_NAME, 
                "Save Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, 
                "Error saving data to files: " + e.getMessage(),
                "Save Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
 /**
 * Calculates the discount amount for a given member based on their loyalty points
 * and member type. The discount is applied depending on the loyalty points thresholds.
 *
 * @param member The GymMember whose discount is to be calculated.
 * @return The calculated discount amount.
 */   
    private double calculateDiscountAmount(GymMember member) {
        double discountAmount = 0.0;
        double basePrice;
        
        // Get base price based on member type
        if (member instanceof RegularMember) {
            basePrice = ((RegularMember) member).getPrice();
        } else if (member instanceof PremiumMember) {
            basePrice = ((PremiumMember) member).getPremiumCharge();
        } else {
            return 0.0;
        }
        
        // Calculate discount based on loyalty points
        double loyaltyPoints = member.getLoyaltyPoints();
        if (loyaltyPoints >= 100.0) {
            discountAmount = Math.round(basePrice * 0.15 * 100.0) / 100.0; // 15% discount
        } else if (loyaltyPoints >= 50.0) {
            discountAmount = Math.round(basePrice * 0.10 * 100.0) / 100.0; // 10% discount
        } else if (loyaltyPoints >= 25.0) {
            discountAmount = Math.round(basePrice * 0.05 * 100.0) / 100.0; // 5% discount
        }

        // Additional discount for premium members
        if (member instanceof PremiumMember) {
            discountAmount += Math.round(basePrice * 0.05 * 100.0) / 100.0; // Extra 5% for premium members
        }

        return Math.round(discountAmount * 100.0) / 100.0; // Round final amount to 2 decimal places
    }
/**
 * Reads member data from external files. It loads regular members from 
 * "regular_members.txt" and premium members from "premium_members.txt".
 * The method handles file reading, data parsing, and member object creation.
 * It includes error handling for file operations and data validation.
 */
    private void readMembersFromFile() {
        members.clear(); // Clear existing members before loading
        boolean regularFileRead = false;
        boolean premiumFileRead = false;
        int regularCount = 0;
        int premiumCount = 0;

        // Read Regular Members
        try {
            File regularFile = new File(REGULAR_DB_NAME);
            if (regularFile.exists()) {
                BufferedReader regularReader = new BufferedReader(new FileReader(regularFile));
                String line;
                boolean dataSection = false;
                
                while ((line = regularReader.readLine()) != null) {
                    // Skip header lines and find start of data
                    if (line.contains("REGULAR MEMBERS LIST")) {
                        dataSection = false;
                        continue;
                    }
                    if (line.contains("ID") && line.contains("Name") && line.contains("Location")) {
                        dataSection = true;
                        continue;
                    }
                    if (!dataSection || line.trim().isEmpty() || line.startsWith("+")) {
                        continue;
                    }

                    // Parse member data
                    try {
                        String[] data = line.split("\\|");
                        if (data.length >= 10) {
                            int id = Integer.parseInt(data[1].trim());
                            String name = data[2].trim();
                            String location = data[3].trim();
                            String phone = data[4].trim();
                            String email = data[5].trim();
                            String startDate = data[6].trim();
                            String plan = data[7].trim();
                            String refSource = data[9].trim();
                            boolean isActive = data[10].trim().equals("Active");

                            // Create RegularMember
                            RegularMember member = new RegularMember(
                                id, name, location, phone, email,
                                "Not Specified", // Default gender
                                startDate, plan, refSource
                            );
                            
                            if (!isActive) {
                                member.deactivateMembership();
                            }
                            
                            members.add(member);
                            regularCount++;
                        }
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                        System.err.println("Error parsing regular member data: " + line);
                        continue;
                    }
                }
                regularReader.close();
                regularFileRead = true;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame,
                "Error reading regular members file: " + e.getMessage(),
                "File Read Error",
                JOptionPane.ERROR_MESSAGE);
        }

        // Read Premium Members
        try {
            File premiumFile = new File(PREMIUM_DB_NAME);
            if (premiumFile.exists()) {
                BufferedReader premiumReader = new BufferedReader(new FileReader(premiumFile));
                String line;
                boolean dataSection = false;
                
                while ((line = premiumReader.readLine()) != null) {
                    // Skip header lines and find start of data
                    if (line.contains("PREMIUM MEMBERS LIST")) {
                        dataSection = false;
                        continue;
                    }
                    if (line.contains("ID") && line.contains("Name") && line.contains("Location")) {
                        dataSection = true;
                        continue;
                    }
                    if (!dataSection || line.trim().isEmpty() || line.startsWith("+")) {
                        continue;
                    }

                    // Parse member data
                    try {
                        String[] data = line.split("\\|");
                        if (data.length >= 11) {
                            int id = Integer.parseInt(data[1].trim());
                            String name = data[2].trim();
                            String location = data[3].trim();
                            String phone = data[4].trim();
                            String gender = data[5].trim();
                            String dob = data[6].trim();
                            String plan = data[7].trim();
                            String trainer = data[8].trim();
                            double charge = Double.parseDouble(data[9].trim().replace("£", ""));
                            boolean isActive = data[11].trim().equals("Active");

                            // Create PremiumMember
                            PremiumMember member = new PremiumMember(
                                id, name, location, phone,
                                gender,
                                dob,
                                trainer,
                                plan,
                                charge
                            );
                            
                            if (!isActive) {
                                member.deactivateMembership();
                            }
                            
                            members.add(member);
                            premiumCount++;
                        }
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                        System.err.println("Error parsing premium member data: " + line);
                        continue;
                    }
                }
                premiumReader.close();
                premiumFileRead = true;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame,
                "Error reading premium members file: " + e.getMessage(),
                "File Read Error",
                JOptionPane.ERROR_MESSAGE);
        }

        // Show summary message
        StringBuilder summary = new StringBuilder();
        summary.append("Data loading complete:\n\n");
        
        if (regularFileRead) {
            summary.append(String.format("Regular Members: %d loaded\n", regularCount));
        } else {
            summary.append("Regular Members: File not found or error reading file\n");
        }
        
        if (premiumFileRead) {
            summary.append(String.format("Premium Members: %d loaded\n", premiumCount));
        } else {
            summary.append("Premium Members: File not found or error reading file\n");
        }
        
        summary.append(String.format("\nTotal Members Loaded: %d", regularCount + premiumCount));

        JOptionPane.showMessageDialog(frame,
            summary.toString(),
            "Load Summary",
            JOptionPane.INFORMATION_MESSAGE);
    }

/**
 * Adds a button to calculate discounts for members. Prompts the user for a member ID,
 * finds the corresponding member, calculates the discount, and displays the results.
 *
 * @param panel The JPanel to which the button will be added.
 */
    private void addCalculateDiscountButton(JPanel panel) {
        JButton calculateDiscountButton = new JButton("Calculate Discount");
        calculateDiscountButton.setBounds(60, 380, 200, 30);
        panel.add(calculateDiscountButton);
        calculateDiscountButton.setBackground(new Color(105, 105, 105));
        calculateDiscountButton.setForeground(Color.WHITE);
        calculateDiscountButton.addActionListener(e -> {
            // Get member ID
            String idInput = JOptionPane.showInputDialog(frame, "Enter member ID to calculate discount:");
            if (idInput == null || idInput.trim().isEmpty()) {
                return; // User cancelled or entered empty ID
            }

            try {
                int id = Integer.parseInt(idInput);
                boolean found = false;

                // Find the member and calculate discount
                for (GymMember member : members) {
                    if (member.getId() == id) {
                        found = true;
                        double basePrice;
                        String memberType;
                        
                        if (member instanceof RegularMember) {
                            basePrice = ((RegularMember) member).getPrice();
                            memberType = "Regular";
                        } else if (member instanceof PremiumMember) {
                            basePrice = ((PremiumMember) member).getPremiumCharge();
                            memberType = "Premium";
                        } else {
                            basePrice = 0.0;
                            memberType = "Unknown";
                        }

                        double discountAmount = calculateDiscountAmount(member);
                        double finalPrice = basePrice - discountAmount;

                        // Create a detailed message
                        String message = String.format(
                            "Member Details:\n" +
                            "----------------\n" +
                            "ID: %d\n" +
                            "Name: %s\n" +
                            "Type: %s\n" +
                            "Base Price: £%.2f\n" +
                            "Loyalty Points: %.0f\n" +
                            "Discount Amount: £%.2f\n" +
                            "Final Price: £%.2f",
                            member.getId(),
                            member.getName(),
                            memberType,
                            basePrice,
                            member.getLoyaltyPoints(),
                            discountAmount,
                            finalPrice
                        );

                        JOptionPane.showMessageDialog(frame, 
                            message,
                            "Discount Calculation", 
                            JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                }

                if (!found) {
                    JOptionPane.showMessageDialog(frame, 
                        "No member found with ID: " + id,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, 
                    "Invalid ID format. Please enter a valid number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
/**
 * Adds a button to clear all form fields in the GUI. Shows a confirmation dialog 
 * before clearing the fields.
 *
 * @param panel The JPanel to which the button will be added.
 */
    private void addClearButton(JPanel panel) {
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(300, 380, 200, 30);
        panel.add(clearButton);
        clearButton.setBackground(new Color(105, 105, 105));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> {
            // Show confirmation dialog
            int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to clear all fields?",
                "Confirm Clear",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Clear text fields
                IDField.setText("");
                NameField.setText("");
                LocationField.setText("");
                PhoneField.setText("");
                EmailField.setText("");
                ReferralSourceField.setText("");
                RemovalReasonField.setText("");
                TrainerField.setText("");
                PremiumPlanField.setText("");

                // Reset combo boxes to default values
                memberComboBox.setSelectedIndex(0);
                yearComboBox.setSelectedIndex(0);
                dobMonthComboBox.setSelectedIndex(0);
                dayComboBox.setSelectedIndex(0);
                MembershipYearComboBox.setSelectedIndex(0);
                MembershipmonthsComboBox.setSelectedIndex(0);
                MembershipdayComboBox.setSelectedIndex(0);

                // Reset radio buttons
                genderGroup.clearSelection();

                // Show success message
                JOptionPane.showMessageDialog(
                    frame,
                    "All fields have been cleared successfully!",
                    "Clear Successful",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }
/**
 * Adds a button to process payment dues for members. Prompts for member ID, 
 * checks membership status, calculates due amounts, and confirms payment details.
 *
 * @param panel The JPanel to which the button will be added.
 */

    private void addPayDueButton(JPanel panel) {
        JButton payDueButton = new JButton("Pay Due");
        payDueButton.setBounds(540, 380, 200, 30);
        panel.add(payDueButton);
        payDueButton.setBackground(new Color(105, 105, 105));
        payDueButton.setForeground(Color.WHITE);
        payDueButton.addActionListener(e -> {
            // Get member ID
            String idInput = JOptionPane.showInputDialog(frame, "Enter member ID to pay dues:");
            if (idInput == null || idInput.trim().isEmpty()) {
                return; // User cancelled or entered empty ID
            }

            try {
                int id = Integer.parseInt(idInput);
                boolean found = false;

                // Find the member and process payment
                for (GymMember member : members) {
                    if (member.getId() == id) {
                        found = true;
                        
                        // Check if member is active
                        if (!member.isActiveStatus()) {
                            int activate = JOptionPane.showConfirmDialog(
                                frame,
                                "This member's membership is currently inactive. Do you want to activate it?",
                                "Inactive Membership",
                                JOptionPane.YES_NO_OPTION
                            );
                            
                            if (activate == JOptionPane.YES_OPTION) {
                                member.activateMembership();
                            } else {
                                JOptionPane.showMessageDialog(
                                    frame,
                                    "Payment cancelled. Membership remains inactive.",
                                    "Payment Cancelled",
                                    JOptionPane.INFORMATION_MESSAGE
                                );
                                return;
                            }
                        }
                        
                        // Calculate due amount based on member type
                        double dueAmount = 0.0;
                        String memberType = "";
                        
                        if (member instanceof RegularMember) {
                            RegularMember regularMember = (RegularMember) member;
                            dueAmount = regularMember.getPrice();
                            memberType = "Regular";
                        } else if (member instanceof PremiumMember) {
                            PremiumMember premiumMember = (PremiumMember) member;
                            dueAmount = premiumMember.getPremiumCharge();
                            memberType = "Premium";
                        }
                        
                        // Apply any discount
                        double discountAmount = calculateDiscountAmount(member);
                        double finalAmount = dueAmount - discountAmount;
                        
                        // Create payment information message
                        String paymentInfo = String.format(
                            "Member Details:\n" +
                            "-----------------\n" +
                            "ID: %d\n" +
                            "Name: %s\n" +
                            "Type: %s\n" +
                            "Due Amount: £%.2f\n" +
                            "Discount: £%.2f\n" +
                            "Final Amount Due: £%.2f\n\n" +
                            "Proceed with payment?",
                            member.getId(),
                            member.getName(),
                            memberType,
                            dueAmount,
                            discountAmount,
                            finalAmount
                        );
                        
                        // Confirm payment
                        int confirmPayment = JOptionPane.showConfirmDialog(
                            frame,
                            paymentInfo,
                            "Payment Confirmation",
                            JOptionPane.YES_NO_OPTION
                        );
                        
                        if (confirmPayment == JOptionPane.YES_OPTION) {
                            // Process payment
                            String paymentMethod = (String) JOptionPane.showInputDialog(
                                frame,
                                "Select payment method:",
                                "Payment Method",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                new String[]{"Cash", "Credit Card", "Debit Card", "Bank Transfer"},
                                "Cash"
                            );
                            
                            if (paymentMethod != null) {
                                // Add loyalty points for payment (cast to int for whole points)
                                int pointsEarned = (int) (finalAmount / 10); // 1 point for every £10
                                
                                // Safely add loyalty points
                                if (pointsEarned > 0) {
                                    member.addLoyaltyPoints(pointsEarned);
                                }
                                
                                // Update payment status based on member type
                                if (member instanceof PremiumMember) {
                                    ((PremiumMember) member).payDueAmount(finalAmount);
                                }
                                
                                JOptionPane.showMessageDialog(
                                    frame,
                                    String.format(
                                        "Payment Successful!\n" +
                                        "Amount Paid: £%.2f\n" +
                                        "Payment Method: %s\n" +
                                        "Loyalty Points Earned: %d\n" +
                                        "New Total Loyalty Points: %.0f",
                                        finalAmount,
                                        paymentMethod,
                                        pointsEarned,
                                        member.getLoyaltyPoints()
                                    ),
                                    "Payment Complete",
                                    JOptionPane.INFORMATION_MESSAGE
                                );
                            }
                        } else {
                            JOptionPane.showMessageDialog(
                                frame,
                                "Payment cancelled.",
                                "Payment Cancelled",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                        break;
                    }
                }

                if (!found) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "No member found with ID: " + id,
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Invalid ID format. Please enter a valid number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    /**
     * Adds a button to load members from files. When clicked, it reads member data
     * from the regular and premium member files and displays a summary of loaded data.
     *
     * @param panel The JPanel to which the button will be added.
     */
    private void addLoadMembersButton(JPanel panel) {
        JButton loadMembersButton = new JButton("Load Members");
        loadMembersButton.setBounds(780, 380, 200, 30);
        panel.add(loadMembersButton);
        loadMembersButton.setBackground(new Color(105, 105, 105));
        loadMembersButton.setForeground(Color.WHITE);
        
        loadMembersButton.addActionListener(e -> {
            // Show confirmation dialog if there are existing members
            if (!members.isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Loading members will clear existing data. Do you want to continue?",
                    "Confirm Load",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            
            // Check if files exist before loading
            File regularFile = new File(REGULAR_DB_NAME);
            File premiumFile = new File(PREMIUM_DB_NAME);
            
            if (!regularFile.exists() && !premiumFile.exists()) {
                JOptionPane.showMessageDialog(
                    frame,
                    "No member files found!\n" +
                    "Expected files:\n" +
                    "- " + REGULAR_DB_NAME + "\n" +
                    "- " + PREMIUM_DB_NAME,
                    "Files Not Found",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            // Call the readMembersFromFile method
            readMembersFromFile();
        });
    }

    public static void main(String[] args) {
        GymGUI gui = new GymGUI();
        gui.frame = new JFrame("GYM Management System");
        gui.frame.setLayout(null);

        JLabel label = new JLabel("GYM Management System");
        label.setBounds(500, 30, 300, 20);

        // panel
        JPanel panel = new JPanel();
        panel.setBounds(50, 60, 1100, 550);
        panel.setBorder(BorderFactory.createTitledBorder("GYM Member"));
        panel.setLayout(null);
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setOpaque(true);
        gui.frame.add(panel);

        // panel ID Label
        JLabel ID = new JLabel("ID:");
        ID.setBounds(20, 40, 100, 30);
        panel.add(ID);

        // panel 1 ID Field
        gui.IDField = new JTextField();
        gui.IDField.setBounds(150, 40, 250, 30);
        panel.add(gui.IDField);

        // panel 1 Name Label
        JLabel Name = new JLabel(" Name:");
        Name.setBounds(20, 80, 100, 30);
        panel.add(Name);

        // panel 1 Name Field
        gui.NameField = new JTextField();
        gui.NameField.setBounds(150, 80, 250, 30);
        panel.add(gui.NameField);

        // panel Location Label
        JLabel LocationLabel = new JLabel("Location:");
        LocationLabel.setBounds(20, 120, 100, 30);
        panel.add(LocationLabel);

        // panel Location Field
        gui.LocationField = new JTextField();
        gui.LocationField.setBounds(150, 120, 250, 30);
        panel.add(gui.LocationField);

        // panel Phone Label
        JLabel PhoneLabel = new JLabel("Phone:");
        PhoneLabel.setBounds(20, 160, 100, 30);
        panel.add(PhoneLabel);

        gui.PhoneField = new JTextField();
        gui.PhoneField.setBounds(150, 160, 250, 30);
        panel.add(gui.PhoneField);

        // panel email label
        JLabel EmailLabel = new JLabel("Email:");
        EmailLabel.setBounds(20, 200, 100, 30);
        panel.add(EmailLabel);

        // email textfield
        gui.EmailField = new JTextField();
        gui.EmailField.setBounds(150, 200, 250, 30);
        panel.add(gui.EmailField);

        // DOB Label
        JLabel DOBLabel = new JLabel("DOB:");
        DOBLabel.setBounds(20, 240, 250, 30);
        panel.add(DOBLabel);

        // Year ComboBox
        String[] years = new String[76];
        for (int i = 0; i < 76; i++) {
            years[i] = String.valueOf(1950 + i);
        }
        gui.yearComboBox = new JComboBox<>(years);
        gui.yearComboBox.setBounds(150, 240, 80, 30);
        panel.add(gui.yearComboBox);

        // Month ComboBox
        String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        gui.dobMonthComboBox = new JComboBox<>(months);
        gui.dobMonthComboBox.setBounds(250, 240, 80, 30);
        panel.add(gui.dobMonthComboBox);

        // Day ComboBox
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }
        gui.dayComboBox = new JComboBox<>(days);
        gui.dayComboBox.setBounds(350, 240, 50, 30);
        panel.add(gui.dayComboBox);

        // Gender Label
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(20, 280, 60, 30);
        panel.add(genderLabel);

        // Gender Radio Button
        JRadioButton maleButton = new JRadioButton("Male");
        maleButton.setBounds(150, 280, 80, 30);
        panel.add(maleButton);

        JRadioButton femaleButton = new JRadioButton("Female");
        femaleButton.setBounds(250, 280, 80, 30);
        panel.add(femaleButton);

        gui.genderGroup = new ButtonGroup();
        gui.genderGroup.add(maleButton);
        gui.genderGroup.add(femaleButton);

        // Referral Source Label
        JLabel ReferralSourceLabel = new JLabel("Referral Source:");
        ReferralSourceLabel.setBounds(550, 40, 100, 30);
        panel.add(ReferralSourceLabel);

        // Referral Source Field
        gui.ReferralSourceField = new JTextField();
        gui.ReferralSourceField.setBounds(700, 40, 250, 30);
        panel.add(gui.ReferralSourceField);

        // Paid Amount Label
        JLabel PaidAmountLabel = new JLabel("Paid Amount:");
        PaidAmountLabel.setBounds(550, 80, 250, 30);
        panel.add(PaidAmountLabel);

        // Paid Amount Field
        JTextField PaidAmountField = new JTextField("1000");
        PaidAmountField.setBounds(700, 80, 250, 30);
        PaidAmountField.setEditable(false);
        PaidAmountField.setForeground(Color.RED);
        panel.add(PaidAmountField);

        // Removal Reason Label
        JLabel RemovalReasonLabel = new JLabel("Removal Reason:");
        RemovalReasonLabel.setBounds(550, 120, 250, 30);
        panel.add(RemovalReasonLabel);

        // Removal Reason Field
        gui.RemovalReasonField = new JTextField();
        gui.RemovalReasonField.setBounds(700, 120, 250, 30);
        panel.add(gui.RemovalReasonField);

        // Trainer's Name Label
        JLabel TrainerLabel = new JLabel("Trainer's Name:");
        TrainerLabel.setBounds(550, 160, 250, 30);
        panel.add(TrainerLabel);

        // Trainer's Name Field
        gui.TrainerField = new JTextField();
        gui.TrainerField.setBounds(700, 160, 250, 30);
        panel.add(gui.TrainerField);

        // Membership Type
        JLabel planJLabel = new JLabel("Plan:");
        planJLabel.setBounds(550, 200, 150, 30);
        panel.add(planJLabel);

        // Membership Type Combo Box
        String[] planType = { "Select Plan", "Basic", "Standard", "Deluxe" };
        gui.memberComboBox = new JComboBox<>(planType);
        gui.memberComboBox.setBounds(700, 200, 250, 30);
        panel.add(gui.memberComboBox);

        // Membership Start Date Label
        JLabel MembershipDateLabel = new JLabel("Membership Start Date:");
        MembershipDateLabel.setBounds(550, 240, 250, 30);
        panel.add(MembershipDateLabel);

        // Year ComboBox
        String[] MembershipYear = new String[76];
        for (int i = 0; i < 76; i++) {
            MembershipYear[i] = String.valueOf(1950 + i);
        }
        gui.MembershipYearComboBox = new JComboBox<>(MembershipYear);
        gui.MembershipYearComboBox.setBounds(700, 240, 80, 30);
        panel.add(gui.MembershipYearComboBox);

        // Month ComboBox
        String[] MembershipMonths = { "January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December" };
        gui.MembershipmonthsComboBox = new JComboBox<>(MembershipMonths);
        gui.MembershipmonthsComboBox.setBounds(800, 240, 80, 30);
        panel.add(gui.MembershipmonthsComboBox);

        // Day ComboBox
        String[] MembershipDay = new String[31];
        for (int i = 0; i < 31; i++) {
            MembershipDay[i] = String.valueOf(i + 1);
        }
        gui.MembershipdayComboBox = new JComboBox<>(MembershipDay);
        gui.MembershipdayComboBox.setBounds(900, 240, 50, 30);
        panel.add(gui.MembershipdayComboBox);

        // Plan Charge Label
        JLabel PremiumPlanCharge = new JLabel("PremiumPlanCharge:");
        PremiumPlanCharge.setBounds(550, 280, 250, 30);
        panel.add(PremiumPlanCharge);

        // Plan Charge Field
        gui.PremiumPlanField = new JTextField();
        gui.PremiumPlanField.setBounds(700, 280, 250, 30);
        panel.add(gui.PremiumPlanField);

        // Add Regular Member Button
        JButton addRegularMemberButton = new JButton("Add Regular Member");
        addRegularMemberButton.setBounds(60, 420, 200, 30);
        panel.add(addRegularMemberButton);
        addRegularMemberButton.setBackground(new Color(105, 105, 105));
        addRegularMemberButton.setForeground(Color.WHITE);
        addRegularMemberButton.addActionListener(e -> {
            // Check for empty required fields
            if (IDField.getText().isEmpty() || NameField.getText().isEmpty() || PhoneField.getText().isEmpty() || EmailField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(gui.frame, "Error: Please fill in all required fields!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if fields are empty
            }
            
            // Parse ID
            int id;
            try {
                id = Integer.parseInt(IDField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gui.frame, "Error: ID must be a number!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if ID is not a number
            }
        
            // Check for duplicate ID
            for (GymMember member : members) {
                if (member.getId() == id) {
                    JOptionPane.showMessageDialog(gui.frame, "Error: A member with this ID already exists!", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit if ID already exists
                }
            }
        
            // Get input values
            String name = NameField.getText();
            String location = LocationField.getText();
            String phone = PhoneField.getText();
            String email = EmailField.getText();
            String gender = maleButton.isSelected() ? "Male" : "Female"; // Assuming you have gender selection
            String dob = yearComboBox.getSelectedItem() + "-" +
                         (dobMonthComboBox.getSelectedIndex() + 1) + "-" + // Months are 0-based
                         dayComboBox.getSelectedItem();
            String membershipStartDate = MembershipYearComboBox.getSelectedItem() + "-" +
                                          (MembershipmonthsComboBox.getSelectedIndex() + 1) + "-" + // Months are 0-based
                                          MembershipdayComboBox.getSelectedItem();
            String refferalSource = ReferralSourceField.getText();
            
        
            // Create and add the regular member
           RegularMember regularMember = new RegularMember(id, name, location, phone, email, gender, dob, membershipStartDate,refferalSource
           );
            members.add(regularMember);
        
            JOptionPane.showMessageDialog(gui.frame, "Regular member added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        

        // Add Premium Member Button
        JButton addPremiumMemberButton = new JButton("Add Premium Member");
        addPremiumMemberButton.setBounds(300, 420, 200, 30);
        panel.add(addPremiumMemberButton);
        addPremiumMemberButton.setBackground(new Color(105, 105, 105));
        addPremiumMemberButton.setForeground(Color.WHITE);
        addPremiumMemberButton.addActionListener(e -> {
            // Show required fields information first
            int result = JOptionPane.showConfirmDialog(gui.frame,
                "Please fill in the following required fields:\n\n" +
                "1. ID (must be a number)\n" +
                "2. Name\n" +
                "3. Location\n" +
                "4. Phone Number\n" +
                "5. Gender (select Male or Female)\n" +
                "6. Date of Birth\n" +
                "7. Trainer's Name\n" +
                "8. Referral Source\n" +
                "9. Premium Plan Charge (must be greater than 0)\n\n" +
                "Note: All fields are mandatory for premium members.",
                "Required Fields Information",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE);

            // Only proceed with validation if user clicks OK
            if (result == JOptionPane.OK_OPTION) {
                // Check for empty required fields
                StringBuilder errorMessage = new StringBuilder();
                
                if (IDField.getText().isEmpty()) {
                    errorMessage.append("ID is required!\n");
                }
                if (NameField.getText().isEmpty()) {
                    errorMessage.append("Name is required!\n");
                }
                if (LocationField.getText().isEmpty()) {
                    errorMessage.append("Location is required!\n");
                }
                if (PhoneField.getText().isEmpty()) {
                    errorMessage.append("Phone number is required!\n");
                }
                if (!maleButton.isSelected() && !femaleButton.isSelected()) {
                    errorMessage.append("Gender must be selected!\n");
                }
                if (TrainerField.getText().isEmpty()) {
                    errorMessage.append("Trainer's name is required!\n");
                }
                if (ReferralSourceField.getText().isEmpty()) {
                    errorMessage.append("Referral source is required!\n");
                }
                if (PremiumPlanField.getText().isEmpty()) {
                    errorMessage.append("Premium plan charge is required!\n");
                }
                
            //If there are any errors, show them and return
                if (errorMessage.length() > 0) {
                    JOptionPane.showMessageDialog(gui.frame, 
                        "Please fill in all required fields:\n" + errorMessage.toString(), 
                        "Input Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Parse ID
                int id;
                try {
                    id = Integer.parseInt(IDField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(gui.frame, 
                        "Error: ID must be a number!", 
                        "Input Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
                // Check for duplicate ID
                for (GymMember member : members) {
                    if (member.getId() == id) {
                        JOptionPane.showMessageDialog(gui.frame, 
                            "Error: A member with this ID already exists!", 
                            "Input Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            
                // Get input values
                String name = NameField.getText();
                String location = LocationField.getText();
                String phone = PhoneField.getText();
                String gender = maleButton.isSelected() ? "Male" : "Female";
                String dob = yearComboBox.getSelectedItem() + "-" +
                             (dobMonthComboBox.getSelectedIndex() + 1) + "-" +
                             dayComboBox.getSelectedItem();
                String referralSource = ReferralSourceField.getText();
                String trainerName = TrainerField.getText();
                String planCharge = PremiumPlanField.getText();
                
                // Validate premium plan charge
                double premiumCharge;
                try {
                    premiumCharge = Double.parseDouble(planCharge);
                    if (premiumCharge <= 0) {
                        JOptionPane.showMessageDialog(gui.frame, 
                            "Error: Premium plan charge must be greater than 0!", 
                            "Input Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(gui.frame, 
                        "Error: Premium plan charge must be a valid number!", 
                        "Input Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            
                // Create and add the premium member
                PremiumMember premiumMember = new PremiumMember(id, name, location, phone, gender, dob, trainerName, referralSource, premiumCharge);
                members.add(premiumMember);
            
                JOptionPane.showMessageDialog(gui.frame, 
                    "Premium member added successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Activate Membership Button
        JButton activateMembershipButton = new JButton("Activate Membership");
        activateMembershipButton.setBounds(540, 420, 200, 30);
        panel.add(activateMembershipButton);
        activateMembershipButton.setBackground(new Color(105, 105, 105));
        activateMembershipButton.setForeground(Color.WHITE);
        activateMembershipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(gui.frame, "Enter id: ");
                try {
                    int id = Integer.parseInt(input);
                    boolean found = false;
                    for (GymMember member : members) {
                        if (member.getId() == id) {
                            member.activateMembership(); 
                            JOptionPane.showMessageDialog(gui.frame, "Membership activated for: " + member.getName());
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        JOptionPane.showMessageDialog(gui.frame, "Member with Id " + id + " not found");
                    }

                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(gui.frame, "Invalid ID input.");
                }
            }
        });

      
         // Deactivate Membership Button
         JButton deactivateMembershipButton = new JButton("Deactivate Membership");
         deactivateMembershipButton.setBounds(780, 420, 200, 30);
         panel.add(deactivateMembershipButton);
         deactivateMembershipButton.setBackground(new Color(105, 105, 105));
         deactivateMembershipButton.setForeground(Color.WHITE);
         deactivateMembershipButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String input = JOptionPane.showInputDialog(gui.frame, "Enter id: ");
                 try {
                     int id = Integer.parseInt(input);
                     boolean found = false;
                     for (GymMember member : members) {
                         if (member.getId() == id) {
                             member.deactivateMembership(); // You'll need to implement this method
                             JOptionPane.showMessageDialog(gui.frame, "Membership deactivated for: " + member.getName());
                             found = true;
                             break;
                         }
                     }
 
                     if (!found) {
                         JOptionPane.showMessageDialog(gui.frame, "Member with Id " + id + " not found");
                     }
 
                 } catch (NumberFormatException exception) {
                     JOptionPane.showMessageDialog(gui.frame, "Invalid ID input.");
                 }
             }
         });
 

        // Mark Attendance Button
        JButton markAttendenceButton = new JButton("Mark Attendance");
        markAttendenceButton.setBounds(60, 460, 200, 30);
        panel.add(markAttendenceButton);
        markAttendenceButton.setBackground(new Color(105, 105, 105));
        markAttendenceButton.setForeground(Color.WHITE);
        markAttendenceButton.addActionListener(e -> {
            // Mark attendance logic here
            JOptionPane.showMessageDialog(gui.frame, "Attendance marked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        // Upgrade Plan Button
        JButton upgradePlanButton = new JButton("Upgrade Plan");
        upgradePlanButton.setBounds(60, 500, 200, 30);  // Moved below Mark Attendance
        panel.add(upgradePlanButton);
        upgradePlanButton.setBackground(new Color(105, 105, 105));
        upgradePlanButton.setForeground(Color.WHITE);
        upgradePlanButton.addActionListener(e -> {
            // Get member ID
            String idInput = JOptionPane.showInputDialog(gui.frame, "Enter regular member ID to upgrade plan:");
            if (idInput == null || idInput.trim().isEmpty()) {
                return; // User cancelled or entered empty ID
            }

            try {
                int id = Integer.parseInt(idInput);
                boolean found = false;

                // Find the member and upgrade their plan
                for (GymMember member : members) {
                    if (member.getId() == id) {
                        if (member instanceof RegularMember) {
                            found = true;
                            RegularMember regularMember = (RegularMember) member;
                            
                            // Show current plan
                            JOptionPane.showMessageDialog(gui.frame, 
                                "Current Plan: " + regularMember.getPlan() + "\n" +
                                "Current Price: " + regularMember.getPrice(),
                                "Current Plan Information",
                                JOptionPane.INFORMATION_MESSAGE);

                            // Get new plan
                            String[] plans = {"Basic", "Standard", "Deluxe"};
                            String newPlan = (String) JOptionPane.showInputDialog(gui.frame,
                                "Select new plan:",
                                "Upgrade Plan",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                plans,
                                regularMember.getPlan());

                            if (newPlan != null) {
                                String result = regularMember.upgradePlan(newPlan);
                                JOptionPane.showMessageDialog(gui.frame, result);
                            }
                        } else {
                            JOptionPane.showMessageDialog(gui.frame, 
                                "This ID belongs to a premium member. Only regular members can use this upgrade option.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    }
                }

                if (!found) {
                    JOptionPane.showMessageDialog(gui.frame, 
                        "No member found with ID: " + id,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gui.frame, 
                    "Invalid ID format. Please enter a valid number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // Revert Member Button
        JButton revertMemberButton = new JButton("Revert Member");
        revertMemberButton.setBounds(300, 460, 200, 30);
        panel.add(revertMemberButton);
        revertMemberButton.setBackground(new Color(105, 105, 105));
        revertMemberButton.setForeground(Color.WHITE);
        revertMemberButton.addActionListener(e -> {
            // Get member ID
            String idInput = JOptionPane.showInputDialog(gui.frame, "Enter member ID to revert:");
            if (idInput == null || idInput.trim().isEmpty()) {
                return; // User cancelled or entered empty ID
            }

            try {
                int id = Integer.parseInt(idInput);
                boolean found = false;

                // Find the member and revert them
                for (GymMember member : members) {
                    if (member.getId() == id) {
                        found = true;
                        // Get removal reason
                        String removalReason = JOptionPane.showInputDialog(gui.frame, 
                            "Enter reason for reverting member " + member.getName() + ":");
                        
                        if (removalReason == null) {
                            return; // User cancelled
                        }

                        if (member instanceof RegularMember) {
                            RegularMember regularMember = (RegularMember) member;
                            regularMember.revertRegularMember(removalReason);
                            JOptionPane.showMessageDialog(gui.frame, 
                                "Regular member " + member.getName() + " has been reverted successfully!", 
                                "Success", 
                                JOptionPane.INFORMATION_MESSAGE);
                        } else if (member instanceof PremiumMember) {
                            PremiumMember premiumMember = (PremiumMember) member;
                            premiumMember.revertPremiumMember();
                            JOptionPane.showMessageDialog(gui.frame, 
                                "Premium member " + member.getName() + " has been reverted successfully!", 
                                "Success", 
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                    }
                }

                if (!found) {
                    JOptionPane.showMessageDialog(gui.frame, 
                        "No member found with ID: " + id, 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gui.frame, 
                    "Invalid ID format. Please enter a valid number.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // Display Members Button
        JButton displayButton = new JButton("Display Members");
        displayButton.setBounds(540, 460, 200, 30);
        panel.add(displayButton);
        displayButton.setBackground(new Color(105, 105, 105));
        displayButton.setForeground(Color.WHITE);
        displayButton.addActionListener(e -> {
            if (members.isEmpty()) {
                JOptionPane.showMessageDialog(gui.frame, "No members to display.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Create a tabbed pane to show both regular and premium members
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setBackground(Color.WHITE);

            // Create table models for regular and premium members
            String[] regularColumns = {"ID", "Name", "Location", "Phone", "Email", "Gender", "DOB", "Plan", "Ref Source", "Status"};
            String[] premiumColumns = {"ID", "Name", "Location", "Phone", "Gender", "DOB", "Plan", "Personal Trainer", "Charge", "Paid", "Status"};
            
            DefaultTableModel regularModel = new DefaultTableModel(regularColumns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table read-only
                }
            };
            
            DefaultTableModel premiumModel = new DefaultTableModel(premiumColumns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table read-only
                }
            };

            // Add data to the models
            for (GymMember member : members) {
                if (member instanceof RegularMember) {
                    RegularMember regularMember = (RegularMember) member;
                    Object[] row = {
                        regularMember.getId(),
                        regularMember.getName(),
                        regularMember.getLocation(),
                        regularMember.getPhone(),
                        regularMember.getEmail(),
                        regularMember.getGender(),
                        regularMember.getDOB(),
                        regularMember.getPlan(),
                        regularMember.getReferralSource(),
                        regularMember.isActiveStatus() ? "Active" : "Inactive"
                    };
                    regularModel.addRow(row);
                } else if (member instanceof PremiumMember) {
                    PremiumMember premiumMember = (PremiumMember) member;
                    Object[] row = {
                        premiumMember.getId(),
                        premiumMember.getName(),
                        premiumMember.getLocation(),
                        premiumMember.getPhone(),
                        premiumMember.getGender(),
                        premiumMember.getDOB(),
                        premiumMember.getPlan(),
                        premiumMember.getPersonalTrainer(),
                        String.format("£%.2f", premiumMember.getPremiumCharge()),
                        String.format("£%.2f", premiumMember.getPaidAmount()),
                        premiumMember.isActiveStatus() ? "Active" : "Inactive"
                    };
                    premiumModel.addRow(row);
                }
            }

            // Create tables
            JTable regularTable = new JTable(regularModel);
            JTable premiumTable = new JTable(premiumModel);

            // Style the tables
            regularTable.setRowHeight(25);
            premiumTable.setRowHeight(25);
            
            // Set header style
            regularTable.getTableHeader().setBackground(new Color(105, 105, 105));
            regularTable.getTableHeader().setForeground(Color.WHITE);
            regularTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
            
            premiumTable.getTableHeader().setBackground(new Color(105, 105, 105));
            premiumTable.getTableHeader().setForeground(Color.WHITE);
            premiumTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

            // Set column widths
            regularTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
            regularTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Name
            regularTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Location
            regularTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Phone
            regularTable.getColumnModel().getColumn(4).setPreferredWidth(200); // Email
            regularTable.getColumnModel().getColumn(5).setPreferredWidth(80);  // Gender
            regularTable.getColumnModel().getColumn(6).setPreferredWidth(100); // DOB
            regularTable.getColumnModel().getColumn(7).setPreferredWidth(80);  // Plan
            regularTable.getColumnModel().getColumn(8).setPreferredWidth(100); // Ref Source
            regularTable.getColumnModel().getColumn(9).setPreferredWidth(80);  // Status

            premiumTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
            premiumTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Name
            premiumTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Location
            premiumTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Phone
            premiumTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Gender
            premiumTable.getColumnModel().getColumn(5).setPreferredWidth(100); // DOB
            premiumTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // Plan
            premiumTable.getColumnModel().getColumn(7).setPreferredWidth(150); // Personal Trainer
            premiumTable.getColumnModel().getColumn(8).setPreferredWidth(100); // Charge
            premiumTable.getColumnModel().getColumn(9).setPreferredWidth(100); // Paid
            premiumTable.getColumnModel().getColumn(10).setPreferredWidth(80); // Status

            // Add scroll panes with preferred size
            JScrollPane regularScrollPane = new JScrollPane(regularTable);
            JScrollPane premiumScrollPane = new JScrollPane(premiumTable);
            regularScrollPane.setPreferredSize(new Dimension(900, 400));
            premiumScrollPane.setPreferredSize(new Dimension(900, 400));

            // Add tables to tabbed pane
            tabbedPane.addTab("Regular Members", regularScrollPane);
            tabbedPane.addTab("Premium Members", premiumScrollPane);

            // Create a panel to hold the tabbed pane
            JPanel displayPanel = new JPanel(new BorderLayout());
            displayPanel.add(tabbedPane, BorderLayout.CENTER);

            // Show the panel in a dialog
            JOptionPane.showMessageDialog(gui.frame, displayPanel, "Member Details", JOptionPane.INFORMATION_MESSAGE);
        });

        // Save Members Button
        JButton saveButton = new JButton("Save Members");
        saveButton.setBounds(780, 460, 200, 30);
        panel.add(saveButton);
        saveButton.setBackground(new Color(105, 105, 105));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            if (members.isEmpty()) {
                JOptionPane.showMessageDialog(gui.frame,
                    "No members to save!",
                    "Save Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            gui.saveMembersToFile();
        });

        // Add Load Members Button
        gui.addLoadMembersButton(panel);

        // Add Calculate Discount Button
        gui.addCalculateDiscountButton(panel);

        // Add Clear Button
        gui.addClearButton(panel);
        
        // Add Pay Due Button
        gui.addPayDueButton(panel);

        gui.frame.setSize(1200, 700);
        gui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.frame.setVisible(true);

        gui.readMembersFromFile();
    }
}

