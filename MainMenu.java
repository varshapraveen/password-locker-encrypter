import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenu {
    // Reference to the password storage class
    private HashtablePassword data = new HashtablePassword(15, 0.5F, 0);

    public MainMenu() {
        // Main frame settings
        JFrame frame = new JFrame("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new GridLayout(5, 1, 10, 10));

        // Buttons for main menu
        JButton generatePassword = new JButton("GENERATE PASSWORD");
        JButton encryptPassword = new JButton("ENCRYPT PASSWORD");
        JButton storePassword = new JButton("STORE PASSWORD");
        JButton searchPassword = new JButton("SEARCH PASSWORD");
        JButton deletePassword = new JButton("DELETE PASSWORD");

        // Add action listeners for each button
        generatePassword.addActionListener(this::onGeneratePassword);
        encryptPassword.addActionListener(this::onEncryptPassword);
        storePassword.addActionListener(this::onStorePassword);
        searchPassword.addActionListener(this::onSearchPassword);
        deletePassword.addActionListener(this::onDeletePassword);

        // Add buttons to the frame
        frame.add(generatePassword);
        frame.add(encryptPassword);
        frame.add(storePassword);
        frame.add(searchPassword);
        frame.add(deletePassword);

        frame.setVisible(true);
    }

    // Action for generating passwords
    private void onGeneratePassword(ActionEvent e) {
        try {
            int length = Integer.parseInt(JOptionPane.showInputDialog("Enter a number (greater than 8) to generate password:"));
            if (length < 8) {
                JOptionPane.showMessageDialog(null, "Password length must be at least 8!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            PasswordGenerator generator = new PasswordGenerator();
            String generatedPassword = generator.generatePassword(length);

            // Custom options for the dialog
            Object[] options = {"OK", "Cancel", "Store"};
            int choice = JOptionPane.showOptionDialog(
                    null,
                    "Generated Password: " + generatedPassword,
                    "Password Generator",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 2) { // Store button
                String account = JOptionPane.showInputDialog("Enter the Account Name to store the generated password:");
                if (account != null && !account.isEmpty()) {
                    data.add_Acc(account.toLowerCase(), generatedPassword); // Store the generated password
                    JOptionPane.showMessageDialog(null, "Generated password successfully stored!", "Store Password", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Account name is required to store the password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Action for encrypting passwords
    private void onEncryptPassword(ActionEvent e) {
        String input = JOptionPane.showInputDialog("Enter your Password:");
        if (input != null && !input.isEmpty()) {
            try {
                byte[] salt = passwordEncryption.getSalt(); // Get the salt
                String encryptedPassword = passwordEncryption.get_SHA_1_SecurePassword(input, salt);

                // Custom options for the dialog
                Object[] options = {"OK", "Cancel", "Store"};
                int choice = JOptionPane.showOptionDialog(
                        null,
                        "Encrypted Password: " + encryptedPassword,
                        "Password Encryption",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (choice == 2) { // Store button
                    String account = JOptionPane.showInputDialog("Enter the Account Name to store the encrypted password:");
                    if (account != null && !account.isEmpty()) {
                        data.add_Acc(account.toLowerCase(), encryptedPassword); // Store the encrypted password
                        JOptionPane.showMessageDialog(null, "Encrypted password successfully stored!", "Store Password", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Account name is required to store the password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error during encryption: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No password entered!", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Action for storing passwords
    private void onStorePassword(ActionEvent e) {
        String account = JOptionPane.showInputDialog("Enter the Account Name:");
        String password = JOptionPane.showInputDialog("Enter the Account Password:");
        if (account != null && password != null && !account.isEmpty() && !password.isEmpty()) {
            data.add_Acc(account.toLowerCase(), password); // Store the account-password pair
            JOptionPane.showMessageDialog(null, "Account successfully stored!", "Store Password", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input. Both fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Action for searching passwords
    private void onSearchPassword(ActionEvent e) {
        String account = JOptionPane.showInputDialog("Enter the Account Name to search:");
        if (account != null && !account.isEmpty()) {
            Object password = data.get_Acc(account.toLowerCase()); // Retrieve the password
            if (password != null) {
                JOptionPane.showMessageDialog(null, "Password for " + account + ": " + password, "Search Password", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Action for deleting passwords
    private void onDeletePassword(ActionEvent e) {
        String account = JOptionPane.showInputDialog("Enter the Account Name to delete:");
        if (account != null && !account.isEmpty()) {
            data.remove_Acc(account.toLowerCase()); // Remove the account
            JOptionPane.showMessageDialog(null, "Account successfully deleted!", "Delete Password", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter an account name.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
