package Menu;

import Invoice.InvoiceGUI;
import Invoice.Invoice;
import Tables.RestaurantTableGUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuGUI extends JFrame {
    private RestaurantMenuController restaurantMenuController;
    private JTextArea orderArea;
    private JLabel totalLabel;
    private double totalAmount;
    private List<MenuItem> cartItems;
    private int tableNumber;
    private JFrame tableFrame;
    private JFrame parentFrame; // Thêm parentFrame để lưu giao diện cha
    private OrderController orderController;
    private OrderItemsController orderItemsController;
    private PaymentController paymentController;

    public RestaurantMenuGUI(RestaurantMenuController restaurantMenuController, int tableNumber, JFrame tableFrame, JFrame parentFrame) {
        this.restaurantMenuController = restaurantMenuController;
        this.totalAmount = 0.0;
        this.cartItems = new ArrayList<>();
        this.tableNumber = tableNumber;
        this.tableFrame = tableFrame;
        this.parentFrame = parentFrame; // Lưu giao diện cha
        this.orderController = new OrderController();
        this.orderItemsController = new OrderItemsController();
        this.paymentController = new PaymentController();

        setTitle("Restaurant Menu - Table " + tableNumber);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(132, 112, 255));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 50, 50));

        JButton cancelButton = new JButton("↩");
        cancelButton.setBackground(new Color(21, 8, 100));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(50, 50));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cập nhật trạng thái bàn và màu nền trong giao diện cha (RestaurantTableGUI)
                restaurantMenuController.updateTableStatus(tableNumber, "Trống");
                if (parentFrame instanceof RestaurantTableGUI) {
                    RestaurantTableGUI tableGUI = (RestaurantTableGUI) parentFrame;
                    tableGUI.updateButtonBackground(tableNumber, "Trống");
                }

                dispose();
                tableFrame.setVisible(true);
            }
        });
        headerPanel.add(cancelButton, BorderLayout.WEST);

        JLabel titleHeaderLabel = new JLabel("MENU - Table " + tableNumber);
        titleHeaderLabel.setForeground(Color.WHITE);
        titleHeaderLabel.setFont(new Font("Arial", Font.BOLD, 35));
        titleHeaderLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        headerPanel.add(titleHeaderLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(1, 3, 30, 30));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        menuPanel.setBackground(new Color(255, 255, 240));

            List<MenuItem> foodItems = restaurantMenuController.getMenuItemsByCategory("Foods");
            List<MenuItem> drinkItems = restaurantMenuController.getMenuItemsByCategory("Drinks");
            List<MenuItem> dessertItems = restaurantMenuController.getMenuItemsByCategory("Desserts");

        JPanel foodPanel = createMenuPanel(foodItems, "Foods", 20, 16);
        JPanel drinkPanel = createMenuPanel(drinkItems, "Drinks", 20, 16);
        JPanel dessertPanel = createMenuPanel(dessertItems, "Desserts", 20, 16);

        changeLabelTextColor(foodPanel, new Color(132, 112, 255));
        changeLabelTextColor(drinkPanel, new Color(132, 112, 255));
        changeLabelTextColor(dessertPanel, new Color(132, 112, 255));

        menuPanel.add(foodPanel);
        menuPanel.add(drinkPanel);
        menuPanel.add(dessertPanel);

        add(menuPanel, BorderLayout.CENTER);

        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        orderArea = new JTextArea(15, 30);
        orderArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderArea);
        scrollPane.setBackground(Color.WHITE);
        // Tạo border với padding 10px
        Border paddingBorder = BorderFactory.createEmptyBorder(5, 10, 5, 0);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(scrollPane.getBorder(), paddingBorder));

        orderPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel totalAndPaymentPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalAndPaymentPanel.add(totalLabel);

        JButton paymentButton = new JButton("Thanh Toán");
        paymentButton.setPreferredSize(new Dimension(150, 40));
        paymentButton.setBackground(new Color(132, 112, 255));
        paymentButton.setForeground(Color.WHITE);
        paymentButton.setFocusPainted(false);
        paymentButton.setFont(new Font("Arial", Font.BOLD, 15));
        totalAndPaymentPanel.add(paymentButton);

        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cartItems.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Giỏ hàng trống. Vui lòng thêm ít nhất một món hàng vào giỏ trước khi thanh toán.");
                    return; // Dừng xử lý tiếp
                }

                int orderID = orderController.createOrderForTable(tableNumber); // Tạo đơn hàng và lấy ID

                if (orderID != -1) {
                    for (MenuItem item : cartItems) {
                        orderItemsController.addOrderItem(orderID, item.getItemID(), 1, item.getPrice()); // Thêm các món ăn vào đơn hàng
                    }

                    double totalAmount = calculateTotalAmount();
                    paymentController.addPayment(orderID, totalAmount); // Thêm thanh toán vào cơ sở dữ liệu

                    JOptionPane.showMessageDialog(null, "Đã thanh toán thành công!");

                    // Cập nhật trạng thái bàn và đóng lại trang menu
                    restaurantMenuController.updateTableStatus(tableNumber, "Trống");
                    if (parentFrame instanceof RestaurantTableGUI) {
                        RestaurantTableGUI tableGUI = (RestaurantTableGUI) parentFrame;
                        tableGUI.updateButtonBackground(tableNumber, "Trống");
                    }
                    dispose(); // Đóng giao diện menu

                    // Tạo đối tượng Invoice và hiển thị giao diện hóa đơn
                    Invoice invoice = new Invoice(cartItems, totalAmount);
                    showInvoice(invoice);
                } else {
                    JOptionPane.showMessageDialog(null, "Lỗi khi tạo đơn hàng!");
                }
            }
        });

        orderPanel.add(totalAndPaymentPanel, BorderLayout.SOUTH);

        add(orderPanel, BorderLayout.EAST);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(132, 112, 255));
        JLabel footerLabel = new JLabel("Nhớ Cảm Ơn Khách");
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }


    private JPanel createMenuPanel(List<MenuItem> items, String category, int headerFontSize, int itemFontSize) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 240));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel(category);
        headerLabel.setFont(new Font("Arial", Font.BOLD, headerFontSize));
        panel.add(headerLabel);

        for (MenuItem item : items) {
            JPanel itemPanel = new JPanel(new BorderLayout());

            itemPanel.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));

            JLabel nameLabel = new JLabel(item.getItemName());
            nameLabel.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
            infoPanel.add(nameLabel);

            JLabel priceLabel = new JLabel( " $ " + item.getPrice());
            priceLabel.setFont(new Font("Arial", Font.PLAIN, itemFontSize));
            infoPanel.add(priceLabel);
            Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
            itemPanel.setBorder(bottomBorder);

