package CRUD;

import Model.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDTableController {
    private String url = "jdbc:mysql://localhost:3306/restaurant";
    private String username = "root";
    private String password = "";

    public List<Table> getAllTables() {
        List<Table> tables = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT TableNumber, Capacity, Status FROM Tables";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int tableNumber = resultSet.getInt("TableNumber");
                int capacity = resultSet.getInt("Capacity");
                String status = resultSet.getString("Status");

                Table table = new Table(tableNumber, capacity, status);
                tables.add(table);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
        }

        return tables;
    }

    public void addTable(Table table) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Tables (TableNumber, Capacity, Status) VALUES (?, ?, ?)")) {

            preparedStatement.setInt(1, table.getTableNumber());
            preparedStatement.setInt(2, table.getCapacity());
            preparedStatement.setString(3, table.getStatus());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Bàn ăn đã được thêm thành công vào cơ sở dữ liệu.");
            } else {
                System.out.println("Không thể thêm bàn ăn vào cơ sở dữ liệu.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm bàn ăn vào cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void updateTable(Table table) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Tables SET Capacity = ?, Status = ? WHERE TableNumber = ?")) {

            preparedStatement.setInt(1, table.getCapacity());
            preparedStatement.setString(2, table.getStatus());
            preparedStatement.setInt(3, table.getTableNumber());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Thông tin bàn ăn đã được cập nhật thành công.");
            } else {
                System.out.println("Không thể cập nhật thông tin bàn ăn.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật thông tin bàn ăn: " + e.getMessage());
        }
    }

    public boolean isTableNumberExists(int tableNumber) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Tables WHERE TableNumber = ?")) {

            preparedStatement.setInt(1, tableNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
        }

        return false;
    }

    public void deleteTable(int tableNumber) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Tables WHERE TableNumber = ?")) {

            preparedStatement.setInt(1, tableNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Bàn ăn đã được xóa khỏi cơ sở dữ liệu.");
            } else {
                System.out.println("Không thể xóa bàn ăn khỏi cơ sở dữ liệu.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa bàn ăn khỏi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    private int getStatusIDByName(String statusName) {
        int statusID = 0;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT StatusID FROM TableStatus WHERE StatusName = ?")) {

            preparedStatement.setString(1, statusName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                statusID = resultSet.getInt("StatusID");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
        }

        return statusID;
    }
}
