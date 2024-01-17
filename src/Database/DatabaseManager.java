package Database;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/restaurant";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    // Phương thức để kiểm tra xem username đã tồn tại hay chưa


    public static boolean isUsernameExists(String username) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT COUNT(*) FROM Accounts WHERE Username = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        }
        return false;
    }

    // Phương thức để kiểm tra xem email đã tồn tại hay chưa
    public static boolean isEmailExists(String email) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT COUNT(*) FROM Accounts WHERE Email = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        }
        return false;
    }

    // Phương thức để thêm thông tin tài khoản mới vào bảng Accounts
    public static void insertAccount(String username, String phoneNumber, String email, String password) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertSQL = "INSERT INTO Accounts (RoleID, Username, PhoneNumber, Email, Password) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                // Tạo tham số cho tài khoản mới
                int roleId = 2; // Giả sử RoleID là 2 (là staff)

                statement.setInt(1, roleId);
                statement.setString(2, username); // Tên người dùng (Username)
                statement.setString(3, phoneNumber); // Số điện thoại (PhoneNumber)
                statement.setString(4, email); // Địa chỉ email (Email)
                statement.setString(5, password); // Mật khẩu (Password)

                // Thực hiện lệnh INSERT để thêm tài khoản mới vào bảng Accounts
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    // Lấy giá trị được tự động tăng của trường AccountID (nếu được hỗ trợ bởi cơ sở dữ liệu)
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int accountId = generatedKeys.getInt(1);
                            System.out.println("Tài khoản đã được tạo thành công với AccountID = " + accountId);
                        }
                    }
                }
            }
        }
    }
}
