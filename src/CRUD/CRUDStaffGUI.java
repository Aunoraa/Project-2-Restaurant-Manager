package CRUD;

import Model.Staff;
import AdminHome.Menu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CRUDStaffGUI extends JFrame {
    private CRUDStaffController controller;
    private JTable staffTable;
    private DefaultTableModel tableModel;
    private Staff lastEnteredStaff;

    public CRUDStaffGUI() {
        controller = new CRUDStaffController();

        setTitle("Staff Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5, true));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(70, 50, 50, 50));
        mainPanel.setBackground(new Color(193, 205, 205));

        JLabel titleLabel = new JLabel("QUẢN LÝ NHÂN VIÊN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(132, 112, 255));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        add(mainPanel);

        tableModel = new DefaultTableModel();
        staffTable = new JTable(tableModel);
        tableModel.addColumn("StaffID");
        tableModel.addColumn("FullName");
        tableModel.addColumn("PhoneNumber");
        tableModel.addColumn("Email");
        tableModel.addColumn("Address");
        tableModel.addColumn("Position");
        tableModel.addColumn("Salary");
        tableModel.addColumn("StartDate");

        refreshTable();

        JScrollPane scrollPane = new JScrollPane(staffTable);
        scrollPane.setBackground(new Color(240, 255, 255));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(193, 205, 205));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JButton addButton = new JButton("Add New");
        addButton.setFocusPainted(false);
        addButton.setBackground(new Color(132, 112, 255));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrUpdateStaff(null);
            }
        });
        buttonPanel.add(addButton);

        JButton updateButton = new JButton("Update");

        updateButton.setFocusPainted(false);
        updateButton.setBackground(new Color(132, 112, 255));
        updateButton.setForeground(Color.WHITE);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = staffTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int staffID = (int) staffTable.getValueAt(selectedRow, 0);
                    Staff selectedStaff = controller.getStaffByID(staffID);
                    if (selectedStaff != null) {
                        addOrUpdateStaff(selectedStaff);
                    } else {
                        JOptionPane.showMessageDialog(CRUDStaffGUI.this, "Cannot find staff with ID: " + staffID);
                    }
                } else {
                    JOptionPane.showMessageDialog(CRUDStaffGUI.this, "Please select a staff to update.");
                }
            }
        });
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete");

        deleteButton.setFocusPainted(false);
        deleteButton.setBackground(new Color(132, 112, 255));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = staffTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int staffID = (int) staffTable.getValueAt(selectedRow, 0);
                    int option = JOptionPane.showConfirmDialog(
                            CRUDStaffGUI.this,
                            "Are you sure you want to delete this staff?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (option == JOptionPane.YES_OPTION) {
                        controller.deleteStaff(staffID);
                        refreshTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(CRUDStaffGUI.this, "Please select a staff to delete.");
                }
            }
        });
        buttonPanel.add(deleteButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFocusPainted(false);
        cancelButton.setBackground(new Color(132, 112, 255));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Menu.MenuHomeGUI();
            }
        });
        buttonPanel.add(cancelButton);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Staff> staffList = controller.getAllStaff();
        for (Staff staff : staffList) {
            Object[] rowData = {staff.getStaffID(), staff.getFullName(), staff.getPhoneNumber(), staff.getEmail(), staff.getAddress(), staff.getPosition(), staff.getSalary(), staff.getStartDate()};
            tableModel.addRow(rowData);
        }
    }

    private void addOrUpdateStaff(Staff staff) {
        JPanel inputPanel = createInputPanel(staff); // Thay lastEnteredStaff bằng staff
        String title = (staff == null) ? "Add New Staff" : "Update Staff";
        int result = JOptionPane.showConfirmDialog(
                this,
                inputPanel,
                title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            JTextField fullNameField = (JTextField) inputPanel.getComponent(1);
            JTextField phoneNumberField = (JTextField) inputPanel.getComponent(3);
            JTextField emailField = (JTextField) inputPanel.getComponent(5);
            JTextField addressField = (JTextField) inputPanel.getComponent(7);
            JTextField positionField = (JTextField) inputPanel.getComponent(9);
            JTextField salaryField = (JTextField) inputPanel.getComponent(11);
            JTextField startDateField = (JTextField) inputPanel.getComponent(13);

            try {
                double salary = Double.parseDouble(salaryField.getText());
                java.sql.Date startDate = java.sql.Date.valueOf(startDateField.getText());

                Staff updatedStaff = new Staff(
                        staff != null ? staff.getStaffID() : 0,
                        fullNameField.getText(),
                        phoneNumberField.getText(),
                        emailField.getText(),
                        addressField.getText(),
                        positionField.getText(),
                        salary,
                        startDate
                );

                if (isValidData(updatedStaff)) {
                    if (updatedStaff.getStaffID() == 0) {
                        controller.addStaff(updatedStaff);
                    } else {
                        controller.updateStaff(updatedStaff);
                    }
                    refreshTable();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Định dạng lương không hợp lệ. Vui lòng nhập số hợp lệ.");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ. Vui lòng nhập ngày theo định dạng yyyy-MM-dd.");
            }
        }
    }

    private boolean isValidData(Staff staff) {
        if (staff.getFullName().isEmpty() || staff.getPhoneNumber().isEmpty() || staff.getEmail().isEmpty() || staff.getAddress().isEmpty() || staff.getPosition().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
            return false;
        } else if (!isValidPhoneNumber(staff.getPhoneNumber())) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ. Vui lòng nhập số điện thoại gồm 10 chữ số.");
            return false;
        } else if (!isValidEmail(staff.getEmail())) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ. Vui lòng nhập email đúng định dạng.");
            return false;
        }
        return true;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
    }

    private JPanel createInputPanel(Staff staff) {
        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        inputPanel.add(new JLabel("Full Name:"));
        JTextField fullNameField = new JTextField();
        inputPanel.add(fullNameField);
        inputPanel.add(new JLabel("Phone Number:"));
        JTextField phoneNumberField = new JTextField();
        inputPanel.add(phoneNumberField);
        inputPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Address:"));
        JTextField addressField = new JTextField();
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Position:"));
        JTextField positionField = new JTextField();
        inputPanel.add(positionField);
        inputPanel.add(new JLabel("Salary:"));
        JTextField salaryField = new JTextField();
        inputPanel.add(salaryField);
        inputPanel.add(new JLabel("Start Date (yyyy-MM-dd):"));
        JTextField startDateField = new JTextField();
        inputPanel.add(startDateField);

        if (staff != null) {
            fullNameField.setText(staff.getFullName());
            phoneNumberField.setText(staff.getPhoneNumber());
            emailField.setText(staff.getEmail());
            addressField.setText(staff.getAddress());
            positionField.setText(staff.getPosition());
            salaryField.setText(Double.toString(staff.getSalary()));
            startDateField.setText(staff.getStartDate().toString());
        }

        return inputPanel;
    }

    public static void crudStaffGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CRUDStaffGUI gui = new CRUDStaffGUI();
                gui.setLocationRelativeTo(null);
                gui.setVisible(true);
            }
        });
    }
}
