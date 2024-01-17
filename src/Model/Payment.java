package Model;

import java.util.Date;

public class Payment {
    private int paymentID;
    private int orderID;
    private Date paymentDate;
    private double totalAmount;

    public Payment(int paymentID, int orderID, Date paymentDate, double totalAmount) {
        this.paymentID = paymentID;
        this.orderID = orderID;
        this.paymentDate = paymentDate;
        this.totalAmount = totalAmount;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public int getOrderID() {
        return orderID;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}