import java.text.DecimalFormat;
import java.util.Objects;

public class Ingredient implements Comparable<Ingredient> {
    private Food food;
    private double amount;
    private String unit;

    public Ingredient(Food foodIn, double amtIn, String unitIn) throws IllegalArgumentException{
        if(foodIn == null) throw new IllegalArgumentException("food cannot be null");
        if(!(amtIn > 0)) throw new IllegalArgumentException("amount cannot be negitive or zero");
        food = foodIn;
        amount = amtIn;
        if(!validUnit(unitIn)) throw new IllegalArgumentException("Invalid Unit");
        unit = unitIn;
    }

    public int compareTo(Ingredient other){
        //returns -1 if "this" object is less than "that" object
        //returns 0 if they are equal
        //returns 1 if "this" object is greater than "that" object
        return this.food.getName().compareTo(other.food.getName());
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return food.getName();
    }

    public String getUnit(){ return unit;    }

    public String toString(){
        return amount + " " + unit + "\n" + food.getName() + "\n" + getCalories() + " Calories";
    }

    private static DecimalFormat calReturn = new DecimalFormat("0.00");


    /**
     * returns number of calories with given amount and unit
     * @return rounded to 2 decimal places.
     */
    public double getCalories(){
         return Double.parseDouble(calReturn.format(convertCalToUnit(unit, food.getCalories(), food.getDensity()) * amount));

    }

    /**
     * valid units include; gram, kilogram, ounce, pound, gallon, quart, pint, cup, tablespoon, teaspoon, fluid ounce, milliliter, millilitre, liter, litre
     * Common abbreviations will also be acceptable
     * @param unitIn A string of input unit
     * @return is unitIn valid
     */
    public static boolean validUnit(String unitIn){
        String[] unitList = {"gram", "kilogram", "ounce", "pound", "gallon", "quart", "pint", "cup", "tablespoon", "teaspoon", "fluid ounce", "milliliter", "millilitre", "liter", "litre"};
        String[] abvList = {"g","kg","oz","lb","#","gal","q","qt","p","pt","c","tbs","tbl","tbsp","tsp","fl oz","cc","ml","l"};
        if(unitIn.equals("T") || unitIn.equals("t")) return true;
        unitIn = unitIn.toLowerCase();
        if(unitIn.contains("tbs")||unitIn.contains("tbl")||unitIn.contains("tbsp")||unitIn.contains("tsp")) {
            if (unitIn.indexOf('.') == -1) {}
            else if(unitIn.indexOf('.')!= unitIn.lastIndexOf('.'))return false;
            else if(unitIn.lastIndexOf('.')!= unitIn.length()-1) return false;
            else unitIn = unitIn.replace(".", "");
        }
        for(int x =0; x < unitList.length; x ++){
            if(unitIn.equals(unitList[x]))
                return true;
        }

        for(int x =0; x < abvList.length; x ++) {
            if (unitIn.equals(abvList[x]))
                return true;
        }
        return false;
    }


    private static boolean isVolumeUnit(String unitIn){
        String[] unitList = {"gallon", "quart", "pint", "cup", "tablespoon", "teaspoon", "fluid ounce", "milliliter", "millilitre", "liter", "litre"};
        for(int x = 0; x < unitList.length; x++){
            if(unitIn.equalsIgnoreCase(unitList[x]))
                return true;
        }
        if(unitIn.equals("T")||unitIn.equalsIgnoreCase("t")) return true;
        String[] abvList = {"gal","q","qt","p","pt","c","tbs","tbl","tbsp","tsp","tbs.","tbl.","tbsp.","tsp.","fl oz","cc","ml","l"};
        for(int x = 0; x < abvList.length; x++){
            if(unitIn.equalsIgnoreCase(abvList[x]))return true;
        }
        return false;
    }

    private static DecimalFormat df = new DecimalFormat("0.0000");

    /**
     * Takes the calories input and converts to input unit
     * @param unitTo Must be a valid unit
     * @param calIn Assumes input calories is calories per 100g of food
     * @param density should be g/cm3. this is for converting from volume to weight units. if left 0 and converting to a volume unit will throw IllegalArgumentException
     *                Can be left 0 if converting weight to weight.
     * @return amount of calories in 1 of input unit.
     */
    public static double convertCalToUnit(String unitTo, double calIn, double density ) throws IllegalArgumentException{
        if(!validUnit(unitTo)) throw new IllegalArgumentException("invalid unit conversion");
        if(isVolumeUnit(unitTo)&& !(density > 0.0)) throw new IllegalArgumentException("Converting to volume with density of zero");

        double returnVal = calIn / 100.0;

        if(!unitTo.equals("T")) unitTo = unitTo.toLowerCase();
        switch(unitTo){
            case "gram":
            case "g":
            case "kilogram":
            case "kg":
                if(unitTo.equals("kilogram")||unitTo.equals("kg"))
                    returnVal *= 1000.0;
                break;


            case "ounce":
            case "oz":
            case "pound":
            case "lb":
            case "#":
                returnVal /= 0.035274;
                if(unitTo.equals("pound") || unitTo.equals("lb") || unitTo.equals("#"))
                    returnVal *= 16;
                break;


            case "gal":
            case "q":
            case "qt":
            case "p":
            case "pt":
            case "c":
            case "T":
            case "tbs":
            case "tbl":
            case "tbsp":
            case "tsp":
            case "tbs.":
            case "tbl.":
            case "tbsp.":
            case "tsp.":
            case "t":
            case "gallon":
            case "quart":
            case "pint":
            case "cup":
            case "tablespoon":
            case "teaspoon":
                returnVal = (returnVal * density) * 3785.41;
                if(unitTo.equals("q")||unitTo.equals("qt")||unitTo.equals("quart"))
                    returnVal /= 4;
                else if(unitTo.equals("p")||unitTo.equals("pt")||unitTo.equals("pint"))
                    returnVal /= 8;
                else if(unitTo.equals("c")||unitTo.equals("cup"))
                    returnVal /= 16;
                else if(unitTo.equals("tbs")||unitTo.equals("tbs.")
                        ||unitTo.equals("tbl")||unitTo.equals("tbl.")
                        ||unitTo.equals("tbsp")||unitTo.equals("tbsp.")
                        ||unitTo.equals("tablespoon")||unitTo.equals("T"))
                    returnVal /= 256;
                else if(unitTo.equals("tsp")||unitTo.equals("tsp.")
                        ||unitTo.equals("teaspoon")||unitTo.equals("t"))
                    returnVal /= 768;
                break;


            case "fluid ounce":
            case "fl oz":
                returnVal = (returnVal * density) * 29.5735;
                break;

            case "milliliter":
            case "millilitre":
            case "cc":
            case "ml":
            case "liter":
            case "litre":
            case "l":
                returnVal *= density;
                if(unitTo.equals("liter")||unitTo.equals("litre")||unitTo.equals("l"))
                    returnVal*=1000;
                break;
            default:
                break;
        }

        return Double.parseDouble(df.format(returnVal));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Double.compare(that.amount, amount) == 0 &&
                Objects.equals(food, that.food) &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(food, amount, unit);
    }
}
