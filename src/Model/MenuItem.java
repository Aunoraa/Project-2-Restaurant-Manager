package Model;

public class MenuItem {
    private int itemID;
    private String itemName;
    private String category;
    private double price;

    public MenuItem(int itemID, String itemName, String category, double price) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.category = category;
        this.price = price;
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
}
