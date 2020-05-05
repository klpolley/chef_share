import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class RecipeUI {

    private ChefShare app;

    public RecipeUI(ChefShare app) {
        this.app = app;
    }

    public void recipeView(BufferedReader reader) {
        System.out.println("Recipes: add, edit, delete, view, search. 'back' to return.");

        String command = "";

        try {
            command = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        while (!command.equals("back")) {

            if (command.equals("search")) {
                searchRecipes(reader);
            }
            else if (command.equals("view")) {
                viewMyRecipes(reader);
            } else if (command.equals("delete")) {
                deleteRecipe(reader);
            }
            else {
                System.out.println("Invalid command.");
            }

            System.out.println("Recipes: add, edit, delete, view, search. 'back' to return.");

            try {
                command = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
    }

    public void viewMyRecipes(BufferedReader reader) {
        System.out.println("Your Recipes");

        List<Recipe> myRecipes = app.getCurrentUserRecipes();
        System.out.println(app.printRecipeList(myRecipes));

        recipeSelector(reader, myRecipes);
    }

    public void searchRecipes(BufferedReader reader) {
        System.out.println("Enter search term or recipe name. Enter nothing to view all.");

        String search = "";

        try {
            search = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        List<Recipe> matches = app.getRecipeListByNameSearch(search);

        System.out.println(app.printRecipeList(matches));

        recipeSelector(reader, matches);

    }

    public void recipeSelector(BufferedReader reader, List<Recipe> recipeList) {
        System.out.println("Enter recipe number to view. Enter 'done' to leave search.");

        String select = "";

        try {
            select = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        while (!select.equals("done")) {

            try {
                int num = Integer.parseInt(select);
                System.out.println(app.printRecipeSelection(num, recipeList));
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter a number from the list.");
            }

            System.out.println("Enter recipe number to view. Enter 'done' to leave search.");

            try {
                select = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
    }

    public void deleteRecipe(BufferedReader reader) {
        System.out.println("Enter the full name of the recipe to be deleted (case sensitive).");

        String name = "";

        try {
            name = reader.readLine();
            Account me = app.getCurrentUser();
            me.deleteRecipe(name);
            System.out.println(name + " has been deleted.");
        } catch (IOException e) {
            System.out.println("Error reading input.");
        } catch (IllegalArgumentException e) {
            System.out.println("That recipe is not in your collection.");
        }


    }

}
