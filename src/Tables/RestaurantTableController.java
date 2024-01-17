package Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantTableController {
    private String url = "jdbc:mysql://localhost:3306/restaurant";
    private String username = "root";
    private String password = "";

    public List<RestaurantTableModel> getAllTables() {
        List<RestaurantTableModel> tables = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT TableID, TableNumber, Capacity, Status FROM Tables";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int tableID = resultSet.getInt("TableID");
                int tableNumber = resultSet.getInt("TableNumber");
                int capacity = resultSet.getInt("Capacity");
                String status = resultSet.getString("Status");

                RestaurantTableModel table = new RestaurantTableModel(tableID, tableNumber, capacity, status);
                tables.add(table);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
        }

        return tables;
    }

    public void updateTableStatus(int tableID, String newStatus) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Tables SET Status = ? WHERE TableID = ?")) {

            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, tableID);

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
