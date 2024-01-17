package CRUD;

import AdminHome.Menu;
import Model.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CRUDTableGUI extends JFrame {
    private CRUDTableController controller;
    private JTable tableTable;
    private DefaultTableModel tableModel;

    public CRUDTableGUI() {
        controller = new CRUDTableController();

        setTitle("Table Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE,5,true));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(70, 50, 50, 50));
        mainPanel.setBackground(new Color(193, 205, 205));

        JLabel titleLabel = new JLabel("QUẢN LÝ BÀN ĂN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(132, 112, 255));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        mainPanel.add(titleLabel,BorderLayout.NORTH);
        add(mainPanel);

        tableModel = new DefaultTableModel();
        tableTable = new JTable(tableModel);
        tableModel.addColumn("TableNumber");
        tableModel.addColumn("Capacity");
        tableModel.addColumn("Status");

        refreshTable();

        JScrollPane scrollPane = new JScrollPane(tableTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(193 ,205 ,205));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JButton addButton = new JButton("Add New");
        addButton.setFocusPainted(false);
        addButton.setBackground(new Color(132, 112, 255));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTable();
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
                updateTable();
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
                deleteTable();
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
        List<Table> tables = controller.getAllTables();
        for (Table table : tables) {
            Object[] rowData = {table.getTableNumber(), table.getCapacity(), table.getStatus()};
            tableModel.addRow(rowData);
        }
    }

    // Trong phần addTable():
    private void addTable() {
        JPanel inputPanel = createInputPanel("Add New Table");

        while (true) {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    inputPanel,
                    "Add New Table",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                JTextField tableNumberField = (JTextField) inputPanel.getComponent(1);
                JTextField capacityField = (JTextField) inputPanel.getComponent(3);
                JComboBox<String> statusComboBox = (JComboBox<String>) inputPanel.getComponent(5);

                String tableNumberText = tableNumberField.getText().trim();
                String capacityText = capacityField.getText().trim();

                if (tableNumberText.isEmpty() || capacityText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
                    continue; // Yêu cầu người dùng nhập lại
                }

                if (!isValidNumber(tableNumberText) || !isValidNumber(capacityText)) {
                    JOptionPane.showMessageDialog(this, "Số bàn và số chỗ ngồi phải là số nguyên!");
                    continue; // Yêu cầu người dùng nhập lại
                }

                int tableNumber = Integer.parseInt(tableNumberText);
                int capacity = Integer.parseInt(capacityText);
                String selectedStatus = (String) statusComboBox.getSelectedItem();

                if (controller.isTableNumberExists(tableNumber)) {
                    JOptionPane.showMessageDialog(this, "Số bàn đã tồn tại trong hệ thống. Vui lòng nhập số bàn khác!");
                    continue; // Yêu cầu người dùng nhập lại
                }

                Table newTable = new Table(tableNumber, capacity, selectedStatus);
                controller.addTable(newTable);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Thêm mới bàn ăn thành công!");
                break; // Kết thúc vòng lặp nếu dữ liệu hợp lệ
            } else {
                break; // Kết thúc vòng lặp nếu người dùng chọn Cancel
            }
        }
    }

    // Trong phần updateTable():
    private void updateTable() {
        int selectedRow = tableTable.getSelectedRow();
        if (selectedRow >= 0) {
            Table selectedTable = new Table(
                    (int) tableTable.getValueAt(selectedRow, 0),
                    (int) tableTable.getValueAt(selectedRow, 1),
                    (String) tableTable.getValueAt(selectedRow, 2)
            );
            JPanel inputPanel = createInputPanel("Update Table");
            populateInputPanel(selectedTable, inputPanel);

            while (true) {
                int result = JOptionPane.showConfirmDialog(
                        this,
                        inputPanel,
                        "Update Table",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (result == JOptionPane.OK_OPTION) {
                    JTextField tableNumberField = (JTextField) inputPanel.getComponent(1);
                    JTextField capacityField = (JTextField) inputPanel.getComponent(3);
                    JComboBox<String> statusComboBox = (JComboBox<String>) inputPanel.getComponent(5);

                    String newTableNumberText = tableNumberField.getText().trim();
                    String newCapacityText = capacityField.getText().trim();

                    if (newTableNumberText.isEmpty() || newCapacityText.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.");
                        continue; // Yêu cầu người dùng nhập lại
                    }

                    if (!isValidNumber(newTableNumberText) || !isValidNumber(newCapacityText)) {
                        JOptionPane.showMessageDialog(this, "Số bàn và số chỗ ngồi phải là số nguyên!");
                        continue; // Yêu cầu người dùng nhập lại
                    }

                    int newTableNumber = Integer.parseInt(newTableNumberText);
                    int newCapacity = Integer.parseInt(newCapacityText);
                    String newStatus = (String) statusComboBox.getSelectedItem();

                    if (newTableNumber != selectedTable.getTableNumber() && controller.isTableNumberExists(newTableNumber)) {
                        JOptionPane.showMessageDialog(this, "Số bàn đã tồn tại trong hệ thống. Vui lòng nhập số bàn khác!");
                        continue; // Yêu cầu người dùng nhập lại
                    }

                    Table updatedTable = new Table(newTableNumber, newCapacity, newStatus);
                    controller.updateTable(updatedTable);
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin bàn ăn thành công!");
                    break; // Kết thúc vòng lặp nếu dữ liệu hợp lệ
                } else {
                    break; // Kết thúc vòng lặp nếu người dùng chọn Cancel
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a table to update.");
        }
    }

    // Thêm hàm kiểm tra số hợp lệ:
    private boolean isValidNumber(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void deleteTable() {
        int selectedRow = tableTable.getSelectedRow();
        if (selectedRow >= 0) {
            int tableNumber = (int) tableTable.getValueAt(selectedRow, 0);
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this table?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (option == JOptionPane.YES_OPTION) {
                controller.deleteTable(tableNumber);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Xóa bàn ăn thành công!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a table to delete.");
        }
    }

    private JPanel createInputPanel(String title) {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Table Number:"));
        JTextField tableNumberField = new JTextField();
        inputPanel.add(tableNumberField);
        inputPanel.add(new JLabel("Capacity:"));
        JTextField capacityField = new JTextField();
        inputPanel.add(capacityField);
        inputPanel.add(new JLabel("Status:"));
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Trống", "Đã đặt", "Đang sử dụng"});
        inputPanel.add(statusComboBox);
        return inputPanel;
    }

    private void populateInputPanel(Table table, JPanel inputPanel) {
        JTextField tableNumberField = (JTextField) inputPanel.getComponent(1);
        JTextField capacityField = (JTextField) inputPanel.getComponent(3);
        JComboBox<String> statusComboBox = (JComboBox<String>) inputPanel.getComponent(5);

        tableNumberField.setText(String.valueOf(table.getTableNumber()));
        capacityField.setText(String.valueOf(table.getCapacity()));
        statusComboBox.setSelectedItem(table.getStatus());
    }

    public static void crudTableGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CRUDTableGUI gui = new CRUDTableGUI();
                gui.setLocationRelativeTo(null);
                gui.setVisible(true);
            }
        });
    }
}
