public class Item {
    private double price;
    private int quantity;

    public Item(double price, int quantity){
        if (price < 0 || quantity < 0) {
            throw new IllegalArgumentException("Price and quantity must be non-negative.");
        }
        this.price = price;
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalCost() {
        return price * quantity;
    }
}
