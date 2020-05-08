import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class ChefShare {

    private AccountContainer accounts;
    private Library foods;
    private TagLib tags;

    public ChefShare() {
        accounts = new AccountContainer();
        foods = new Library();
        tags = new TagLib();
    }

    public void login(String username, String password) throws IllegalArgumentException, IllegalStateException {
        accounts.login(username, password);
    }

    public void logout() throws IllegalStateException {
        accounts.logout();
    }

    public Account getCurrentUser() {
        return accounts.getCurrentAccount();
    }

    public void addAccount(String user, String pass, String bio) throws IllegalArgumentException {
        accounts.createAccount(user, pass, bio);
    }

    public void addFood(Food f){
        foods.addFood(f);
    }

    public boolean containsFood(String s){
        return foods.isFoodPresent(s);
    }

    public Food getFood(String s){
        return foods.getFood(s);
    }

    public ArrayList<Food> searchFood(String s){
        return foods.search(s);
    }

    public String listAllFood(){
        return foods.listAllFood();
    }

    public List<Recipe> getRecipeListByNameSearch(String search) {
        return accounts.getRecipesByName(search);
    }

    public String printRecipeList(List<Recipe> r) {
        return accounts.getRecipeListString(r);
    }

    public String printRecipeSelection(int selection, List<Recipe> recipes) {
        return accounts.printSelectedRecipe(selection, recipes);
    }

    public void createRecipe(Recipe r) {
        accounts.getCurrentAccount().createRecipe(r);
    }

    public List<Recipe> getCurrentUserRecipes() {
        return getCurrentUser().getRecipeList();
    }
  
    public Recipe getRecipeByName(String name){
        List<Recipe> rs = accounts.getAllRecipes();
        for(Recipe r:rs){
            if(r.getName().equalsIgnoreCase(name))
                return r;
        }
        return null;
    }
    public shoppingList getCurrentUserShoppingList() { return getCurrentUser().getShoppingList();}

    public String printShoppingList(shoppingList s){ return getCurrentUser().printShoppingList();}

    public int getCurrentUserIngredientIndex(String name) {return accounts.getCurrentAccount().getShoppingList().getIngredientIndex(name);}

    public double getCurrUserIngAmount(int index) {return accounts.getCurrentAccount().getShoppingList().getAmount(index);}

    public String getCurrUserIngUnit(int index) {return accounts.getCurrentAccount().getShoppingList().getUnit(index); }

    public int ingredientListLength(Recipe r) { return r.getNumberIngredients();}

    public Collection<Ingredient> recipeIngredientList(Recipe r) { return r.getIngredients();}



}
