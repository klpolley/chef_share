import util.JsonUtil;

import java.io.IOException;
import java.util.List;

public class UserTest {

    public static void main(String[] args) throws IOException {

        //CREATE ACCOUNTS
        AccountContainer chefAccts = new AccountContainer();
        chefAccts.createAccount("firstchef", "password1", "the first chef");
        chefAccts.createAccount("secondchef", "password2", "the second chef");
        chefAccts.createAccount("thirdchef", "password3", "the third chef");

        //LOGIN
        chefAccts.login("firstchef", "password1");
        Account account = chefAccts.getCurrentAccount();
        System.out.println("Logged in as "+account.getUsername());

        //IMPORT RECIPES FROM JSON
        List<Recipe> recipes = JsonUtil.listFromJsonFile("src/test/resources/UserTest.json", Recipe.class);
        for (Recipe r:recipes) {
            account.addRecipe(r);
        }

        //PRINT ALL RECIPES
        System.out.println("All Recipes");
        System.out.println(chefAccts.getRecipeListString(chefAccts.getAllRecipes()));

        //LOGOUT AND LOGIN AS DIFFERENT USER
        chefAccts.logout();
        System.out.println("Logout");
        chefAccts.login("secondchef", "password2");
        account = chefAccts.getCurrentAccount();
        System.out.println("Logged in as "+account.getUsername());

        //PRINT ALL RECIPES
        System.out.println("All Recipes");
        System.out.println(chefAccts.getRecipeListString(chefAccts.getAllRecipes()));

        //PRINT DETAILS FOR SPECIFIC RECIPE
        System.out.println("Recipe Details");
        System.out.println(chefAccts.printSelectedRecipe(1, chefAccts.getAllRecipes()));

        //ADD SELECTED RECIPE TO SHOPPING LIST
        account.addRecipeToShoppingList(chefAccts.getSelectedRecipe(2, chefAccts.getAllRecipes()));

        //PRINT SHOPPING LIST
        System.out.println("Shopping List");
        System.out.println(account.printShoppingList());


    }

}
