import java.nio.channels.FileLockInterruptionException;
import java.text.DecimalFormat;
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

    public enum Unit{
        GRAM, KILOGRAM, OUNCE, POUND, GALLON, QUART, PINT, CUP, TABLESPOON, TEASPOON, FLUID_OUNCE, MILLILITER, LITER, NONE
    }

    public static Unit unitSimp(String unitIn){
        if(!Ingredient.validUnit(unitIn)) return Unit.NONE;
        if(unitIn.equals("litre"))
            return Unit.LITER;
        if(unitIn.equals("millilitre"))
            return Unit.MILLILITER;
        if(unitIn.equals("t"))
            return Unit.TEASPOON;
        if(unitIn.equals("T"))
            return Unit.TABLESPOON;
        String[] unitList = {"gram", "kilogram", "ounce", "pound", "gallon", "quart", "pint", "cup", "tablespoon", "teaspoon", "fluid ounce", "milliliter", "liter"};
        String[] abvList = {"g","kg","oz","lb","gal","q","p","c","tbs","tsp","fl oz","ml","l","","","","#","","qt","pt","","tbl","","","cc","","","","","lbs","","","","","tbsp"};
        if(unitIn.indexOf(".") != -1)
            unitIn = unitIn.replace(".","");
        for(int x = 0; x < unitList.length; x++){
            if(unitIn.equals(unitList[x]))
                return Unit.values()[x];
        }
        for(int x = 0; x < abvList.length; x++){
            if(unitIn.equals(abvList[x]))
                return Unit.values()[x%13];
        }
        return Unit.NONE;
    }

    private static DecimalFormat df =  new DecimalFormat("0.00");

    public static double unitConversion(String unitTo, Ingredient from) throws IllegalArgumentException{
        if(!Ingredient.validUnit(unitTo)) throw new IllegalArgumentException("Invalid Unit");
        if(from == null) throw new IllegalArgumentException("ingredient to be converted cannot be null");
        if(unitSimp(unitTo) == unitSimp(from.getUnit())) return from.getAmount();

        if(!(Ingredient.isVolumeUnit(unitTo) && Ingredient.isVolumeUnit(from.getUnit()))){ //if not both volume units
            if((Ingredient.isVolumeUnit(unitTo) || Ingredient.isVolumeUnit(from.getUnit()))){ //if one is a volume unit. This catches if neither is a volume unit which would still be true for the previous if statement
                if(!(from.getFood().getDensity() > 0))throw new IllegalArgumentException("no density to properly convert");
                if(Ingredient.isVolumeUnit(unitTo)){ // Weight to Volume
                    double temp = unitConversion("gram", from);
                    Ingredient tempI = new Ingredient(from.getFood(), temp/from.getFood().getDensity(), "ml");
                    return unitConversion(unitTo, tempI);
                }
                else{
                    //convert from volume to weight
                    double temp = unitConversion("ml", from);
                    Ingredient tempI = new Ingredient(from.getFood(),temp * from.getFood().getDensity(), "g");
                    return unitConversion(unitTo, tempI);
                }
            }
        }
        if(Ingredient.isVolumeUnit(unitTo)){
            double amount = from.getAmount();
            if(unitSimp(from.getUnit()) == Unit.MILLILITER){}
            else if(unitSimp(unitTo) != Unit.MILLILITER)
                amount = unitConversion("ml",from );
            if(unitSimp(unitTo) != Unit.MILLILITER){
                switch(unitSimp(unitTo)){
                    case GALLON:
                        amount *= 0.000219969;
                        break;
                    case QUART:
                        amount *= 0.000879877;
                        break;
                    case PINT:
                        amount *= 0.00175975;
                        break;
                    case CUP:
                        amount *= 0.00351951;
                        break;
                    case TABLESPOON:
                        amount *= 0.0563121;
                        break;
                    case TEASPOON:
                        amount *= 0.168936;
                        break;
                    case FLUID_OUNCE:
                        amount *= 0.0351951;
                        break;
                    case LITER:
                        amount *= 0.001;
                        break;
                    default:
                        break;
                }
            }
            else{
                switch(unitSimp(from.getUnit())){
                    case GALLON:
                        amount *= 4546.09;
                        break;
                    case QUART:
                        amount *= 1136.52;
                        break;
                    case PINT:
                        amount *= 568.261;
                        break;
                    case CUP:
                        amount *= 284.131;
                        break;
                    case TABLESPOON:
                        amount *= 17.7582;
                        break;
                    case TEASPOON:
                        amount *= 5.91939;
                        break;
                    case FLUID_OUNCE:
                        amount *= 28.4131;
                        break;
                    case LITER:
                        amount *= 1000.0;
                        break;
                    default:
                        break;
                }
            }
            return Double.parseDouble(df.format(amount));
        }
        else{
            Double amount = from.getAmount();
            if(unitSimp(from.getUnit()) == Unit.GRAM){}
            else if(unitSimp(unitTo) != Unit.GRAM)
                amount = unitConversion("g", from);
            if(unitSimp(unitTo) != Unit.GRAM){
                switch (unitSimp(unitTo)){
                    case KILOGRAM:
                        amount *= 0.001;
                        break;
                    case OUNCE:
                        amount *= 0.035274;
                        break;
                    case POUND:
                        amount *= 0.00220462;
                        break;
                    default:
                        break;
                }
            }
            else{
                switch (unitSimp(from.getUnit())){
                    case KILOGRAM:
                        amount *= 1000.0;
                        break;
                    case OUNCE:
                        amount *= 28.35;
                        break;
                    case POUND:
                        amount *= 453.592;
                        break;
                    default:
                        break;
                }
            }
            return Double.parseDouble(df.format(amount));
        }
    }


}
