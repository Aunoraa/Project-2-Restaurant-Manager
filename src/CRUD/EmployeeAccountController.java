package CRUD;

import Model.EmployeeAccount;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EmployeeAccountController {

    public static DefaultTableModel getAccountsTableModel() {
        List<EmployeeAccount> accounts = EmployeeAccountModel.getAccounts();

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Account ID");
        tableModel.addColumn("Username");
        tableModel.addColumn("Phone Number");
        tableModel.addColumn("Email");

        for (EmployeeAccount account : accounts) {
            Object[] rowData = new Object[]{
                    account.getAccountId(),
                    account.getUsername(),
                    account.getPhoneNumber(),
                    account.getEmail()
            };
            tableModel.addRow(rowData);
        }

        return tableModel;
    }

    public static void resetAccountPassword(int accountID, String newPassword) {
        String url = "jdbc:mysql://localhost:3306/restaurant";
        String dbUsername = "root";
        String dbPassword = "";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String updatePasswordSQL = "UPDATE Accounts SET Password = ? WHERE AccountID = ?";
            PreparedStatement updatePasswordStatement = conn.prepareStatement(updatePasswordSQL);

            updatePasswordStatement.setString(1, newPassword); // Không cần hash ở đây
            updatePasswordStatement.setInt(2, accountID);

            int rowsAffected = updatePasswordStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Mật khẩu đã được reset thành công.");
            } else {
                System.out.println("Không thể reset mật khẩu.");
            }

            updatePasswordStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            // Chuyển đổi byte array thành chuỗi hex để lưu vào cơ sở dữ liệu
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = String.format("%02x", b); // Đúng cách để biểu diễn mỗi byte
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteAccount(int accountID) {
        EmployeeAccountModel.deleteAccount(accountID);
    }
}