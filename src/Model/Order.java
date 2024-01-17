package Model;

import java.util.Date;

public class Order {
    private int orderID;
    private int tableID;
    private Date orderDate;

    public Order(int orderID, int tableID, Date orderDate) {
        this.orderID = orderID;
        this.tableID = tableID;
        this.orderDate = orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getTableID() {
        return tableID;
    }

    public Date getOrderDate() {
        return orderDate;
    }
}