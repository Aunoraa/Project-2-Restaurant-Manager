package CRUD;

import Model.EmployeeAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeAccountModel {

    // Phương thức này lấy danh sách tài khoản từ bảng Accounts
    public static List<EmployeeAccount> getAccounts() {
        String url = "jdbc:mysql://localhost:3306/restaurant";
        String dbUsername = "root";
        String dbPassword = "";

        String selectAccountsSQL = "SELECT AccountID, Username, PhoneNumber, Email FROM Accounts";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectAccountsSQL)) {

            List<EmployeeAccount> accounts = new ArrayList<>();

            while (rs.next()) {
                int accountId = rs.getInt("AccountID");
                String username = rs.getString("Username");
                String phoneNumber = rs.getString("PhoneNumber");
                String email = rs.getString("Email");

                EmployeeAccount account = new EmployeeAccount(accountId, username, phoneNumber, email);
                accounts.add(account);
            }

            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteAccount(int accountID) {
        String url = "jdbc:mysql://localhost:3306/restaurant";
        String dbUsername = "root";
        String dbPassword = "";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String deleteAccountSQL = "DELETE FROM Accounts WHERE AccountID = ?";
            PreparedStatement deleteAccountStatement = conn.prepareStatement(deleteAccountSQL);

            deleteAccountStatement.setInt(1, accountID);

            int rowsAffected = deleteAccountStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Tài khoản đã được xóa thành công.");
            } else {
                System.out.println("Không thể xóa tài khoản.");
            }

            deleteAccountStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}