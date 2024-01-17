package Menu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuController {
    private String url = "jdbc:mysql://localhost:3306/restaurant";
    private String username = "root";
    private String password = "";

    public List<MenuItem> getMenuItemsByCategory(String categoryName) {
        List<MenuItem> menuItems = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT m.ItemID, m.ItemName, m.Price, c.CategoryID " +
                             "FROM Menu m " +
                             "INNER JOIN Category c ON m.CategoryID = c.CategoryID " +
                             "WHERE c.CategoryName = ?")) {

            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int itemID = resultSet.getInt("ItemID");
                String itemName = resultSet.getString("ItemName");
                double price = resultSet.getDouble("Price");
                int categoryID = resultSet.getInt("CategoryID");

                MenuItem menuItem = new MenuItem(itemID, itemName, price, categoryID);
                menuItems.add(menuItem);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
        }

        return menuItems;
    }

    public void updateTableStatus(int tableNumber, String newStatus) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE Tables SET Status = ? WHERE TableNumber = ?")) {

            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, tableNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Trạng thái bàn đã được cập nhật thành công.");
            } else {
                System.out.println("Không thể cập nhật trạng thái bàn.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật trạng thái bàn: " + e.getMessage());
        }
    }
}
