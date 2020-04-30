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

    public void addIngredient(Ingredient ingredientIn) {
        String ingredientName = ingredientIn.getName();
        int index = getIngredientIndex(ingredientName);

        if (index == -1) {
            length++;
            shoppingList.add(ingredientIn);
        }else{
            String unit = getUnit(index);
            if (unit.equals(ingredientIn.getUnit())){
                shoppingList.get(index).setAmount(shoppingList.get(index).getAmount()+ingredientIn.getAmount());
            }else{
                length++;
                shoppingList.add(ingredientIn);
            }
        }
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

    public Ingredient getIngredient(int index){
        return shoppingList.get(index);
    }

    public double getAmount(int index){
        return shoppingList.get(index).getAmount();
    }

    public String getUnit(int index){
        return shoppingList.get(index).getUnit();
    }

    public void removeIngredient(String ingredientNameIn, double amount, String unit) throws IllegalArgumentException{

        int index = getIngredientIndex(ingredientNameIn);

        if (index == -1){
            throw new IllegalArgumentException("Ingredient is not in your shopping list.");
        }else if (amount >= 0 && amount <= shoppingList.get(index).getAmount()){
            String unitOf = getUnit(index);
            if (unitOf.equals(unit)) {
                shoppingList.get(index).setAmount(shoppingList.get(index).getAmount() - amount);
                if (shoppingList.get(index).getAmount() == 0) {
                    shoppingList.remove(index);
                    length--;
                }
            }else{
                throw new IllegalArgumentException("You don't have an ingredient with those units.");
            }
        } else{
            throw new IllegalArgumentException("That is not a correct amount to remove.");
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
