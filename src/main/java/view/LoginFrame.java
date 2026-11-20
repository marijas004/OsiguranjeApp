package view;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        initComponents();
    }
    public String getUsername() { return usernameField.getText(); }
    public String getPassword() { return new String(passwordField.getPassword()); }
    public JButton getLoginButton() { return loginButton; }

    private void initComponents() {
        setTitle("Prijava na sistem");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Margine oko panela


        panel.add(new JLabel("Korisničko ime:"));
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 25));
        panel.add(usernameField);

        panel.add(new JLabel("Lozinka:"));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 25));
        panel.add(passwordField);

        loginButton = new JButton("Prijavi se");
        panel.add(new JLabel());
        panel.add(loginButton);


        add(panel);
    }
}
