package Menu;
import Model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentController {
    private String url = "jdbc:mysql://localhost:3306/restaurant";
    private String username = "root";
    private String password = "";

    public boolean addPayment(int orderID, Date paymentDate, double totalAmount) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Payment (OrderID, PaymentDate, TotalAmount) VALUES (?, ?, ?)")) {

            preparedStatement.setInt(1, orderID);
            preparedStatement.setDate(2, new java.sql.Date(paymentDate.getTime()));
            preparedStatement.setDouble(3, totalAmount);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm thanh toán: " + e.getMessage());
            return false;
        }
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Payment")) {

            while (resultSet.next()) {
                int paymentID = resultSet.getInt("PaymentID");
                int orderID = resultSet.getInt("OrderID");
                Date paymentDate = resultSet.getDate("PaymentDate");
                double totalAmount = resultSet.getDouble("TotalAmount");

                Payment payment = new Payment(paymentID, orderID, paymentDate, totalAmount);
                payments.add(payment);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn thanh toán: " + e.getMessage());
        }

        return payments;
    }

    public boolean addPayment(int orderID, double totalAmount) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Payment (OrderID, PaymentDate, TotalAmount) VALUES (?, ?, ?)")) {

            Date paymentDate = new Date();

            preparedStatement.setInt(1, orderID);
            preparedStatement.setDate(2, new java.sql.Date(paymentDate.getTime()));
            preparedStatement.setDouble(3, totalAmount);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm thanh toán: " + e.getMessage());
            return false;
        }
    }

}
