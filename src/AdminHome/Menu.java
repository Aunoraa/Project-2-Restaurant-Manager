package AdminHome;

import CRUD.CRUDTableGUI;
import CRUD.EmployeeAccountGUI;
import UserAuth.LoginGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private JPanel panel1;
    private JButton button2;
    private JButton button1;
    private JButton button4;
    private JButton button3;
    private JButton button5;
    private JButton button6;

    public Menu() {
        // Thêm các hành động vào các nút
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuHomeController.handleButton1Click();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuHomeController.handleButton2Click();
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExitButtonClick();
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuHomeController.handleButton4Click();
            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuHomeController.handleButton5Click();
            }
        });
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuHomeController.handleButton6Click();
            }
        });
    }

    public void handleExitButtonClick() {
        int option = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn thoát?",
                "Xác nhận thoát",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            dispose();
            LoginGUI.loginGUI(); // Mở cửa sổ Login
        }

        // Gọi phương thức setMenuFrame để cung cấp tham chiếu tới giao diện cũ (Menu)
        MenuHomeController.setMenu(this);
    }

    public static void MenuHomeGUI() {
        Menu menu = new Menu();
        menu.setTitle("Menu");
        menu.setSize(600, 400);
        menu.setContentPane(menu.panel1);

        // Đặt cửa sổ ở chính giữa màn hình
        menu.setLocationRelativeTo(null);

        menu.setVisible(true);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}