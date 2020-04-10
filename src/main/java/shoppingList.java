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
}
