package UserAuth;


import CRUD.CRUDTableGUI;
import Tables.RestaurantTableGUI;
import AdminHome.Menu;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class LoginController implements ActionListener {

    private LoginGUI loginGUIFrame;

    public LoginController(LoginGUI loginGUIFrame) {
        this.loginGUIFrame = loginGUIFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if the username and password are valid
        String username = loginGUIFrame.usernameField.getText();
        String password = String.valueOf(loginGUIFrame.passwordField.getPassword());

        // Validate that both fields are not empty
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginGUIFrame, "Vui lòng nhập đầy đủ thông tin.");
            return; // Exit the action listener
        }

        // Kết nối database và xác thực đăng nhập
        int roleID = authenticateUser(username, password);
        if (roleID != -1) {
            // Login successful

            // Đóng cửa sổ đăng nhập
            loginGUIFrame.dispose();

            // Phân quyền người dùng và mở giao diện tương ứng
            switch (roleID) {
                case 1: // Admin
                    Menu.MenuHomeGUI();
                    break;
                case 2: // Staff
                    RestaurantTableGUI.restaurantTableGUI();
                    break;
            }
        } else {
            // Login failed
            JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu không chính xác!");
        }
    }

    // Hàm xác thực người dùng và trả về roleID nếu đúng, ngược lại trả về -1 nếu sai
    private int authenticateUser(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/restaurant";
        String dbUsername = "root";
        String dbPassword = "";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statement = conn.prepareStatement("SELECT RoleID FROM accounts WHERE Username = ? AND Password = ?")) {

            // Set parameters
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if the result set has any rows (i.e., if the user exists)
            if (resultSet.next()) {
                return resultSet.getInt("RoleID");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }
}
