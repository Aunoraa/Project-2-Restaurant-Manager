package UserAuth;

import java.sql.*;


public class Register {
    // Phương thức để thêm người dùng mới là admin
    public static void registerAdmin() {
        String url = "jdbc:mysql://localhost:3306/restaurant";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Lệnh chèn dữ liệu vào bảng accounts
            String insertAccountSQL = "INSERT INTO accounts (AccountID, RoleID, Username, Password) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStatement = conn.prepareStatement(insertAccountSQL);

            // Tạo tham số cho tài khoản mới
            insertStatement.setInt(1, 1); // Giả sử AccountID là 1 (lấy số tiếp theo)
            insertStatement.setInt(2, 1); // Giả sử RoleID là 1 (là admin)
            insertStatement.setString(3, "admin"); // Tên người dùng (username)
            insertStatement.setString(4, "123456"); // Mật khẩu (password)

            // Thực hiện lệnh INSERT để thêm tài khoản mới
            int rowsAffected = insertStatement.executeUpdate();

            // Kiểm tra xem liệu việc thêm đã thành công hay chưa
            if (rowsAffected > 0) {
                System.out.println("Tài khoản admin đã được tạo thành công.");
            } else {
                System.out.println("Không thể tạo tài khoản admin.");
            }

            // Đóng tài nguyên
            insertStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức để thêm người dùng mới là staff
    public static void registerUser(String username, String phoneNumber, String email, String password) {
        String url = "jdbc:mysql://localhost:3306/restaurant";
        String dbUsername = "root";
        String dbPassword = "";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            // Lệnh chèn dữ liệu vào bảng Accounts
            String insertAccountSQL = "INSERT INTO Accounts (AccountID, RoleID, Username, PhoneNumber, Email, Password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertAccountStatement = conn.prepareStatement(insertAccountSQL);

            // Tạo tham số cho tài khoản mới
            insertAccountStatement.setInt(1, 2); // Giả sử AccountID là 2 (lấy số tiếp theo)
            insertAccountStatement.setInt(2, 2); // Giả sử RoleID là 2 (là staff)
            insertAccountStatement.setString(3, username); // Tên người dùng (Username)
            insertAccountStatement.setString(4, phoneNumber); // Số điện thoại (PhoneNumber)
            insertAccountStatement.setString(5, email); // Địa chỉ email (Email)
            insertAccountStatement.setString(6, password); // Mật khẩu (Password)

            // Thực hiện lệnh INSERT để thêm tài khoản mới vào bảng Accounts
            int rowsAffected = insertAccountStatement.executeUpdate();

            // Kiểm tra xem liệu việc thêm đã thành công hay chưa
            if (rowsAffected > 0) {
                System.out.println("Tài khoản đã được tạo thành công.");
            } else {
                System.out.println("Không thể tạo tài khoản.");
            }

            // Đóng tài nguyên
            insertAccountStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
