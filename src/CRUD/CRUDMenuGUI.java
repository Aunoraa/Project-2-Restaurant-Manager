package CRUD;

import Model.MenuItem;
import AdminHome.Menu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CRUDMenuGUI extends JFrame {
    private CRUDMenuController controller;
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> categoryComboBox;

    public CRUDMenuGUI() {
        controller = new CRUDMenuController();

        setTitle("Menu Management");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE,5,true));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(70, 50, 50, 50));
        mainPanel.setBackground(new Color(193, 205, 205));
        JLabel titleLabel = new JLabel("QUẢN LÝ THỰC ĐƠN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(132, 112, 255));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        mainPanel.add(titleLabel,BorderLayout.NORTH);
        add(mainPanel);



        tableModel = new DefaultTableModel();
        menuTable = new JTable(tableModel);
        tableModel.addColumn("ItemID");
        tableModel.addColumn("ItemName");
        tableModel.addColumn("Category");
        tableModel.addColumn("Price");

        menuTable.getColumnModel().getColumn(3).setCellRenderer(new CurrencyTableCellRenderer());

        refreshTable();

        JScrollPane scrollPane = new JScrollPane(menuTable);
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
                addMenuItem();
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
                updateMenuItem();
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
                deleteMenuItem();
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
        List<MenuItem> menuItems = controller.getAllMenuItems();
        for (MenuItem item : menuItems) {
            Object[] rowData = {item.getItemID(), item.getItemName(), item.getCategory(), item.getPrice()};
            tableModel.addRow(rowData);
        }
    }

    private boolean isValidPrice(String priceStr) {
        try {
            double price = Double.parseDouble(priceStr);
            return price >= 0; // Giá trị phải lớn hơn hoặc bằng 0
        } catch (NumberFormatException e) {
            return false; // Không phải là số hợp lệ
        }
    }

    private void addMenuItem() {
        JPanel inputPanel = createInputPanel("Add New Item");

        while (true) {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    inputPanel,
                    "Add New Item",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                JTextField itemNameField = (JTextField) inputPanel.getComponent(1);
                JComboBox<String> categoryComboBox = (JComboBox<String>) inputPanel.getComponent(3);
                JTextField priceField = (JTextField) inputPanel.getComponent(5);

                String itemName = itemNameField.getText();
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                String priceStr = priceField.getText();

                if (!itemName.isEmpty()) {
                    if (isValidPrice(priceStr)) {
                        try {
                            double price = Double.parseDouble(priceStr);
                            MenuItem newItem = new MenuItem(0, itemName, selectedCategory, price);
                            controller.addMenuItem(newItem);
                            refreshTable();
                            break; // Thoát khỏi vòng lặp khi thông tin hợp lệ
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Giá trị không hợp lệ.Vui lòng nhập lại.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Giá trị không hợp lệ.Vui lòng nhập lại.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                }
            } else {
                break; // Thoát khỏi vòng lặp nếu người dùng nhấn Cancel
            }
        }
    }

    private void updateMenuItem() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow >= 0) {
            MenuItem selectedItem = new MenuItem(
                    (int) menuTable.getValueAt(selectedRow, 0),
                    (String) menuTable.getValueAt(selectedRow, 1),
                    (String) menuTable.getValueAt(selectedRow, 2),
                    (double) menuTable.getValueAt(selectedRow, 3)
            );
            JPanel inputPanel = createInputPanel("Update Item");
            populateInputPanel(selectedItem, inputPanel);

            while (true) {
                int result = JOptionPane.showConfirmDialog(
                        this,
                        inputPanel,
                        "Update Item",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (result == JOptionPane.OK_OPTION) {
                    JTextField itemNameField = (JTextField) inputPanel.getComponent(1);
                    JComboBox<String> categoryComboBox = (JComboBox<String>) inputPanel.getComponent(3);
                    JTextField priceField = (JTextField) inputPanel.getComponent(5);

                    String newName = itemNameField.getText();
                    String newCategory = (String) categoryComboBox.getSelectedItem();
                    String newPriceStr = priceField.getText();

                    if (!newName.isEmpty()) {
                        if (isValidPrice(newPriceStr)) {
                            try {
                                double newPrice = Double.parseDouble(newPriceStr);
                                MenuItem updatedItem = new MenuItem(selectedItem.getItemID(), newName, newCategory, newPrice);
                                controller.updateMenuItem(updatedItem);
                                refreshTable();
                                break; // Thoát khỏi vòng lặp khi thông tin hợp lệ
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this, "Giá trị không hợp lệ.Vui lòng nhập lại.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Giá trị không hợp lệ.Vui lòng nhập lại.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                    }
                } else {
                    break; // Thoát khỏi vòng lặp nếu người dùng nhấn Cancel
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a menu item to update.");
        }
    }


    private void deleteMenuItem() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow >= 0) {
            int itemID = (int) menuTable.getValueAt(selectedRow, 0);
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this menu item?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (option == JOptionPane.YES_OPTION) {
                controller.deleteMenuItem(itemID);
                refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a menu item to delete.");
        }
    }

    private JPanel createInputPanel(String title) {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Item Name:"));
        JTextField itemNameField = new JTextField();
        inputPanel.add(itemNameField);
        inputPanel.add(new JLabel("Category:"));
        JComboBox<String> categoryComboBox = new JComboBox<>(controller.getAllCategoryNames());
        inputPanel.add(categoryComboBox);
        inputPanel.add(new JLabel("Price:"));
        JTextField priceField = new JTextField();
        inputPanel.add(priceField);
        return inputPanel;
    }

    private void populateInputPanel(MenuItem item, JPanel inputPanel) {
        JTextField itemNameField = (JTextField) inputPanel.getComponent(1);
        JComboBox<String> categoryComboBox = (JComboBox<String>) inputPanel.getComponent(3);
        JTextField priceField = (JTextField) inputPanel.getComponent(5);

        itemNameField.setText(item.getItemName());
        categoryComboBox.setSelectedItem(item.getCategory());
        priceField.setText(String.valueOf(item.getPrice()));
    }

    public static void crudMenuGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CRUDMenuGUI gui = new CRUDMenuGUI();
                gui.setLocationRelativeTo(null);
                gui.setVisible(true);
            }
        });
    }
}