//            infoPanel

            itemPanel.add(infoPanel, BorderLayout.WEST);
            JPanel addButtonPanel = new JPanel();
            JButton addButton = new JButton("+");
            addButton.setBackground(new Color(132, 112, 255));
            addButton.setForeground(Color.WHITE);
            addButton.setFocusPainted(false);
            addButton.setPreferredSize(new Dimension(50, 50));
            addButtonPanel.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 10));
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addToCart(item);
                }
            });


            JButton removeButton = new JButton("-");
            removeButton.setBackground(new Color(132, 112, 255));
            removeButton.setForeground(Color.WHITE);
            removeButton.setFocusPainted(false);
            removeButton.setPreferredSize(new Dimension(50, 50));
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeFromCart(item);
                }
            });
            addButtonPanel.add(addButton,BorderLayout.WEST);
            addButtonPanel.add(removeButton,BorderLayout.EAST);
            itemPanel.add(addButtonPanel, BorderLayout.EAST);

            panel.add(itemPanel);
        }

        return panel;
    }



    private void addToCart(MenuItem item) {
        cartItems.add(item);
        totalAmount += item.getPrice();
        totalLabel.setText("Total: $" + String.format("%.2f", totalAmount));
        updateOrderArea();
    }

    private void removeFromCart(MenuItem item) {
        if (totalAmount > 0) {
            cartItems.remove(item);
            totalAmount -= item.getPrice();
            totalLabel.setText("Total: $" + String.format("%.2f", totalAmount));
            updateOrderArea();
        }
    }


    private void updateOrderArea() {
        orderArea.setText("");
        for (MenuItem item : cartItems) {
            orderArea.append(item.getItemName() + " - $" + item.getPrice() + "\n");
        }
    }

    private double calculateTotalAmount() {
        double total = 0.0;
        for (MenuItem item : cartItems) {
            total += item.getPrice();
        }
        return total;
    }

    private void showInvoice(Invoice invoice) {
        // Tạo giao diện hóa đơn mới và truyền đối tượng Invoice
        InvoiceGUI invoiceGUI = new InvoiceGUI(invoice);
        invoiceGUI.setVisible(true);
        // Thực hiện in hóa đơn tự động sau khi giao diện hóa đơn đã hiển thị
        invoiceGUI.printInvoice();
    }

    private void changeLabelTextColor(JPanel panel, Color color) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setForeground(color);
            }
        }
    }


    public static void restaurantMenuGUI(RestaurantMenuController restaurantMenuController, int tableNumber, JFrame tableFrame, JFrame parentFrame) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RestaurantMenuGUI gui = new RestaurantMenuGUI(restaurantMenuController, tableNumber, tableFrame, parentFrame);
                gui.setVisible(true);
            }
        });
    }
}
