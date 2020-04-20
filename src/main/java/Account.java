import javax.print.attribute.HashDocAttributeSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.Collections;

public class Account {

    private String username;
    private String password;
    private String biography;
    private HashMap<String, Recipe> recipeList;
    private shoppingList shopping;
    private ArrayList <Recipe> cookedRecipesList;

    public Account(String username, String password, String bio) throws IllegalArgumentException {
        if (!isUserValid(username)) throw new IllegalArgumentException("Username must be between 6 and 15 characters, alphanumeric or _ only");
        if (!isPasswordValid(password)) throw new IllegalArgumentException("Password must be at least 8 characters long");
        this.username = username;
        this.password = password;
        this.biography = bio;
        this.recipeList = new HashMap<>();
        this.shopping = new shoppingList();
        this.cookedRecipesList = new ArrayList<Recipe>();

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUser) throws IllegalArgumentException {
        if (!isUserValid(newUser)) throw new IllegalArgumentException("Username must be between 6 and 15 characters, alphanumeric or _ only");
        this.username = newUser;
    }

    public boolean confirmPassword(String password) {
        if (password.equals(this.password)) return true;
        else return false;
    }

    public void setPassword(String newPassword) {
        if (!isPasswordValid(newPassword)) throw new IllegalArgumentException("Password must be at least 8 characters long");
        this.password = newPassword;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String newBio) {
        this.biography = newBio;
    }

    public static boolean isUserValid(String username){
        //valid username has between 6 and 15 characters, alphanumeric or underscore
        //taken usernames will be handled by the account container class
        int length = username.length();
        if (length < 6 || length > 15) {
            return false;
        } else {
            for (int i = 0; i < length; i++) {
                //nums = 48-57, upper = 65-90, lower = 97-122, _ = 95
                char c = username.charAt(i);
                if (c > 47 && c < 58);
                else if (c > 64 && c < 91);
                else if (c > 96 && c < 123);
                else if (c == 95);
                else return false;
            }
            return true;
         }
    }

    public static boolean isPasswordValid(String password) {
        //valid password is at least 8 characters long
        //alphanumeric and special characters allowed
        //spaces not allowed
        //let the user determine how secure or not they want to be
        int length = password.length();
        if (length < 8) {
            return false;
        } else {
            for(char i:password.toCharArray()) {
                if (i == ' ') {
                    return false;
                }
            }
            return true;
        }
    }

    public void addStep(String name, String step, int stepNumber)throws IllegalArgumentException{
        if(!recipeList.containsKey(name)) throw new IllegalArgumentException("No Such Recipe");
        recipeList.get(name).addStep(step,stepNumber);
    }
    public void addStep(String name, String step)throws IllegalArgumentException{
        if(!recipeList.containsKey(name)) throw new IllegalArgumentException("No Such Recipe");
        recipeList.get(name).addStep(step);
    }

    public String getStep(String name, int stepNum) throws IllegalArgumentException{
        if(!recipeList.containsKey(name)) throw new IllegalArgumentException("No Such Recipe");
        return recipeList.get(name).getStep(stepNum);
    }
    public int getNumberSteps(String name)throws IllegalArgumentException{
        if(!recipeList.containsKey(name)) throw new IllegalArgumentException("No Such Recipe");
        return recipeList.get(name).getNumberSteps();
    }
    public void editStep(String name, int stepNum, String newStep) throws IllegalArgumentException{
        if(!recipeList.containsKey(name)) throw new IllegalArgumentException("No Such Recipe");
        recipeList.get(name).editStep(stepNum, newStep);
    }

    public void createRecipe(String name) throws IllegalArgumentException{
        if(recipeList.containsKey(name)) throw new IllegalArgumentException("Recipe already Exits");
        Recipe r = new Recipe(name);
        r.setAuthor(username);
        recipeList.put(name, new Recipe(name));
    }
    public void createRecipe(Recipe In) throws IllegalArgumentException{
        if(recipeList.containsKey(In.getName())) throw new IllegalArgumentException("Recipe already Exits");
        In.setAuthor(username);
        recipeList.put(In.getName(), In);
    }

    public String recipeListToString(){
        if(recipeList.size() <= 0) return "";
        Set<String> set = recipeList.keySet();
        String[] arr = {""};
        arr = set.toArray(arr);
        Arrays.sort(arr);
        String ret = "";
        for(int x = 0; x < arr.length; x++){
            ret+= arr[x] + "\n";
        }
        return ret;
    }

    //get sorted list of recipe names (alphabetical)
    public List<String> getRecipeNameList() {
        List<String> recipes = new ArrayList<>();
        for(String name:recipeList.keySet()) {
            recipes.add(name);
        }
        Collections.sort(recipes);
        return recipes;
    }

    //get sorted list of recipes
    public List<Recipe> getRecipeList() {
        List<String> names = getRecipeNameList();
        List<Recipe> recipes = new ArrayList<>();
        for (String name: names) {
            recipes.add(recipeList.get(name));
        }
        return recipes;
    }

    public String recipeToString(String name)throws IllegalArgumentException{
        if(!recipeList.containsKey(name)) throw new IllegalArgumentException("No Such Recipe");
        return recipeList.get(name).getPrintableSteps();
    }

    public int numOfRecipes(){return recipeList.size();}

    public shoppingList getShoppingList() {
        return shopping;
    }

    public String printShoppingList() {
        return shopping.printList();
    }

    public void addToShoppingList(Ingredient ingr) {
        shopping.addIngredient(ingr);
    }

    public void removeFromShoppingList(String name, double amount, String unit) {
        shopping.removeIngredient(name, amount, unit);

    }

    public int numOfCooked(){return cookedRecipesList.size();}

    public void addToCookedList(Recipe recipeCooked){
        cookedRecipesList.add(recipeCooked);
    }


}
