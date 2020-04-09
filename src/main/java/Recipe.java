import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Recipe implements Comparable<Recipe> {
    private String name;
    private List<String> steps;
    private List<Ingredient> ingredients;
    private String author;

    public Recipe(String nameIn) throws IllegalArgumentException{
        setName(nameIn);
        this.steps = new ArrayList();
        this.ingredients = new ArrayList();
    }

    public Recipe(String nameIn, ArrayList<String> stepsIn, ArrayList<Ingredient> ingredientsIn) throws IllegalArgumentException{
        setName(nameIn);
        this.steps = stepsIn;
        this.ingredients = ingredientsIn;
    }

    public int compareTo(Recipe other){
        //returns -1 if "this" object is less than "that" object
        //returns 0 if they are equal
        //returns 1 if "this" object is greater than "that" object
        return this.name.compareTo(other.name);
    }

    public void setName(String nameIn) throws IllegalArgumentException{
        if(validName(nameIn))
            this.name = nameIn;
        else throw new IllegalArgumentException("Name must contain at least one character!");
    }

    public String getName(){
        return name;
    }

    public void setAuthor(String user) {
        this.author = user;
    }

    public String getAuthor(){
        return author;
    }

    public boolean validName(String name){
        for(int i = 0; i < name.length(); i++){
            char c = name.charAt(i);
            if(c != ' ')
            return true;
        }
        return false;
    }

    public void removeStep(int stepNum) throws IllegalArgumentException{
        if(stepNum > steps.size() || stepNum < 1) throw new IllegalArgumentException("Cannot remove a step that does not exist");
        steps.remove(stepNum-1);
    }

    public Collection<String> getSteps(){
        return this.steps;
    }

    public void addStep(String stepIn){
        steps.add(stepIn);
    }

    public void addStep(String stepIn, int stepNum) throws IllegalArgumentException{
        if(stepNum > steps.size()+1 || stepNum < 1) throw new IllegalArgumentException("Cannot add step there");
        steps.add(stepNum -1, stepIn);
    }

    public String getStep(int stepNum) throws IllegalArgumentException{
        if(stepNum > steps.size() || stepNum < 1) throw new IllegalArgumentException("No such Step");
        return steps.get(stepNum-1);
    }

    public int getNumberSteps(){
        return steps.size();
    }

    public void editStep(int stepNum, String newStep) throws IllegalArgumentException{
        if(stepNum > steps.size() || stepNum < 1) throw new IllegalArgumentException("No such step");
        steps.remove(stepNum-1);
        steps.add(stepNum-1, newStep);
    }

    public String getPrintableSteps(){
        String result = "";
        for(int i = 0; i < steps.size(); i++){
            result += (i+1) + ": " + steps.get(i) + "\n";
        }
        return result;
    }

    public String getPrintableIngredients(){
        String result = "";
        for(Ingredient i : ingredients){
            result += i.getAmount() + i.getUnit() + " " + i.getName() + "\n";
        }
        return result;
    }

    public Collection<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public String getIngredient(int loc) {
        if(loc > 0 && loc <= getNumberIngredients()){
            Ingredient i = ingredients.get(loc-1);
            return i.getAmount() + i.getUnit() + " " + i.getName();
        }
        else throw new IllegalArgumentException("Ingredient does not exist!");
    }

    public int getNumberIngredients() {
        return ingredients.size();
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void addIngredient(Ingredient ingredient, int loc) {
        if(loc > 0 && loc <= getNumberIngredients() + 1){
            ingredients.add(loc - 1, ingredient);
        }
        else throw new IllegalArgumentException("Cannot insert ingredient at that location");
    }

    public void editIngredient(Ingredient ingredient, int loc) {
        if(loc > 0 && loc <= getNumberIngredients()){
            ingredients.remove(loc - 1);
            ingredients.add(loc - 1, ingredient);
        }
        else throw new IllegalArgumentException("Ingredient does not exist!");
    }

    public void removeIngredient(int loc) {
        if(loc > 0 && loc <= getNumberIngredients()){
            ingredients.remove(loc - 1);
        }
        else throw new IllegalArgumentException("Ingredient does not exist!");
    }
}
