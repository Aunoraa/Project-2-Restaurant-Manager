package Database;

import java.sql.*;

public class ConnectDatabase {
    public static void connectDatabase(){
        // Các thông số
        String url = "jdbc:mysql://localhost:3306/restaurant";
        String username = "root";
        String password = "";
        try {
            // Tạo kết nối
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Kết nối thành công!");

            conn.close();
            System.out.println("Đã đóng kết nối!");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}





