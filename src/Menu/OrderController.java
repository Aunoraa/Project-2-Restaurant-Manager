package Menu;

import java.sql.*;
import java.util.Date;

public class OrderController {
    private String url = "jdbc:mysql://localhost:3306/restaurant";
    private String username = "root";
    private String password = "";

    public boolean createOrder(int tableID) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Orders (TableID, OrderDate) VALUES (?, ?)")) {

            Date orderDate = new Date(); // Get current date and time

            preparedStatement.setInt(1, tableID);
            preparedStatement.setDate(2, new java.sql.Date(orderDate.getTime()));

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo đơn hàng: " + e.getMessage());
            return false;
        }
    }

    public int createOrderForTable(int tableID) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Orders (TableID, OrderDate) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            Date orderDate = new Date(); // Get current date and time

            preparedStatement.setInt(1, tableID);
            preparedStatement.setDate(2, new java.sql.Date(orderDate.getTime()));

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }

            return -1;

        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo đơn hàng: " + e.getMessage());
            return -1;
        }
    }
}
