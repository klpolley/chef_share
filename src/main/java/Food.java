/**
 * Just a place holder class
 */

public class Food {
    private String name;
    private double calories; //Assume that this is number of calories per 100g of said food.
    private double density; // g/ml or g/cm3

    public Food(String nameIn, double caloriesIn, double densityIn){
        density = densityIn;
        name = nameIn;
        calories = caloriesIn;
    }

    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }

    public double getDensity() {
        return density;
    }
}
