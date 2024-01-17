package Model;

public class OrderItem {
    private int orderItemID;
    private int orderID;
    private int itemID;
    private int quantity;
    private double subtotal;

    public OrderItem(int orderItemID, int orderID, int itemID, int quantity, double subtotal) {
        this.orderItemID = orderItemID;
        this.orderID = orderID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public int getOrderItemID() {
        return orderItemID;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getItemID() {
        return itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }
}
