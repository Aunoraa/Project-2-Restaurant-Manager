package Invoice;

import Menu.MenuItem;
import Tables.RestaurantTableGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.Date;


public class InvoiceGUI extends JFrame {
    private JTextArea invoiceArea;
    private JPanel mainPanel;

    public InvoiceGUI(Invoice invoice) {
        setTitle("Hóa đơn");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel restaurantLabel = new JLabel("Nhà hàng 5 sao");
        restaurantLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 10, 50));
        restaurantLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(restaurantLabel, BorderLayout.NORTH);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        JLabel dateLabel = new JLabel("Ngày: " + dateFormat.format(new Date()));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(dateLabel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Invoice Items
        invoiceArea = new JTextArea();
        invoiceArea.setEditable(false);

        for (MenuItem item : invoice.getItems()) {
            invoiceArea.append(item.getItemName() + "\t$" + String.format("%.2f", item.getPrice()) + "\n");
        }

        invoiceArea.append("\n---------------------------------\n");
        invoiceArea.append("Tổng tiền:\t$" + String.format("%.2f", invoice.getTotalAmount()));

        JScrollPane scrollPane = new JScrollPane(invoiceArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(Color.BLACK, 1, true)
        ));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new BorderLayout());
        JLabel thankYouLabel = new JLabel("Cảm ơn quý khách!");
        thankYouLabel.setFont(new Font("Arial", Font.BOLD, 18));
        thankYouLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerPanel.add(thankYouLabel, BorderLayout.NORTH);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private BufferedImage createImageFromPanel(JPanel panel) {
        BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        panel.print(graphics2D);
        graphics2D.dispose();
        return image;
    }

    public void printInvoice() {
        try {
            BufferedImage image = createImageFromPanel(mainPanel);
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintable(new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
                    if (pageIndex != 0) {
                        return NO_SUCH_PAGE;
                    }

                    graphics.drawImage(image, 0, 0, mainPanel.getWidth(), mainPanel.getHeight(), null);
                    return PAGE_EXISTS;
                }
            });

            boolean doPrint = printerJob.printDialog();
            if (doPrint) {
                printerJob.print();
                dispose(); // Đóng cửa sổ hóa đơn sau khi in
                RestaurantTableGUI.restaurantTableGUI();
            } else {
                JOptionPane.showMessageDialog(this, "Không in hóa đơn.");
                dispose();
                RestaurantTableGUI.restaurantTableGUI();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi in hóa đơn: " + ex.getMessage());
        }
    }
}

