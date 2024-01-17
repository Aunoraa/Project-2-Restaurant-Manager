package Tables;

public class RestaurantTableModel {
    private int tableID;
    private int tableNumber;
    private int capacity;
    private String status;

    public RestaurantTableModel(int tableID, int tableNumber, int capacity, String status) {
        this.tableID = tableID;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
    }

    public int getTableID() {
        return tableID;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
