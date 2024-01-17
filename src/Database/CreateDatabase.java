package Database;

import java.sql.*;

public class CreateDatabase {
    public static void createDatabase() {

        String url = "jdbc:mysql://localhost:3306/";
        String databaseName = "restaurant";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            // Kiểm tra xem cơ sở dữ liệu đã tồn tại hay chưa
            ResultSet resultSet = connection.getMetaData().getCatalogs();
            boolean databaseExists = false;
            while (resultSet.next()) {
                String dbName = resultSet.getString(1);
                if (dbName.equalsIgnoreCase(databaseName)) {
                    databaseExists = true;
                    break;
                }
            }
            resultSet.close();

            if (databaseExists) {
                System.out.println("Cơ sở dữ liệu đã tồn tại.");
            } else {
                // Tạo database
                String createDatabaseSQL = "CREATE DATABASE " + databaseName;
                statement.executeUpdate(createDatabaseSQL);
                System.out.println("Cơ sở dữ liệu đã được tạo thành công.");
            }

            // Sử dụng cơ sở dữ liệu "restaurant"
            String useDatabaseSQL = "USE " + databaseName;
            statement.executeUpdate(useDatabaseSQL);

            // Câu lệnh SQL để tạo các bảng
            String createRoleTable = "CREATE TABLE Role (RoleID INT PRIMARY KEY, name VARCHAR(50) NOT NULL)";
            String createAccountsTable = "CREATE TABLE Accounts (AccountID INT PRIMARY KEY AUTO_INCREMENT, RoleID INT , Username VARCHAR(50) NOT NULL, PhoneNumber VARCHAR(20), Email VARCHAR(100), Password VARCHAR(60) NOT NULL , FOREIGN KEY (RoleID) REFERENCES Role(RoleID))";
            String createTablesTable = "CREATE TABLE Tables (TableID INT AUTO_INCREMENT PRIMARY KEY, TableNumber INT UNIQUE NOT NULL, Capacity INT NOT NULL, Status ENUM('Trống', 'Đã đặt', 'Đang sử dụng') NOT NULL DEFAULT 'Trống')";
            String createCategoryTable = "CREATE TABLE Category (CategoryID INT PRIMARY KEY, CategoryName VARCHAR(50) NOT NULL)";
            String createMenuTable = "CREATE TABLE Menu (ItemID INT PRIMARY KEY AUTO_INCREMENT, ItemName VARCHAR(100) NOT NULL, CategoryID INT, Price DECIMAL(10, 2) NOT NULL, FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID))";
            String createOrdersTable = "CREATE TABLE Orders (OrderID INT AUTO_INCREMENT PRIMARY KEY, TableID INT, OrderDate DATE, FOREIGN KEY (TableID) REFERENCES Tables(TableID))";
            String createOrderItemsTable = "CREATE TABLE OrderItems (OrderItemID INT AUTO_INCREMENT PRIMARY KEY, OrderID INT, ItemID INT, Quantity INT NOT NULL, Subtotal DECIMAL(10, 2) NOT NULL, FOREIGN KEY (OrderID) REFERENCES Orders(OrderID), FOREIGN KEY (ItemID) REFERENCES Menu(ItemID))";
            String createPaymentTable = "CREATE TABLE Payment (PaymentID INT AUTO_INCREMENT PRIMARY KEY, OrderID INT, PaymentDate DATE, TotalAmount DECIMAL(10, 2) NOT NULL, FOREIGN KEY (OrderID) REFERENCES Orders(OrderID))";
            String createStaffTable = "CREATE TABLE Staff (StaffID INT PRIMARY KEY AUTO_INCREMENT, FullName VARCHAR(100) NOT NULL, PhoneNumber VARCHAR(20), Email VARCHAR(100), Address VARCHAR(255), Position VARCHAR(50), Salary DECIMAL(10, 2), StartDate DATE)";
            String createStatisticsTable = "CREATE TABLE Statistics (StatID INT PRIMARY KEY, Date DATE, TotalOrders INT NOT NULL, TotalRevenue DECIMAL(10, 2) NOT NULL, TotalProfit DECIMAL(10, 2) NOT NULL, OrderID INT, PaymentID INT, FOREIGN KEY (OrderID) REFERENCES Orders(OrderID), FOREIGN KEY (PaymentID) REFERENCES Payment(PaymentID))";

            // Thực thi câu lệnh SQL để tạo các bảng
            statement.executeUpdate(createRoleTable);
            statement.executeUpdate(createAccountsTable);
            statement.executeUpdate(createTablesTable);
            statement.executeUpdate(createCategoryTable);
            statement.executeUpdate(createMenuTable);
            statement.executeUpdate(createOrdersTable);
            statement.executeUpdate(createOrderItemsTable);
            statement.executeUpdate(createPaymentTable);
            statement.executeUpdate(createStaffTable);
            statement.executeUpdate(createStatisticsTable);

            System.out.println("Các bảng đã được tạo thành công.");

            // Insert dữ liệu vào bảng Role
            String insertRoleSQL = "INSERT INTO Role (RoleID, name) VALUES (?, ?)";
            PreparedStatement insertRoleStatement = connection.prepareStatement(insertRoleSQL);

            // Insert dữ liệu cho admin
            insertRoleStatement.setInt(1, 1);
            insertRoleStatement.setString(2, "admin");
            insertRoleStatement.executeUpdate();

            // Insert dữ liệu cho staff
            insertRoleStatement.setInt(1, 2);
            insertRoleStatement.setString(2, "staff");
            insertRoleStatement.executeUpdate();

            System.out.println("Dữ liệu đã được chèn vào bảng Role.");

            insertRoleStatement.close();


            // Insert dữ liệu vào bảng Category
            String insertCategorySQL = "INSERT INTO Category (CategoryID, CategoryName) VALUES (?, ?)";
            PreparedStatement insertCategoryStatement = connection.prepareStatement(insertCategorySQL);

            // Insert dữ liệu cho Foods
            insertCategoryStatement.setInt(1, 1);
            insertCategoryStatement.setString(2, "Foods");
            insertCategoryStatement.executeUpdate();

            // Insert dữ liệu cho Drinks
            insertCategoryStatement.setInt(1, 2);
            insertCategoryStatement.setString(2, "Drinks");
            insertCategoryStatement.executeUpdate();

            // Insert dữ liệu cho Desserts
            insertCategoryStatement.setInt(1, 3);
            insertCategoryStatement.setString(2, "Desserts");
            insertCategoryStatement.executeUpdate();

            System.out.println("Dữ liệu đã được chèn vào bảng Category.");

            insertCategoryStatement.close();

        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo cơ sở dữ liệu: " + e.getMessage());
        }
    }
}
