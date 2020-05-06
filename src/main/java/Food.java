import java.util.Objects;

public class Food {
    private String name;
    private double calories; //Assume that this is number of calories per 100g of said food.
    private double density; // g/ml or g/cm3

    public Food(String nameIn, double caloriesIn) throws IllegalArgumentException{
        if (nameIn == "" || nameIn==" "){
            throw new IllegalArgumentException("food name cannot be empty");
        }
        else if (!isCalorieValid(caloriesIn)){
            throw new IllegalArgumentException("calories must be a positive value greater than 0");
        }
        else{
            name = nameIn;
            calories = caloriesIn;
            density = 0.0;
        }

    }

    public Food(String nameIn, double caloriesIn, double densityIn) throws IllegalArgumentException{
        if (nameIn == "" || nameIn==" "){
            throw new IllegalArgumentException("food name cannot be empty");
        }
        else if (!isCalorieValid(caloriesIn)){
            throw new IllegalArgumentException("calories must be a positive value greater than 0");
        }
        else{
            name = nameIn;
            calories = caloriesIn;
            density = densityIn;
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

    public String details(){
        return name + "\n" + calories + "\n" + density;
    }

    public static boolean isCalorieValid(double caloriesIn){
        boolean isValid = true;

        if (caloriesIn <= 0){
            isValid = false;
        }
        else {
            String amountString = Double.toString(caloriesIn);
            if (amountString.indexOf('.')+2 < amountString.length()-1){
                isValid=false;
            }
        }

        return isValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Double.compare(food.calories, calories) == 0 &&
                Double.compare(food.density, density) == 0 &&
                Objects.equals(name, food.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, calories, density);
    }

    //JSON functions:

    public Food(){}

    public void setName(String name){
        if (name == "" || name ==" "){
            throw new IllegalArgumentException("food name cannot be empty");
        }
        else this.name = name;
    }

    public void setCalories(double calories){
        if (!isCalorieValid(calories)){
            throw new IllegalArgumentException("calories must be a positive value greater than 0");
        }
        else this.calories = calories;
    }

    public void setDensity(double density){this.density = density;}
}
