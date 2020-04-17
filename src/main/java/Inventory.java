import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private HashMap<String, Ingredient> availableInventory;

    Inventory(){
        availableInventory = new HashMap<String, Ingredient>();
    }

    public void addIngredient(Ingredient i){
        availableInventory.put(i.getName(), i);
    }

    public boolean haveIngredient(Ingredient i){
        if(availableInventory.containsKey(i.getName())) {
            return availableInventory.get(i.getName()).equals(i);
        }
        else return false;
    }

    public String toString(){
        String output = "";
        for (Map.Entry<String, Ingredient> e : availableInventory.entrySet()) {
                output += e.getValue().toString() + "\n";
        }
        return output;
    }
}
