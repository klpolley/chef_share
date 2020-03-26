public class Ingredient {
    private Food food;
    private double amount;
    private String unit;

    public Ingredient(Food foodIn, double amtIn, String unitIn) throws IllegalArgumentException{}

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return"";
    }

    public String getUnit(){
        return"";
    }

    public double getCalories(){
        return -1234.00;
    }

    /**
     * valid units include; grams, kilograms, ounces, pounds, gallon, quart, pint, cup, tablespoon, teaspoon, liquid ounces, milliliter, liter
     * Common abbreviations will also be acceptable
     * @param unitIn A string of input unit
     * @return is unitIn valid
     */
    public static boolean validUnit(String unitIn){
        return false;
    }

    /**
     * Takes the calories input and converts to input unit
     * @param unitIn Must be a valid unit
     * @param calIn Assumes input calories is calories per 100g of food
     * @param density should be g/cm3. this is for converting from volume to weight units. if left 0 and converting to a volume unit will throw IllegalArgumentException
     *                Can be left 0 if converting weight to weight.
     * @return amount of calories in 1 of input unit.
     */
    public static int convertCalToUnit(String unitIn, double calIn, double density ) throws IllegalArgumentException{
        return-1234;
    }

}
