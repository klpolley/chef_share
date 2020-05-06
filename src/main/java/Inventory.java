import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

public class Inventory {
    private HashMap<String, Ingredient> availableInventory;

    Inventory(){
        availableInventory = new HashMap<>();
    }

    public void addIngredient(Ingredient i){
        String name = i.getName();
        if(!availableInventory.containsKey(i.getName())){
            availableInventory.put(i.getName(), i);
        }
        else{
            Ingredient temp = availableInventory.get(i.getName());
            temp.setAmount(temp.getAmount() + shoppingList.unitConversion(temp.getUnit(), i));
            availableInventory.put(i.getName(), temp);
        }
    }

    public boolean haveIngredient(Ingredient i){
        if(!availableInventory.containsKey(i.getName())) return false;
        return !(availableInventory.get(i.getName()).getAmount() < shoppingList.unitConversion(availableInventory.get(i.getName()).getUnit(), i));
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
            output += availableInventory.get(n).toString() + "\n";
        }
        return output;
    }


    public void removeIngredient(String name, double amount, String unit) {
        if (!availableInventory.containsKey(name)) throw new IllegalArgumentException("Food not in inventory");
        double have = shoppingList.unitConversion(unit, availableInventory.get(name));
        if(have < amount)throw new IllegalArgumentException("not enough food");
        Ingredient edit = availableInventory.get(name);
        edit.setAmount(edit.getAmount() - shoppingList.unitConversion(edit.getUnit(), new Ingredient(edit.getFood(), amount, unit)));
        availableInventory.put(name, edit);
        if(!(availableInventory.get(name).getAmount() > 0)) availableInventory.remove(name);
    }


    public Ingredient getIngredient(String name) throws IllegalArgumentException{
        if(!availableInventory.containsKey(name))throw new IllegalArgumentException("No Ingredient With name: " + name);
        return availableInventory.get(name);
    }
    public Ingredient getIngredient(String name, String unit) throws IllegalArgumentException{
        if(!availableInventory.containsKey(name))throw new IllegalArgumentException("No Ingredient With name: " + name);
        return new Ingredient(availableInventory.get(name).getFood(), shoppingList.unitConversion(unit, availableInventory.get(name)), unit);
    }

    public boolean validIngredient (String name, double amount, String unit) {
        if(!availableInventory.containsKey(name)) return false;
        double have = shoppingList.unitConversion(unit, availableInventory.get(name));
        if(!(have < amount))return true;
        return false;
    }




}
