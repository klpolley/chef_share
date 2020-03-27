public class Food {
    private String name;
    private int calories;

    public Food(String nameIn, int caloriesIn) throws IllegalArgumentException{
        if (nameIn == "" || nameIn==" "){
            throw new IllegalArgumentException("food name cannot be empty");
        }
        else if (caloriesIn < 1){
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

    public int getCalories(){
        return calories;
    }
}
