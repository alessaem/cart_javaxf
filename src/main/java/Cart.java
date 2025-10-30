import java.util.ArrayList;
import java.util.List;

public class Cart {
    private double totalCost = 0;
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
        totalCost += item.getTotalCost();
    }

    public double getTotalCost(){
        return totalCost;
    }
}
