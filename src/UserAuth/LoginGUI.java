package UserAuth;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class LoginGUI extends JFrame {
    public JTextField usernameField;
    public JPasswordField passwordField;
    private LoginController loginController;

    private JButton loginButton;

    public LoginGUI() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        // Create a panel for the login form
        JPanel loginPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImageIcon = new ImageIcon(getClass().getResource("/img/cafe.jpg"));
                Image image = backgroundImageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        loginPanel.setOpaque(false);
        loginPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // Create a container panel to hold all components
        JPanel contentPanel = new JPanel(new GridBagLayout()) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 400);

            }
        };
        contentPanel.setBackground(new Color(255, 255, 255, 150));



        loginPanel.add(contentPanel);



        // Create label for the title
        JLabel titleLabel = new JLabel("ĐĂNG NHẬP");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(148, 0, 211));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        contentPanel.add(titleLabel, constraints);


        // Create an ImageIcon for the account icon
        ImageIcon accountIcon = new ImageIcon(getClass().getResource("/img/account.png")); // Change this path

        // Resize the icon
        Image iconAccountImage = accountIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon resizedIconAccount = new ImageIcon(iconAccountImage);

        // Create labels, text fields, and buttons
        JLabel usernameLabel = new JLabel("Username");
        usernameField = new JTextField(30);
        usernameField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE,1,true), BorderFactory.createEmptyBorder(5, 30, 5, 5)));
        usernameField.setPreferredSize(new Dimension(250, 40));
        constraints.gridx = 0;
        constraints.gridy = 1;
        // Create a label with the resized account icon
        JLabel iconAccountLabel = new JLabel(resizedIconAccount);
        // Create a panel to hold the icon and the text field
        JPanel panelAccount = new JPanel(new BorderLayout(10, 10));
        panelAccount.setBorder(BorderFactory.createLineBorder(Color.BLUE,1,true));
        iconAccountLabel.setBackground(Color.WHITE);
        panelAccount.add(iconAccountLabel, BorderLayout.WEST);
        panelAccount.add(usernameField, BorderLayout.CENTER);
        contentPanel.add(panelAccount, constraints);

        constraints.gridx = 1;
        contentPanel.add(usernameLabel, constraints);

        ImageIcon passwordIcon = new ImageIcon(getClass().getResource("/img/password.png"));
        // Resize the icon
        Image iconImagePasword = passwordIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon resizedIconPassword = new ImageIcon(iconImagePasword);



        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.BLACK);

        passwordField = new JPasswordField(30);
        passwordField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLUE,1,false), BorderFactory.createEmptyBorder(5, 30, 5, 5)));
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setEchoChar((char) 0);
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setEchoChar('\u2022'); // Hide the password when the field gets focus
                passwordLabel.setText("Password"); // Remove the placeholder
                passwordLabel.setForeground(Color.BLACK); // Set the label color to black
            }

            @Override
            public void focusLost(FocusEvent e) {

            }


        });

        constraints.gridx = 0;
        constraints.gridy = 2;
        // Create a label with the resized account icon
        JLabel iconPaswordLabel = new JLabel(resizedIconPassword);
        // Create a panel to hold the icon and the text field
        JPanel panelPassword = new JPanel(new BorderLayout(10, 10));
        panelPassword.setBorder(BorderFactory.createLineBorder(Color.BLUE,1,true));
        iconPaswordLabel.setBackground(Color.WHITE);
        panelPassword.add(iconPaswordLabel, BorderLayout.WEST);
        panelPassword.add(passwordField, BorderLayout.CENTER);
        contentPanel.add(panelPassword, constraints);

        constraints.gridx = 1;
        contentPanel.add(passwordLabel, constraints);

        // Create a custom button for Login
        loginButton = new JButton("Đăng Nhập") {
            @Override
            public Dimension getPreferredSize() {
                Dimension originalSize = super.getPreferredSize();
                return new Dimension(titleLabel.getPreferredSize().width, originalSize.height);
            }
        };

        // Create a gradient panel for the Login button
        GradientPanel buttonGradient = new GradientPanel(Color.decode("#ad5389"), Color.decode("#3c1053"));
        buttonGradient.add(loginButton);

        // Styling for Login button
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.addActionListener(e -> {
            // Handle login logic here
        });

        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER; // Align the button to the center
        contentPanel.add(buttonGradient, constraints);

        // Add the content panel to the login panel
        loginPanel.add(contentPanel);

        // Add the login panel to the frame's content pane
        getContentPane().add(loginPanel);

        // Initialize the login controller and pass the LoginGUI object
        loginController = new LoginController(this);

        // Add event listener for the Login button
        loginButton.addActionListener(loginController);

        // Set the frame visible
        setVisible(true);
    }



    // Custom panel for gradient background
    public static class GradientPanel extends JPanel {
        private final Color startColor;
        private final Color endColor;

        public GradientPanel(Color startColor, Color endColor) {
            this.startColor = startColor;
            this.endColor = endColor;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, startColor, w, h, endColor);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }




    public static void loginGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginGUI loginGUIFrame = new LoginGUI();
                loginGUIFrame.setVisible(true);
            }
        });
    }
}
