public class Food {
    private String name;
    private double calories; //Assume that this is number of calories per 100g of said food.
    private double density; // g/ml or g/cm3

    public Food(String nameIn, double caloriesIn) throws IllegalArgumentException{
        if (nameIn == "" || nameIn==" "){
            throw new IllegalArgumentException("food name cannot be empty");
        }
        else if (!(caloriesIn>0)){
            throw new IllegalArgumentException("calories must be a positive value greater than 0");
        }
        else{
            name = nameIn;
            calories = caloriesIn;
        }

    }

    public String getName(){
        return name;
    }

    public double getCalories(){
        return calories;
    }

    public double getDensity() {
        return density;
    }

    public String toString(){
        return name;
    }
}
