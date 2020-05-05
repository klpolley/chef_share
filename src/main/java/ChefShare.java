import java.util.List;

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

}
