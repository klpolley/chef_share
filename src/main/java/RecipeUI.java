import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeUI {

    private ChefShare app;
    private FoodUI foodUI;

    public RecipeUI(ChefShare app) {
        this.app = app;
        this.foodUI = new FoodUI(app);
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
            }
            else if (command.equals("add")) {
                addRecipe(reader);
            }
            else if (command.equals("edit")) {

            }
            else if (command.equals("delete")) {
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

    public void addRecipe(BufferedReader reader) {
        System.out.println("Enter name of new recipe.");

        String name = "";

        try {
            name = reader.readLine();
            if (app.getCurrentUser().getRecipeNameList().contains(name))
                throw new IllegalArgumentException("A recipe with that name already exists.");

            List<Ingredient> ings = addIngredients(reader);

            List<String> steps = addSteps(reader);

            Recipe r = new Recipe(name, steps, ings);
            app.getCurrentUser().createRecipe(r);
            System.out.println("Recipe " + name + " created!");

        } catch (IOException e) {
            System.out.println("Error reading input.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Ingredient> addIngredients(BufferedReader reader) {
        System.out.println("Ingredients. Enter 'add' to create ingredient or 'done' when complete.");

        String command = "";

        try {
            command = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        List<Ingredient> ings = new ArrayList<>();

        while (!command.equals("done")) {
            if (command.equals("add")){
                try {
                    System.out.println("Selecting food for ingredient.");
                    Food myFood = foodUI.foodTool(reader);
                    System.out.println("Creating Ingredient");
                    System.out.println("Enter amount (number only).");
                    double amt = Double.parseDouble(reader.readLine());
                    System.out.println("Enter unit of measurement.");
                    String unit = reader.readLine();
                    Ingredient i = new Ingredient(myFood, amt, unit);
                    ings.add(i);
                } catch (IOException e) {
                    System.out.println("Error reading input.");
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }

            } else {
                System.out.println("Invalid command.");
            }

            System.out.println("Enter 'add' to create ingredient, 'done' to move on.");

            try {
                command = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }

        return ings;
    }

    public List<String> addSteps(BufferedReader reader) {
        System.out.println("Steps. Enter 'add' to add a step or 'done' when complete.");

        String command = "";
        List<String> steps = new ArrayList<>();

        try {
            command = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        while (!command.equals("done")) {
            if (command.equals("add")){
                try {
                    System.out.println("Enter step details.");
                    String step = reader.readLine();
                    steps.add(step);
                } catch (IOException e) {
                    System.out.println("Error reading input.");
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }

            } else {
                System.out.println("Invalid command.");
            }

            System.out.println("Enter 'add' to create step, 'done' to move on.");

            try {
                command = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }

        return steps;
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
