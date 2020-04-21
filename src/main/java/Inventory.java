import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

public class Inventory {
    private HashMap<String, ArrayList<Ingredient>> availableInventory;

    Inventory(){
        availableInventory = new HashMap<>();
    }

    public void addIngredient(Ingredient i){
        String name = i.getName();
        if (availableInventory.containsKey(name)) {
            boolean add = true;
            for (Ingredient ing:availableInventory.get(name)) {
                if (ing.getUnit().equals(i.getUnit())) {
                    ing.setAmount(ing.getAmount()+i.getAmount());
                    add = false;
                    break;
                }
            }
            if (add) availableInventory.get(i.getName()).add(i);
        } else {
            ArrayList ings = new ArrayList();
            ings.add(i);
            availableInventory.put(i.getName(), ings);
        }
    }

    public boolean haveIngredient(Ingredient i){
        if(availableInventory.containsKey(i.getName())) {
            for (Ingredient ing:availableInventory.get(i.getName())) {
                if (ing.equals(i)) return true;
            }
        }
        return false;
    }

    public String toString() {
        String output = "";

        //sort list - ingredients alphabetical
        ArrayList<String> names = new ArrayList();
        for (String key:availableInventory.keySet()) {
            names.add(key);
        }
        Collections.sort(names);

        for (String n:names) {
            for (Ingredient ing : availableInventory.get(n))
                output += ing.toString() + "\n";
        }
        return output;
    }


    public void removeIngredient(String name, double amount, String unit) {
        if (!availableInventory.containsKey(name)) throw new IllegalArgumentException("Food not in inventory");
        if (!validIngredient(name, amount, unit)) throw new IllegalArgumentException("Amount and/or unit not in inventory");

        Ingredient toRemove = null;
        for (Ingredient i:availableInventory.get(name)) {
            if (i.getUnit().equals(unit)) {
                i.setAmount(i.getAmount()-amount);
                if (i.getAmount() == 0) {
                    toRemove = i;
                }
                break;
            }
        }
        if (toRemove != null) {
            availableInventory.get(name).remove(toRemove);
        }
    }

    public boolean validIngredient (String name, double amount, String unit) {
        if (availableInventory.containsKey(name)) {
            for (Ingredient i:availableInventory.get(name)) {
                if (i.getUnit().equals(unit)) {
                    if (i.getAmount() >= amount) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }




}
