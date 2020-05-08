import java.util.*;

public class ChefShareTest {

    public static ChefShare createTestApp() {
        ChefShare app = new ChefShare();
        app.addAccount("username1", "password1", "");
        app.addAccount("username2", "password2", "");
        app.addAccount("username3", "password3", "");

        app.login("username1", "password1");

        ArrayList<String> steps = new ArrayList<>();
        steps.add("step 1");
        steps.add("step 2");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Food eggs = new Food("Eggs", 100);
        app.addFood(eggs);
        ingredients.add(new Ingredient(eggs, 2, "g"));

        Recipe recipe = new Recipe("Thing With Eggs", steps, ingredients);
        app.createRecipe(recipe);

        app.logout();
        app.login("username3", "password3");

        steps = new ArrayList<>();
        steps.add("add eggs");
        steps.add("add more eggs");
        steps.add("add infinite eggs");
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(eggs, 100, "g"));
        Food superEggs = new Food("Super Eggs", 1000);
        app.addFood(superEggs);
        ingredients.add(new Ingredient(superEggs, 100, "g"));

        recipe = new Recipe("Thing With SO MANY Eggs", steps, ingredients);
        app.createRecipe(recipe);

        app.logout();
        app.login("username2", "password2");

        steps = new ArrayList<>();
        steps.add("egg 1");
        steps.add("egg 2");
        steps.add("egg 3");
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(eggs, 3, "g"));

        recipe = new Recipe("Thing With Eggs", steps, ingredients);
        app.createRecipe(recipe);

        recipe = new Recipe("XEggs", steps, ingredients);
        app.createRecipe(recipe);

        recipe = new Recipe("Invisible", steps, ingredients);
        app.createRecipe(recipe);

        app.addFood(eggs);
        app.addFood(superEggs);

        app.logout();

        return app;
    }

}
