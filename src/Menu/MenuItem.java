package Menu;

public class MenuItem {
    private int itemID;
    private String itemName;
    private double price;
    private int categoryID;

    public MenuItem(int itemID, String itemName, double price, int categoryID) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.price = price;
        this.categoryID = categoryID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
