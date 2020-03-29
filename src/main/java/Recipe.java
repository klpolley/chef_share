import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Recipe {
    private String name;
    private List<String> steps;
    private List<Ingredient> ingredients;

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

    public void setName(String nameIn) throws IllegalArgumentException{
        if(validName(nameIn))
            this.name = nameIn;
        else throw new IllegalArgumentException("Name must contain at least one character!");
    }

    public String getName(){
        return name;
    }

    public boolean validName(String name){
        for(int i = 0; i < name.length(); i++){
            char c = name.charAt(i);
            if(c != ' ')
            return true;
        }
        return false;
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
            result += (i+1) + ":  " + steps.get(i) + "\n";
        }
        return result;
    }

    public String getPrintableIngredients(){
        String result = "";
        for(Ingredient i : ingredients){
            result += i.getName() + "\n";
        }
        return result;
    }

    public Collection<Ingredient> getIngredients() {
        return this.ingredients;
    }
}
