import javax.swing.*;
import java.awt.*;

public class SplashScreen {
    JFrame frame;
    JLabel image = new JLabel(new ImageIcon("img/icon.png"));
    JLabel text = new JLabel("PASSWORD MANAGER");
    JLabel text2 = new JLabel("Developer: Varsha Praveen");
    JProgressBar progressBar = new JProgressBar();
    JLabel message = new JLabel(); // JLabel for displaying the message

    SplashScreen() {
        createGUI();
        addImage();
        addText();
        addProgressBar();
        addMessage();
        addTitle();
        runningPBar();
    }

    public void createGUI() {
        frame = new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setVisible(true);
    }

    public void addImage() {
        image.setSize(600, 200);
        frame.add(image);
    }

    public void addText() {
        text.setFont(new Font("arial", Font.BOLD, 30));
        text.setBounds(130, 200, 600, 60);
        text.setForeground(Color.WHITE);
        frame.add(text);
    }

    public void addTitle() {
        text2.setFont(new Font("arial", Font.BOLD, 17));
        text2.setBounds(20, 370, 400, 20);
        text2.setForeground(Color.GREEN);
        frame.add(text2);
    }

    public void addMessage() {
        message.setBounds(230, 320, 200, 40); // Setting size and location
        message.setForeground(Color.BLACK); // Setting foreground color
        message.setFont(new Font("arial", Font.BOLD, 15)); // Setting font properties
        frame.add(message); // Adding label to the frame
    }

    public void addProgressBar() {
        progressBar.setBounds(100, 280, 400, 30);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.BLACK);
        progressBar.setForeground(Color.YELLOW);
        progressBar.setValue(0);
        frame.add(progressBar);
    }

    public void runningPBar() {
        int i = 0; // Initial progress value
        while (i <= 100) {
            try {
                Thread.sleep(40); // Pause for 40 milliseconds
                progressBar.setValue(i); // Set progress bar value
                message.setText("LOADING....(" + i + "%)"); // Update loading message
                i++;
                if (i == 100) {
                    frame.dispose(); // Close splash screen
                    new MainMenu(); // Open Main Menu after splash screen
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}