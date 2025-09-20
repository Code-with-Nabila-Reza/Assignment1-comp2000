import java.util.ArrayList;
import java.util.List;

public class Inventory<T extends Collectible >{

    private List<T> ItemsCollected;

    public Inventory(){
        ItemsCollected = new ArrayList<>();
    }
    
    public void addItem(T item){
        ItemsCollected.add(item);
    }

    public List<T> getItemList(){
        return new ArrayList<>(ItemsCollected);
    }

    public int getTotalPoints() {
        int total = 0;
        for (T item : ItemsCollected) {
            total += item.getPoints();
        }
        return total;
    }

    public int size() {
        return ItemsCollected.size();
    }
}

