package Tables;

import Menu.RestaurantMenuController;
import Menu.RestaurantMenuGUI;
import UserAuth.LoginGUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RestaurantTableGUI extends JFrame {
    private RestaurantTableController tableController;
    private JFrame currentFrame;  // Store a reference to the current frame
    private JPanel mainPanel;

    public RestaurantTableGUI() {
        tableController = new RestaurantTableController();
        currentFrame = this;  // Initialize the current frame reference
        setTitle("Restaurant Table Management");
        setSize(1000, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        mainPanel = new JPanel(); // Khởi tạo biến mainPanel
        mainPanel.setBackground(new Color(206, 228, 205)); // Set background color
        add(mainPanel);





        // Tạo panel cho phần header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(254, 245, 237));
        headerPanel.setPreferredSize(new Dimension(1000, 50));
        JLabel headerLabel = new JLabel("Lựa chọn bàn");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);





        // Tạo panel cho phần chứa các bàn
        JPanel tablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 25));
        tablePanel.setBackground(new Color(173, 194, 169));
        tablePanel.setPreferredSize(new Dimension(600, 400));

        // Tạo viền bo góc vuông cho tablePanel
        Border roundedBorder = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), roundedBorder));



        JPanel groupTable = new JPanel();
        groupTable.setPreferredSize(new Dimension(500,300));
        groupTable.setBackground(new Color(173, 194, 169));
        groupTable.setLayout(new GridLayout(4, 3, 20, 20));
        List<RestaurantTableModel> tables = tableController.getAllTables();

        for (RestaurantTableModel table : tables) {
            JButton tableButton = new JButton("Table " + table.getTableNumber() + " (" + table.getCapacity() + " seats)");
            updateButtonBackground(tableButton, table.getStatus());

            tableButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (table.getStatus().equals("Trống")) {
                        tableController.updateTableStatus(table.getTableID(), "Đang sử dụng");
                        updateButtonBackground(tableButton, "Đang sử dụng");

                        // Open the menu GUI with the selected table number
                        openMenuGUI(table.getTableNumber());
                    } else if (table.getStatus().equals("Đang sử dụng")) {
                        // Handle the case when the table is already in use
                        JOptionPane.showMessageDialog(currentFrame, "Bàn đang được sử dụng.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });
            groupTable.add(tableButton);
        }
        // Tạo panel mới để bọc groupTable
        JPanel groupTablePanel = new JPanel();
        groupTablePanel.setPreferredSize(new Dimension(500, 300));
        groupTablePanel.setBackground(new Color(173, 194, 169));
        groupTablePanel.setLayout(new BorderLayout());

        // Đặt groupTable vào panel mới
        groupTablePanel.add(groupTable, BorderLayout.CENTER);
        // Tạo JScrollPane cho groupTablePanel
        JScrollPane groupTableScrollPane = new JScrollPane(groupTablePanel);
        groupTableScrollPane.setPreferredSize(new Dimension(500, 300));
        groupTableScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(groupTableScrollPane);
        mainPanel.add(tablePanel, BorderLayout.CENTER);



        // Tạo panel cho phần trạng thái và chú thích
        JPanel statusPanel = new JPanel(new GridBagLayout());
        statusPanel.setPreferredSize(new Dimension(200, 100));
        statusPanel.setBackground(new Color(173, 194, 169));
        // Tạo viền bo góc vuông cho tablePanel
        Border roundedBorderStatusPanel = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        statusPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), roundedBorderStatusPanel));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 8, 0); // Cách xa dưới 8px
        gbc.anchor = GridBagConstraints.WEST;

        // Tạo hình vuông đỏ (Đã đặt)
        JPanel reservedSquare = new JPanel();
        reservedSquare.setPreferredSize(new Dimension(20, 20));
        reservedSquare.setBackground(Color.RED);
        reservedSquare.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        statusPanel.add(reservedSquare, gbc);

        JLabel reservedLabel = new JLabel("Đang Sử Dụng");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 8, 8, 0);
        statusPanel.add(reservedLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(8, 0, 0, 0);

        JPanel availableSquare = new JPanel();
        availableSquare.setPreferredSize(new Dimension(20, 20));
        availableSquare.setBackground(Color.GREEN);
        availableSquare.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        statusPanel.add(availableSquare, gbc);
        JLabel availableLabel = new JLabel("Trống");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(8, 8, 0, 0); // Cách xa trên 8px và trái 8px
        statusPanel.add(availableLabel, gbc);

        mainPanel.add(statusPanel, BorderLayout.EAST);


        // Tạo panel cho phần footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(153, 167, 153));
        footerPanel.setPreferredSize(new Dimension(1000, 50));

        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Thêm nút đăng xuất
        JButton logoutButton = new JButton("Đăng xuất");
        logoutButton.setPreferredSize(new Dimension(100, 30));
        logoutButton.setFocusPainted(false);
        logoutButton.setBackground(new Color(132, 112, 255));
        logoutButton.setForeground(Color.WHITE);
        footerPanel.add(logoutButton);

        // Thêm xử lý sự kiện cho nút Đăng xuất
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmResult = JOptionPane.showConfirmDialog(
                        currentFrame,
                        "Bạn có chắc chắn muốn đăng xuất không?",
                        "Xác nhận đăng xuất",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmResult == JOptionPane.YES_OPTION) {
                    dispose();
                    LoginGUI.loginGUI();
                }
            }
        });


        getContentPane().add(mainPanel);

        setResizable(false);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void updateButtonBackground(JButton button, String status) {
        switch (status) {
            case "Trống":
                button.setBackground(Color.GREEN);
                break;
            case "Đang sử dụng":
                button.setBackground(Color.RED);
                break;
            default:
                button.setBackground(null);
        }
    }

    private void openMenuGUI(int tableNumber) {
        RestaurantMenuController restaurantMenuController = new RestaurantMenuController();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RestaurantMenuGUI gui = new RestaurantMenuGUI(restaurantMenuController, tableNumber, currentFrame, currentFrame);
                gui.setLocationRelativeTo(null);
                gui.setVisible(true);

                // Hide the current frame when opening the menu
                currentFrame.setVisible(false);
            }
        });
    }

    public void updateButtonBackground(int tableNumber, String status) {
        Component[] components = mainPanel.getComponents(); // Thay mainPanel bằng tên biến của container nút bàn
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton tableButton = (JButton) component;
                String buttonText = tableButton.getText();
                int currentTableNumber = Integer.parseInt(buttonText.substring(6)); // Lấy số bàn từ "Table X"

                if (currentTableNumber == tableNumber) {
                    switch (status) {
                        case "Trống":
                            tableButton.setBackground(Color.GREEN);
                            break;
                        case "Đang sử dụng":
                            tableButton.setBackground(Color.RED);
                            break;
                        default:
                            tableButton.setBackground(null);
                    }
                    break; // Đã tìm thấy nút bàn và cập nhật màu, dừng vòng lặp
                }
            }
        }
    }


    public static void restaurantTableGUI() {
        RestaurantTableGUI restaurantTableGUI = new RestaurantTableGUI();
        restaurantTableGUI.setVisible(true);

    }
}
