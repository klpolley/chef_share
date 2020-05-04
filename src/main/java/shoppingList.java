import java.nio.channels.FileLockInterruptionException;
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

    public static HashMap<Integer, HashMap<Integer, Double>> unitMultCreate(){
        HashMap<Integer, HashMap<Integer, Double>> returnMap = new HashMap<>();

        HashMap<Integer, Double> temp = new HashMap<>();
        temp.put(1, 0.001);
        temp.put(2, 0.035274);
        temp.put(3, 0.00220462);
        returnMap.put(0, temp);

        temp = new HashMap<>();
        temp.put(0, 1000.0);
        temp.put(2, 35.274);
        temp.put(3, 2.205);
        returnMap.put(1, temp);

        temp = new HashMap<>();
        temp.put(0, 28.35);
        temp.put(1, 0.0283495);
        temp.put(3, 0.0625);
        returnMap.put(2, temp);

        temp = new HashMap<>();
        temp.put(0, 453.592);
        temp.put(1, 0.453592);
        temp.put(2, 16.0);
        returnMap.put(3, temp);

        temp = new HashMap<>();
        temp.put(5,4.0);
        temp.put(6,8.0 );
        temp.put(7, 16.0);
        temp.put(8, 256.0);
        temp.put(9, 768.0);
        temp.put(10, 160.0);
        temp.put(11, 4546.09);
        temp.put(12, 4.54609);
        returnMap.put(4, temp);

        temp = new HashMap<>();
        temp.put(4,0.25);
        temp.put(6,2.0 );
        temp.put(7, 4.0);
        temp.put(8, 64.0);
        temp.put(9, 192.0);
        temp.put(10, 40.0);
        temp.put(11, 1136.52);
        temp.put(12, 1.13652);
        returnMap.put(5, temp);

        temp = new HashMap<>();
        temp.put(4,0.125);
        temp.put(5,0.5 );
        temp.put(7, 2.0);
        temp.put(8, 32.0);
        temp.put(9, 96.0);
        temp.put(10, 20.0);
        temp.put(11, 568.261);
        temp.put(12, 0.568261);
        returnMap.put(6, temp);

        temp = new HashMap<>();
        temp.put(4,0.0625);
        temp.put(5,0.25 );
        temp.put(6, .5);
        temp.put(8, 16.0);
        temp.put(9, 48.0);
        temp.put(10, 10.0);
        temp.put(11, 284.131);
        temp.put(12, 0.284131);
        returnMap.put(7, temp);

        temp = new HashMap<>();
        temp.put(4,0.00390625);
        temp.put(5,0.015625 );
        temp.put(6, 0.03125);
        temp.put(7, 0.0625);
        temp.put(9, 3.0);
        temp.put(10, .625);
        temp.put(11, 17.7582);
        temp.put(12, 0.0177582);
        returnMap.put(8, temp);

        temp = new HashMap<>();
        temp.put(4,0.00130208);
        temp.put(5,0.00520834 );
        temp.put(6, 0.0104167);
        temp.put(7, 0.0208333);
        temp.put(8, 0.333333);
        temp.put(10, 0.208333);
        temp.put(11, 5.91939);
        temp.put(12, 0.00591939);
        returnMap.put(9, temp);

        temp = new HashMap<>();
        temp.put(4,0.00625);
        temp.put(5,0.025 );
        temp.put(6, 0.05);
        temp.put(7, 0.1);
        temp.put(8, 1.6);
        temp.put(9, 4.8);
        temp.put(11, 28.4131);
        temp.put(12, 0.0284131);
        returnMap.put(10, temp);

        temp = new HashMap<>();
        temp.put(4,0.000219969);
        temp.put(5,0.000879877 );
        temp.put(6, 0.00175975);
        temp.put(7, 0.00351951);
        temp.put(8, 0.0563121);
        temp.put(9, 0.168936);
        temp.put(10, 0.0351951);
        temp.put(12, 0.001);
        returnMap.put(11, temp);

        temp = new HashMap<>();
        temp.put(4,0.219969);
        temp.put(5,0.879877 );
        temp.put(6, 1.75975);
        temp.put(7, 3.51951);
        temp.put(8, 56.3121);
        temp.put(9, 168.936);
        temp.put(10, 35.1951);
        temp.put(11, 1000.0);
        returnMap.put(12, temp);

        return returnMap;
    }

    public static double unitConversion(String unitTo, Ingredient from) throws IllegalArgumentException{
        if(!Ingredient.validUnit(unitTo)) throw new IllegalArgumentException("Invalid Unit");
        if(from == null) throw new IllegalArgumentException("ingredient to be converted cannot be null");
        if(unitSimp(unitTo) == unitSimp(from.getUnit())) return from.getAmount();

        if(!(Ingredient.isVolumeUnit(unitTo) && Ingredient.isVolumeUnit(from.getUnit()))){ //if not both volume units
            if((Ingredient.isVolumeUnit(unitTo) || Ingredient.isVolumeUnit(from.getUnit()))){ //if one is a volume unit. This catches if neither is a volume unit which would still be true for the previous if statement
                if(!(from.getFood().getDensity() > 0))throw new IllegalArgumentException("no density to properly convert");
                if(Ingredient.isVolumeUnit(unitTo)){
                    double temp = unitConversion("gram", from);
                    Ingredient tempI = new Ingredient(from.getFood(), temp/from.getFood().getDensity(), "ml");
                    return unitConversion(unitTo, tempI);
                }
                else{
                    //convert from to non volume
                    double temp = unitConversion("ml", from);
                    Ingredient tempI = new Ingredient(from.getFood(),temp * from.getFood().getDensity(), "g");
                    return unitConversion(unitTo, tempI);
                }
            }
        }
        HashMap<Integer, HashMap<Integer, Double>> unitMult =unitMultCreate();
        return from.getAmount() * unitMult.get(unitSimp(from.getUnit())).get(unitSimp(unitTo));
    }

    public static int unitSimp(String unitIn){
        if(!Ingredient.validUnit(unitIn)) return -1;
        if(unitIn.equals("litre"))
            return 12;
        if(unitIn.equals("millilitre"))
            return 11;
        if(unitIn.equals("t"))
            return 9;
        if(unitIn.equals("T"))
            return 8;
        String[] unitList = {"gram", "kilogram", "ounce", "pound", "gallon", "quart", "pint", "cup", "tablespoon", "teaspoon", "fluid ounce", "milliliter", "liter"};
        String[] abvList = {"g","kg","oz","lb","gal","q","p","c","tbs","tsp","fl oz","ml","l","","","","#","","qt","pt","","tbl","","","cc","","","","","lbs","","","","","tbsp"};
        if(unitIn.indexOf(".") != -1)
            unitIn = unitIn.replace(".","");
        for(int x = 0; x < unitList.length; x++){
            if(unitIn.equals(unitList[x]))
                return x;
        }
        for(int x = 0; x < abvList.length; x++){
            if(unitIn.equals(abvList[x]))
                return x%13;
        }
        return -1;
    }
}
