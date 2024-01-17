package Invoice;

import Menu.MenuItem;

import java.util.List;

public class Invoice {
    private List<MenuItem> items;
    private double totalAmount;

    public Invoice(List<MenuItem> items, double totalAmount) {
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}