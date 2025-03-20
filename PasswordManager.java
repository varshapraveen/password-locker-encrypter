import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordManager implements ActionListener {

    //Store password class reference
    HashtablePassword data = new HashtablePassword(15,0.5F,0);

    JFrame frame;
    JFrame frame2;
    Container conn1,conn2;
    JLabel lAcc,lPass;
    JTextArea encryptPasswdArea, genePassArea, searchPassArea;
    JButton PassGeneBtn,PassEncryptBtn, PassStoreBtn, PassSearchBtn, AccAddBtn, PassDeleteBtn;
    JTextField tAcc,tPass;
    @Override
    public void actionPerformed(ActionEvent e) { }

    //Frame settings
    public static void FrameGUI(JFrame frame){
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
    }

    //container settings
    public static void ContainerGUI(Container conn){
        conn.setVisible(true);
        conn.setBackground(Color.getHSBColor(20.4f, 10.5f, 12.9f));
        conn.setLayout(null);
    }


    // buttons settings
    public void GUIButtonsSetting(JButton btn){
        btn.setBackground(Color.LIGHT_GRAY);
        btn.setForeground(Color.BLACK);
        Font fn = new Font("Arial", Font.PLAIN, 15);
        btn.setFont(fn);
        Cursor crs = new Cursor(Cursor.HAND_CURSOR);
        btn.setCursor(crs);
    }

    //GUI of Store password
    public void StoringGUI()
    {
        frame2 = new JFrame("Store your passwords");
        frame2.setBounds(1400, 700, 600, 500);
        frame2.setSize(500,400);
        FrameGUI(frame2);
        conn2 = frame2.getContentPane();
        ContainerGUI(conn2);
        Font fn = new Font("Arial", Font.BOLD, 20);

        //Account textFiled and label
        lAcc = new JLabel("ENTER ACCOUNT NAME");
        lAcc.setBounds(100, 23, 480, 50);
        lAcc.setFont(fn);
        conn2.add(lAcc);

        tAcc = new JTextField();
        tAcc.setBounds(100,70,300,80);
        tAcc.setFont(fn);
        tAcc.setForeground(Color.DARK_GRAY);
        conn2.add(tAcc);

        //Account password textField and label
        lPass = new JLabel("ENTER ACCOUNT PASSWORD");
        lPass.setBounds(100, 160, 480, 50);
        lPass.setFont(fn);
        conn2.add(lPass);

        tPass = new JTextField();
        tPass.setBounds(100,200,300,80);
        tPass.setFont(fn);
        tPass.setForeground(Color.DARK_GRAY);
        conn2.add(tPass);

        AccAddBtn = new JButton("STORE");
        AccAddBtn.setBounds(170, 290, 150, 50);
        conn2.add(AccAddBtn);
        GUIButtonsSetting(AccAddBtn);
    }

    //for password generator and encryption
    public void textArea(String Pass,JTextArea TA){
        TA.setText(Pass);
        Font fn = new Font("Arial", Font.BOLD, 20);
        TA.setWrapStyleWord(true);
        TA.setLineWrap(true);
        TA.setCaretPosition(0);
        TA.setEditable(false);
        TA.setFont(fn);

    }
    PasswordManager() {
        frame = new JFrame("Password Manager");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.png"));
//        frame.setBounds(300, 100, 700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(580,630);
        FrameGUI(frame);

        conn1 = frame.getContentPane();
        ContainerGUI(conn1);


        //Generator buttons settings
        PassGeneBtn = new JButton("GENERATE PASSWORD");
        PassGeneBtn.setBounds(160, 80, 220, 70);
        conn1.add(PassGeneBtn);
        GUIButtonsSetting(PassGeneBtn);
//----------------------------------------------------------------------------------------------------------------------
        //generating password
        PassGeneBtn.addActionListener(e -> {
            if (PassGeneBtn == e.getSource()) {
                try {
                    String input = JOptionPane.showInputDialog("Enter the password length (minimum 8):");

                    // Validate that input is not null or empty
                    if (input == null || input.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(conn1, "Input cannot be empty.", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Parse the input to an integer
                    int len;
                    try {
                        len = Integer.parseInt(input.trim());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(conn1, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Check that length is valid
                    if (len < 8) {
                        JOptionPane.showMessageDialog(conn1, "Password length must be at least 8 characters.", "Invalid Length", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Generate the password
                    PasswordGenerator generator = new PasswordGenerator();
                    String password = generator.generatePassword(len);

                    // Display the generated password in a text area
                    genePassArea = new JTextArea(5, 4);
                    textArea(password, genePassArea);

                    JOptionPane.showMessageDialog(conn1, new JScrollPane(genePassArea), "Copy your password", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(conn1, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        //----------------------------------------------------------------------------------------------------------------------
        //Encryption Button
        PassEncryptBtn = new JButton("ENCRYPT PASSWORD");
        GUIButtonsSetting(PassEncryptBtn);
        PassEncryptBtn.setBounds(160, 180, 220, 70);
        conn1.add(PassEncryptBtn);
        PassEncryptBtn.addActionListener(e -> {
                    if (PassEncryptBtn == e.getSource()) {
                        try {
                            String simplePasswd = JOptionPane.showInputDialog("Enter your Password");
                            if (!simplePasswd.isEmpty()) {
                                byte[] salt = passwordEncryption.getSalt();
                                String encPass = passwordEncryption.get_SHA_1_SecurePassword(simplePasswd, salt);
                                //txtArea adding in the panel
                                encryptPasswdArea = new JTextArea(7, 4);
                                textArea(encPass, encryptPasswdArea);
                                JOptionPane.showMessageDialog(conn1, new JScrollPane(encryptPasswdArea), "Copy your Encrypted password", JOptionPane.INFORMATION_MESSAGE);
                            } else JOptionPane.showMessageDialog(conn1, "Please enter password!");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(conn1, ex.getMessage(), "EXIT", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
        );

//----------------------------------------------------------------------------------------------------------------------

        //storing password using hashtable
        PassStoreBtn = new JButton("STORE PASSWORD");
        PassStoreBtn.setBounds(160, 280, 220, 70);
        conn1.add(PassStoreBtn);
        GUIButtonsSetting(PassStoreBtn);
        //Store password action
        PassStoreBtn.addActionListener(e -> {
                    if(PassStoreBtn ==e.getSource())
                    {
                        try{
                            StoringGUI();
                            // action on the Store btn
                            AccAddBtn.addActionListener(e4 -> {
                                        if (AccAddBtn == e4.getSource()) {
                                            String account_name = tAcc.getText();
                                            String acc_pass = tPass.getText();
                                            if (account_name.isEmpty() && acc_pass.isEmpty()) {
                                                JOptionPane.showMessageDialog(conn2,"unable to store your password!","ERROR",JOptionPane.ERROR_MESSAGE);
                                            }
                                            else{
                                                //calling put method of the hashtablePassword class
                                                data.add_Acc(account_name,acc_pass);
                                                JOptionPane.showMessageDialog(conn2, "Account added Successfully !");
                                                tAcc.setText(null);
                                                tPass.setText(null);
                                            }
                                        }
                                    }
                            );
                        }
                        catch(Exception ex) {JOptionPane.showMessageDialog(conn2,ex.getMessage(),"EXIT",JOptionPane.ERROR_MESSAGE);}
                    }
                }
        );

//----------------------------------------------------------------------------------------------------------------------
        //searching password
        PassSearchBtn = new JButton("SEARCH PASSWORD");
        GUIButtonsSetting(PassSearchBtn);
        PassSearchBtn.setBounds(160, 380, 220, 70);
        conn1.add(PassSearchBtn);
        PassSearchBtn.addActionListener(e ->{
                    if (PassSearchBtn ==e.getSource()){
                        try{
                            String acc_name = JOptionPane.showInputDialog("Enter your Account Name");
                            if (!acc_name.isBlank()) {
                                Object pass = data.get_Acc(acc_name.toLowerCase());
                                if(pass!=null) {
                                    searchPassArea = new JTextArea(4,5);
                                    textArea(String.valueOf(pass), searchPassArea);
                                    JOptionPane.showMessageDialog(conn1, new JScrollPane(searchPassArea), "Copy your password", JOptionPane.INFORMATION_MESSAGE);
                                }
                                else JOptionPane.showMessageDialog(conn1, "Account not Found!");
                            }
                        }
                        catch (Exception ex){
                            JOptionPane.showMessageDialog(conn1,ex.getMessage(),"EXIT",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
        );
//----------------------------------------------------------------------------------------------------------------------
        // deleting password
        PassDeleteBtn = new JButton("DELETE PASSWORD");
        GUIButtonsSetting(PassDeleteBtn);
        PassDeleteBtn.setBounds(160, 480, 220, 70);
        conn1.add(PassDeleteBtn);
        PassDeleteBtn.addActionListener(e -> {
                    if (PassDeleteBtn == e.getSource()) {
                        try {
                            String acc_name = JOptionPane.showInputDialog("Enter the Account Name");
                            if (!acc_name.isBlank()) {
                                data.remove_Acc(acc_name.toLowerCase());
                                JOptionPane.showMessageDialog(conn1, "Delete successfully!");
                            }
                            else JOptionPane.showMessageDialog(conn1, "Account not found!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(conn1, ex.getMessage(), "EXIT", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
        );

    }
    //----------------------------------------------------------------------------------------------------------------------------
    // main method
    public static void main(String[] args) {
        //loading screen class
        new SplashScreen();
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
            new PasswordManager();
        }catch (Exception ex) { ex.printStackTrace(); }
    }
}




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
