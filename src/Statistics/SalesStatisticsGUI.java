package Statistics;

import AdminHome.Menu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SalesStatisticsGUI {

    public static void salesStatisticsGUI() {
        SwingUtilities.invokeLater(() -> {
            // Create and configure GUI components
            JFrame frame = new JFrame("Sales Statistics");

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5, true));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(70, 50, 50, 50));
            mainPanel.setBackground(new Color(193, 205, 205));

            JLabel titleLabel = new JLabel("BẢNG THỐNG KÊ");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
            titleLabel.setForeground(new Color(132, 112, 255));
            titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            mainPanel.add(titleLabel, BorderLayout.NORTH);

            // Create a table model
            DefaultTableModel tableModel = new DefaultTableModel();

            // Add columns to the table model
            tableModel.addColumn("Ngày");
            tableModel.addColumn("Doanh thu");

            // Get data from the controller
            List<String> revenueList = SalesStatisticsController.getTotalRevenueByDay();

            // Populate the table model with data
            for (String revenueInfo : revenueList) {
                String[] rowData = revenueInfo.split(":");
                tableModel.addRow(rowData);
            }

            // Create a JTable using the table model
            JTable table = new JTable(tableModel);

            // Add the JTable to a scroll pane
            JScrollPane scrollPane = new JScrollPane(table);
            mainPanel.add(scrollPane, BorderLayout.CENTER);

            JButton cancelButton = new JButton("Cancel");
            cancelButton.setFocusPainted(false);
            cancelButton.setBackground(new Color(132, 112, 255));
            cancelButton.setForeground(Color.WHITE);
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    Menu.MenuHomeGUI();
                }
            });
            mainPanel.add(cancelButton,BorderLayout.SOUTH);


            frame.add(mainPanel);

            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

    }
}
