package Menu;

import java.sql.*;

public class OrderItemsController {
    private String url = "jdbc:mysql://localhost:3306/restaurant";
    private String username = "root";
    private String password = "";

    public boolean addOrderItem(int orderID, int itemID, int quantity, double subtotal) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO OrderItems (OrderID, ItemID, Quantity, Subtotal) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setInt(1, orderID);
            preparedStatement.setInt(2, itemID);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, subtotal);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm món ăn vào đơn hàng: " + e.getMessage());
            return false;
        }
    }
}
