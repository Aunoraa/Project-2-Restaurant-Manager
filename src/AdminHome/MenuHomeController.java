package AdminHome;

import CRUD.CRUDMenuGUI;
import CRUD.CRUDStaffGUI;
import CRUD.CRUDTableGUI;
import CRUD.EmployeeAccountGUI;
import Statistics.SalesStatisticsGUI;

import javax.swing.*;

public class MenuHomeController {
    private static JFrame Menu; // Biến static để lưu tham chiếu tới giao diện cũ (Menu)

    public static void handleButton1Click() {
        // Xử lý khi button 1 được click
        System.out.println("Button 1 clicked.");
        // Mở trang CRUDMenu
        CRUDMenuGUI.crudMenuGUI();
    }

    public static void handleButton2Click() {
        // Xử lý khi button 2 được click
        System.out.println("Button 2 clicked.");
        // Mở trang CRUDStaff
        CRUDStaffGUI.crudStaffGUI();
    }

    public static void handleButton4Click() {
        // Xử lý khi button 4 được click
        System.out.println("Button 4 clicked.");
        // Mở trang SalesStatisticsGUI
        SalesStatisticsGUI.salesStatisticsGUI();
    }

    public static void handleButton5Click() {
        // Xử lý khi button 5 được click
        System.out.println("Button 5 clicked.");
        // Mở trang EmployeeAccountGUI
        EmployeeAccountGUI.showEmployeeAccountGUI();
    }

    public static void handleButton6Click() {
        // Xử lý khi button 6 được click
        System.out.println("Button 6 clicked.");
        // Mở trang CRUDTable
        CRUDTableGUI.crudTableGUI();
    }

    // Phương thức này được gọi từ Menu để lưu tham chiếu tới giao diện cũ
    public static void setMenu(JFrame frame) {
        Menu = frame;
    }
}