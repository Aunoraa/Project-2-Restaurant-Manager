package CRUD;

import Model.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CRUDStaffController {
    private String url = "jdbc:mysql://localhost:3306/restaurant";
    private String username = "root";
    private String password = "";

    // Phương thức để lấy danh sách nhân viên từ cơ sở dữ liệu
    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM Staff";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int staffID = resultSet.getInt("StaffID");
                String fullName = resultSet.getString("FullName");
                String phoneNumber = resultSet.getString("PhoneNumber");
                String email = resultSet.getString("Email");
                String address = resultSet.getString("Address");
                String position = resultSet.getString("Position");
                double salary = resultSet.getDouble("Salary");
                Date startDate = resultSet.getDate("StartDate");

                Staff staff = new Staff(staffID, fullName, phoneNumber, email, address, position, salary, startDate);
                staffList.add(staff);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
        }

        return staffList;
    }

    // Phương thức để thêm một nhân viên mới vào cơ sở dữ liệu
    public void addStaff(Staff staff) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Staff (FullName, PhoneNumber, Email, Address, Position, Salary, StartDate) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, staff.getFullName());
            preparedStatement.setString(2, staff.getPhoneNumber());
            preparedStatement.setString(3, staff.getEmail());
            preparedStatement.setString(4, staff.getAddress());
            preparedStatement.setString(5, staff.getPosition());
            preparedStatement.setDouble(6, staff.getSalary());
            preparedStatement.setDate(7, new java.sql.Date(staff.getStartDate().getTime()));

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Lấy id tự động tăng của nhân viên vừa được thêm vào
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedID = generatedKeys.getInt(1);
                    staff.setStaffID(generatedID);
                    System.out.println("Nhân viên đã được thêm thành công vào cơ sở dữ liệu. ID: " + generatedID);
                } else {
                    System.out.println("Không thể lấy ID của nhân viên từ cơ sở dữ liệu.");
                }
            } else {
                System.out.println("Không thể thêm nhân viên vào cơ sở dữ liệu.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nhân viên vào cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public Staff getStaffByID(int staffID) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Staff WHERE StaffID = ?")) {

            preparedStatement.setInt(1, staffID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String fullName = resultSet.getString("FullName");
                String phoneNumber = resultSet.getString("PhoneNumber");
                String email = resultSet.getString("Email");
                String address = resultSet.getString("Address");
                String position = resultSet.getString("Position");
                double salary = resultSet.getDouble("Salary");
                Date startDate = resultSet.getDate("StartDate");

                return new Staff(staffID, fullName, phoneNumber, email, address, position, salary, startDate);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
        }

        return null;
    }

    // Phương thức để cập nhật thông tin một nhân viên trong cơ sở dữ liệu
    public void updateStaff(Staff staff) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Staff SET FullName = ?, PhoneNumber = ?, Email = ?, Address = ?, Position = ?, Salary = ?, StartDate = ? WHERE StaffID = ?")) {

            preparedStatement.setString(1, staff.getFullName());
            preparedStatement.setString(2, staff.getPhoneNumber());
            preparedStatement.setString(3, staff.getEmail());
            preparedStatement.setString(4, staff.getAddress());
            preparedStatement.setString(5, staff.getPosition());
            preparedStatement.setDouble(6, staff.getSalary());
            preparedStatement.setDate(7, new java.sql.Date(staff.getStartDate().getTime()));
            preparedStatement.setInt(8, staff.getStaffID());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Thông tin nhân viên đã được cập nhật thành công.");
            } else {
                System.out.println("Không thể cập nhật thông tin nhân viên.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật thông tin nhân viên: " + e.getMessage());
        }
    }

    // Phương thức để xóa một nhân viên khỏi cơ sở dữ liệu
    public void deleteStaff(int staffID) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Staff WHERE StaffID = ?")) {

            preparedStatement.setInt(1, staffID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Nhân viên đã được xóa khỏi cơ sở dữ liệu.");
            } else {
                System.out.println("Không thể xóa nhân viên khỏi cơ sở dữ liệu.");
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa nhân viên khỏi cơ sở dữ liệu: " + e.getMessage());
        }
    }

}
