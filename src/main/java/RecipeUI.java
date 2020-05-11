import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipeUI {

    private ChefShare app;
    private FoodUI foodUI;

    public RecipeUI(ChefShare app) {
        this.app = app;
        this.foodUI = new FoodUI(app);
    }

    public void recipeView(BufferedReader reader) {
        System.out.println("Recipes: add, edit, delete, view, search, rename. 'back' to return.");

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
                editMyRecipes(reader);
            }
            else if (command.equals("delete")) {
                deleteRecipe(reader);
            }
            else if(command.equals("rename")){
                renameRecipe(reader);
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
        System.out.println("Chef Share:");
    }

    public void editMyRecipes(BufferedReader reader) {
        System.out.println("Your Recipes");

        List<Recipe> myRecipes = app.getCurrentUserRecipes();
        System.out.println(app.printRecipeList(myRecipes));

        editRecipeSelector(reader, myRecipes);
    }

    public void editRecipeSelector(BufferedReader reader, List<Recipe> recipeList) {
        System.out.println("Enter recipe number to edit. Enter 'done' to leave search.");

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
                editRecipe(reader, recipeList.get(num-1));
                return;
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter a number from the list.");
            }

            System.out.println("Enter recipe number to edit. Enter 'done' to leave search.");

            try {
                select = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
    }

    private void editRecipe(BufferedReader reader, Recipe r) {
        System.out.println("Enter add step, edit step, remove step, add ingredient, edit ingredient, remove ingredient, or done.");

        String select = "";

        try {
            select = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        while (!select.equals("done")) {
            if(select.equals("add step")){
                String step, locStr;
                int loc;
                try {
                    System.out.println("Please enter the step you would like to add:");
                    step = reader.readLine();
                    System.out.println("Please enter the location to add this step, or leave blank to add onto the end.");
                    locStr = reader.readLine();
                    if(!locStr.equals("")){
                        loc = Integer.parseInt(locStr);
                        app.getCurrentUser().addStep(r.getName(), step, loc);
                    }
                    else{
                        app.getCurrentUser().addStep(r.getName(), step);
                    }
                    System.out.println("Step added");

                }
                catch (IOException e){
                    System.out.println("Error reading input.");
                }
                catch (NumberFormatException e){
                    System.out.println("Not a valid number!");
                }
                catch (IllegalArgumentException e){
                    System.out.println("Not a valid location to add a step!");
                }

            }
            else if(select.equals("edit step")){
                int loc = selectStep(reader, r);
                String newStep = "";

                if(loc >=0 ) {
                    try {
                        System.out.println("Please enter the new step");
                        newStep = reader.readLine();
                    } catch (IOException e) {
                        System.out.println("Error reading input.");
                    }

                    app.getRecipeByName(r.getName()).editStep(loc, newStep);
                    System.out.println("Step updated!");
                }
            }
            else if(select.equals("remove step")){
                int loc = selectStep(reader, r);
                if(loc >= 0) {
                    app.getRecipeByName(r.getName()).removeStep(loc);
                    System.out.println("Step removed!");
                }
            }
            else if(select.equals("add ingredient")){
                List<Ingredient> ingredientsToAdd = addIngredients(reader);
                for(int i = 0; i < ingredientsToAdd.size(); i++){
                    app.getCurrentUser().addIngredient(r.getName(), ingredientsToAdd.get(i));
                }
                System.out.println("Ingredient(s) added!");
            }
            else if(select.equals("edit ingredient")){
                int ingredientNum = selectIngredient(reader, r);

                if(ingredientNum >= 0) {
                    Ingredient ingredient = null;
                    String amtIn, unit;
                    double amt;
                    try {
                        System.out.println("Please enter a new quantity for this ingredient");
                        amtIn = reader.readLine();
                        amt = Double.parseDouble(amtIn);
                        System.out.println("Please enter a new unit for this ingredient");
                        unit = reader.readLine();
                        ingredient = new Ingredient(app.getCurrentUser().getIngredient(r.getName(), ingredientNum).getFood(), amt, unit);
                    } catch (IOException e) {
                        System.out.println("Error reading input.");
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number");
                    } catch (Exception e) {
                        System.out.println("Other Error:  " + e.getMessage());
                    }

                    if (ingredient != null) {
                        app.getCurrentUser().editIngredient(r.getName(), ingredientNum, ingredient);
                        System.out.println("Ingredient updated!");
                    }
                }

            }
            else if(select.equals("remove ingredient")){
                int loc = selectIngredient(reader, r);
                if(loc >= 0) {
                    Ingredient removed = app.getRecipeByName(r.getName()).returnIngredient(loc);
                    app.getRecipeByName(r.getName()).removeIngredient(loc);
                    System.out.println("Ingredient removed!");
                }
            }

            System.out.println("Enter add step, edit step, remove step, add ingredient, edit ingredient, remove ingredient, or done.");

            try {
                select = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
    }

    private int selectIngredient(BufferedReader reader, Recipe recipe){
        System.out.println("Enter ingredient number to select. Enter 'done' to cancel.");
        Collection<Ingredient> ingredientList = recipe.getIngredients();
        for(int i = 0; i < ingredientList.size(); i++){
            System.out.println(i+1 + ": " + ((List)ingredientList).get(i));
        }

        String select = "";

        try {
            select = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        while (!select.equals("done")) {

            try {
                int num = Integer.parseInt(select);
                if(num > 0 && num <= ingredientList.size()){
                    return num;
                }
                else{
                    System.out.println("Please enter a number from the list");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
            }

            System.out.println("Enter ingredient number to select. Enter 'done' to cancel.");

            try {
                select = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
        return -1;
    }

    private int selectStep(BufferedReader reader, Recipe recipe){
        Collection<String> stepList = recipe.getSteps();
        for(int i = 0; i < stepList.size(); i++){
            System.out.println(i+1 + ": " + ((List)stepList).get(i));
        }
        System.out.println("Enter step number to select. Enter 'done' to cancel.");

        String select = "";

        try {
            select = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        while (!select.equals("done")) {

            try {
                int num = Integer.parseInt(select);
                if(num <= stepList.size() && num > 0)
                    return num;
                else {
                    System.out.println("Please enter a number from the list.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer.");
            }

            System.out.println("Enter step number to select. Enter 'done' to cancel.");

            try {
                select = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
        return -1;
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

    void renameRecipe(BufferedReader reader){
        System.out.println("Please Enter the name of the recipe you want to change: ");

        String command = "";
        List<Recipe> r = null;
        Recipe r2 = null;

        try {
            command = reader.readLine();
            r = app.getCurrentUser().getRecipeList();
            if (r.size() == 0){
                System.out.println("No Recipes to change the name of. Returning to Recipe menu");
                return;
            }
            for(Recipe x: r){
                if(x.getName().equals(command))
                    r2 = x;
            }

        } catch (IOException e) {
            System.out.println("Error reading input.");
        }
        if (r2 == null){
            System.out.println("No Recipe with that name. Returning to Recipe menu");
            return;
        }
        System.out.println("Please enter the new name for the recipeL: ");
        try {
            command = reader.readLine();

        } catch (IOException e) {
            System.out.println("Error reading input.");
        }
        r2.setName(command);
    }

}
