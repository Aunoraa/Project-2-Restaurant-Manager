package CRUD;

import UserAuth.RegisterStaffGUI;
import AdminHome.Menu;

import Model.EmployeeAccount;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeAccountGUI extends JFrame {

    private JTable accountsTable;
    private JScrollPane scrollPane;
    private JButton addAccountButton;
    private JButton resetPasswordButton;
    private JButton deleteAccountButton;


    public EmployeeAccountGUI() {
        setTitle("Employee Account Management");

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE,5,true));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 20, 50));
        mainPanel.setBackground(new Color(193, 205, 205));

        JLabel titleLabel = new JLabel("TÀI KHOẢN NHÂN VIÊN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(132, 112, 255));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        mainPanel.add(titleLabel,BorderLayout.NORTH);



        DefaultTableModel tableModel = EmployeeAccountController.getAccountsTableModel();
        accountsTable = new JTable(tableModel);
        scrollPane = new JScrollPane(accountsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        addAccountButton = new JButton("Add new account");
        addAccountButton.setFocusPainted(false);
        addAccountButton.setBackground(new Color(132, 112, 255));
        addAccountButton.setForeground(Color.WHITE);



        resetPasswordButton = new JButton("Reset password account");
        resetPasswordButton.setFocusPainted(false);
        resetPasswordButton.setBackground(new Color(132, 112, 255));
        resetPasswordButton.setForeground(Color.WHITE);



        deleteAccountButton = new JButton("Delete account");
        deleteAccountButton.setFocusPainted(false);
        deleteAccountButton.setBackground(new Color(132, 112, 255));
        deleteAccountButton.setForeground(Color.WHITE);



        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(193 ,205 ,205));
        buttonPanel.add(addAccountButton);
        buttonPanel.add(resetPasswordButton);
        buttonPanel.add(deleteAccountButton);
        mainPanel.add(buttonPanel,BorderLayout.SOUTH);


        getContentPane().add(mainPanel);


        addAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddAccountButtonClick();
            }
        });

        resetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleResetPasswordButtonClick();
            }
        });

        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteAccountButtonClick();
            }
        });

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

        pack();
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon("resources/icon.png").getImage());
    }



    private void handleAddAccountButtonClick() {
        RegisterStaffGUI.registerStaffGUI(this);
    }

    private void handleResetPasswordButtonClick() {
        int selectedRow = accountsTable.getSelectedRow();
        if (selectedRow >= 0) {
            int accountID = (int) accountsTable.getValueAt(selectedRow, 0);

            boolean validPassword = false;
            while (!validPassword) {
                String newPassword = JOptionPane.showInputDialog(this, "Nhập mật khẩu mới:", "Reset Password", JOptionPane.PLAIN_MESSAGE);

                if (newPassword == null) {
                    JOptionPane.showMessageDialog(this, "Hủy thay đổi mật khẩu.");
                    break;
                } else if (newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu mới không được bỏ trống.");
                } else if (newPassword.length() < 6) {
                    JOptionPane.showMessageDialog(this, "Mật khẩu mới phải chứa ít nhất 6 ký tự.");
                } else {
                    EmployeeAccountController.resetAccountPassword(accountID, newPassword);
                    refreshTable();
                    validPassword = true;
                    JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản để reset mật khẩu.");
        }
    }

    private void handleDeleteAccountButtonClick() {
        int selectedRow = accountsTable.getSelectedRow();
        if (selectedRow >= 0) {
            int accountID = (int) accountsTable.getValueAt(selectedRow, 0);
            EmployeeAccountController.deleteAccount(accountID);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an account to delete.");
        }
    }

    public void refreshTable() {
        DefaultTableModel tableModel = EmployeeAccountController.getAccountsTableModel();
        accountsTable.setModel(tableModel);
    }

    public static void showEmployeeAccountGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EmployeeAccountGUI employeeAccountGUI = new EmployeeAccountGUI();
                employeeAccountGUI.setVisible(true);
            }
        });
    }
}
