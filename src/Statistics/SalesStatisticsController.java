package Statistics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

public class SalesStatisticsController {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/restaurant";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "";

    public static List<String> getTotalRevenueByDay() {
        List<String> revenueList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            Statement statement = connection.createStatement();

            // Get the total revenue for each day
            ResultSet resultSet = statement.executeQuery("SELECT PaymentDate, SUM(TotalAmount) AS TotalRevenue FROM payment GROUP BY PaymentDate");

            while (resultSet.next()) {
                // Process the data and add to the list
                java.sql.Date paymentDate = resultSet.getDate("PaymentDate");
                int totalRevenue = resultSet.getInt("TotalRevenue");

                // Format the date in the "dd/MM/yyyy" format
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = dateFormat.format(paymentDate);

                // Create the revenue information string
                String revenueInfo = "Ngày " + formattedDate + ": " + totalRevenue + "$";
                revenueList.add(revenueInfo); // Thêm vào danh sách doanh thu
            }

            // Get the total revenue for each month
            resultSet = statement.executeQuery("SELECT MONTH(PaymentDate) AS Month, SUM(TotalAmount) AS TotalRevenue FROM payment GROUP BY MONTH(PaymentDate)");

            while (resultSet.next()) {
                // Process the data and add to the list
                int month = resultSet.getInt("Month");
                int totalRevenue = resultSet.getInt("TotalRevenue");

                // Create the revenue information string
                String revenueInfo = "Tổng Doanh Thu Trong Tháng " + month + " là: " + totalRevenue + "$";
                revenueList.add(revenueInfo); // Thêm vào danh sách doanh thu
            }

            // Get the total revenue for each year
            resultSet = statement.executeQuery("SELECT YEAR(PaymentDate) AS Year, SUM(TotalAmount) AS TotalRevenue FROM payment GROUP BY YEAR(PaymentDate)");

            while (resultSet.next()) {
                // Process the data and add to the list
                int year = resultSet.getInt("Year");
                int totalRevenue = resultSet.getInt("TotalRevenue");

                // Create the revenue information string
                String revenueInfo = "Tổng Doanh Thu Trong Năm " + year + " là: " + totalRevenue + "$";
                revenueList.add(revenueInfo); // Thêm vào danh sách doanh thu
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return revenueList;
    }
}
