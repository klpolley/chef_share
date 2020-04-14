import java.util.*;

public class shoppingList {
    private int length;
    ArrayList <Ingredient> shoppingList;

    public shoppingList(){
        shoppingList = new ArrayList<Ingredient>();
        length = 0;
    }

    public int getLength(){
        return length;
    }

    public void addIngredient(Ingredient ingredientIn){
        length++;
        shoppingList.add(ingredientIn);
    }

    public String getIngredientName(int index){
        return shoppingList.get(index).getName();
    }

    public int getIngredientIndex(String name){
        int index = -1;

        for(int i = 0; i<length; i++){
            if (name.equals(shoppingList.get(i).getName())){
                index = i;
            }
        }

        return index;
    }

    public void removeIngredient(String ingredientNameIn) throws IllegalArgumentException{

        int index = getIngredientIndex(ingredientNameIn);

        if (index == -1){
            throw new IllegalArgumentException("Ingredient is not present in list");
        }else {

            shoppingList.remove(index);
            length--;
        }

    }

    public String printList() {
        String str = "";
        for (Ingredient i: shoppingList) {
            str += i.getAmount() + i.getUnit() + " " + i.getName() + "\n";
        }
        return str;
    }
}
