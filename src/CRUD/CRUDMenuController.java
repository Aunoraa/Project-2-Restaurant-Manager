package CRUD;

import Model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDMenuController {
    private String url = "jdbc:mysql://localhost:3306/restaurant";
    private String username = "root";
    private String password = "";

    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT Menu.ItemID, Menu.ItemName, Category.CategoryName, Menu.Price FROM Menu " +
                    "INNER JOIN Category ON Menu.CategoryID = Category.CategoryID";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int itemID = resultSet.getInt("ItemID");
                String itemName = resultSet.getString("ItemName");
                String category = resultSet.getString("CategoryName");
                double price = resultSet.getDouble("Price");

                MenuItem menuItem = new MenuItem(itemID, itemName, category, price);
                menuItems.add(menuItem);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
        }

        return menuItems;
    }

    public String[] getAllCategoryNames() {
        List<String> categoryNames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT CategoryName FROM Category";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String categoryName = resultSet.getString("CategoryName");
                categoryNames.add(categoryName);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
        }

        return categoryNames.toArray(new String[0]);
    }

    public void addMenuItem(MenuItem menuItem) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Menu (ItemName, CategoryID, Price) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, menuItem.getItemName());
            preparedStatement.setInt(2, getCategoryIDByName(menuItem.getCategory()));
            preparedStatement.setDouble(3, menuItem.getPrice());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedID = generatedKeys.getInt(1);
                    menuItem.setItemID(generatedID);
                    System.out.println("Món ăn đã được thêm thành công vào cơ sở dữ liệu. ID: " + generatedID);
                } else {
                    System.out.println("Không thể lấy ID của món ăn từ cơ sở dữ liệu.");
                }
            } else {
                System.out.println("Không thể thêm món ăn vào cơ sở dữ liệu.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm món ăn vào cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void updateMenuItem(MenuItem menuItem) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Menu SET ItemName = ?, CategoryID = ?, Price = ? WHERE ItemID = ?")) {

            preparedStatement.setString(1, menuItem.getItemName());
            preparedStatement.setInt(2, getCategoryIDByName(menuItem.getCategory()));
            preparedStatement.setDouble(3, menuItem.getPrice());
            preparedStatement.setInt(4, menuItem.getItemID());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Thông tin món ăn đã được cập nhật thành công.");
            } else {
                System.out.println("Không thể cập nhật thông tin món ăn.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật thông tin món ăn: " + e.getMessage());
        }
    }

    public void deleteMenuItem(int itemID) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Menu WHERE ItemID = ?")) {

            preparedStatement.setInt(1, itemID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Món ăn đã được xóa khỏi cơ sở dữ liệu.");
            } else {
                System.out.println("Không thể xóa món ăn khỏi cơ sở dữ liệu.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa món ăn khỏi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    private int getCategoryIDByName(String categoryName) {
        int categoryID = 0;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT CategoryID FROM Category WHERE CategoryName = ?")) {

            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                categoryID = resultSet.getInt("CategoryID");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
        }

        return categoryID;
    }
}
