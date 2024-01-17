package UserAuth;

import CRUD.EmployeeAccountGUI;
import Database.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterStaffGUI extends JFrame {

    private JLabel usernameLabel, phoneNumberLabel, emailLabel, passwordLabel, confirmPasswordLabel;
    public JTextField usernameTextField, phoneNumberTextField, emailTextField;
    public JPasswordField passwordField, confirmPasswordField;
    private JButton registerButton;
    private EmployeeAccountGUI employeeAccountGUI; // Thêm tham chiếu đến EmployeeAccountGUI

    public RegisterStaffGUI(EmployeeAccountGUI employeeAccountGUI) {
        super("Register Staff");
        this.employeeAccountGUI = employeeAccountGUI;; // Lưu tham chiếu đến EmployeeAccountGUI

        // Create a panel for the register form
        JPanel registerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // Create labels, text fields, and button
        usernameLabel = new JLabel("Username:");
        usernameTextField = new JTextField(20);
        phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberTextField = new JTextField(20);
        emailLabel = new JLabel("Email:");
        emailTextField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField(20);
        registerButton = new JButton("Register");

        // Add components to the panel with custom constraints
        constraints.gridx = 0;
        constraints.gridy = 0;
        registerPanel.add(usernameLabel, constraints);

        constraints.gridx = 1;
        registerPanel.add(usernameTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        registerPanel.add(phoneNumberLabel, constraints);

        constraints.gridx = 1;
        registerPanel.add(phoneNumberTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        registerPanel.add(emailLabel, constraints);

        constraints.gridx = 1;
        registerPanel.add(emailTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        registerPanel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        registerPanel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        registerPanel.add(confirmPasswordLabel, constraints);

        constraints.gridx = 1;
        registerPanel.add(confirmPasswordField, constraints);

        // Add the register button to the panel at the bottom center
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(registerButton, BorderLayout.CENTER);
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        registerPanel.add(buttonPanel, constraints);

        // Add the panel to the frame's content pane
        getContentPane().add(registerPanel);

        // Add event listener for the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegisterButtonClick();
            }
        });

        // Pack the frame to fit the preferred size of the content
        pack();
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Set the frame's title icon
        setIconImage(new ImageIcon("resources/register.png").getImage());
    }

    public void handleRegisterButtonClick() {
        // Lấy thông tin từ các trường nhập liệu trên giao diện đăng ký
        String username = usernameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String email = emailTextField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());

        // Kiểm tra xem các trường nhập liệu có bị bỏ trống không
        if (username.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        // Kiểm tra định dạng của email có hợp lệ không
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ.");
            return;
        }

        // Kiểm tra xem mật khẩu có đủ độ dài không
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải chứa ít nhất 6 ký tự.");
            return;
        }

        // Kiểm tra xem mật khẩu và mật khẩu xác nhận có khớp nhau hay không
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu và mật khẩu xác nhận không khớp!");
            return;
        }

        // Kiểm tra xem username và email đã tồn tại trong cơ sở dữ liệu hay chưa
        if (isUsernameExists(username)) {
            JOptionPane.showMessageDialog(this, "Tên người dùng đã tồn tại.");
            return;
        }

        if (isEmailExists(email)) {
            JOptionPane.showMessageDialog(this, "Email đã tồn tại.");
            return;
        }

        // Kiểm tra xem Phone Number có hợp lệ không
        if (!isValidPhoneNumber(phoneNumber)) {
            JOptionPane.showMessageDialog(this, "Phone Number không hợp lệ.");
            return;
        }

        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu (sử dụng hàm hashPassword của bạn)
        String hashedPassword = hashPassword(password);

        // Gọi phương thức registerUser để thêm thông tin tài khoản vào bảng Accounts
        try {
            DatabaseManager.insertAccount(username, phoneNumber, email, password);
            JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
            handleRegisterSuccess();
            this.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể tạo tài khoản.");
        }
    }
    private boolean isUsernameExists(String username) {
        try {
            // Truy vấn cơ sở dữ liệu để kiểm tra xem username đã tồn tại hay chưa
            // Sử dụng lớp DatabaseManager của bạn để thực hiện truy vấn
            return DatabaseManager.isUsernameExists(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        // Cài đặt các điều kiện kiểm tra định dạng email tùy theo yêu cầu của bạn
        // Ví dụ đơn giản là kiểm tra có chứa ký tự @ và có ít nhất một dấu chấm sau ký tự @
        return email.contains("@") && email.indexOf("@") < email.lastIndexOf(".");
    }
    private boolean isEmailExists(String email) {
        try {
            // Truy vấn cơ sở dữ liệu để kiểm tra xem email đã tồn tại hay chưa
            // Sử dụng lớp DatabaseManager của bạn để thực hiện truy vấn
            return DatabaseManager.isEmailExists(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Kiểm tra xem phoneNumber có phải là số và có độ dài là 10 hay không
        return phoneNumber.matches("\\d{10}");
    }

    private String hashPassword(String password) {
        // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu (có thể sử dụng thuật toán băm như SHA-256)
        // Đây chỉ là ví dụ, bạn nên sử dụng thư viện mã hóa mật khẩu thực tế
        return password;
    }
    public void handleRegisterSuccess() {
        if (employeeAccountGUI != null) {
            employeeAccountGUI.refreshTable();
        }
    }

    public static void registerStaffGUI(EmployeeAccountGUI employeeAccountGUI) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RegisterStaffGUI registerStaffGUIFrame = new RegisterStaffGUI(employeeAccountGUI);
                registerStaffGUIFrame.setVisible(true);
            }
        });
    }
}
