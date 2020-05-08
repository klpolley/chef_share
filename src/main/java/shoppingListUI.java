import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Collection;

public class shoppingListUI {

    private ChefShare app;

    public shoppingListUI(ChefShare app) {
        this.app = app;
    }

    public void shoppingListView(BufferedReader reader){
        System.out.println("Shopping List: would you like to add, remove, or view your list? Enter 'back' to return. ");

        String command = "";

        try {
            command = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        while (!command.equals("back")) {
            if (command.equals("add")) {
                addToList(reader);
            }
            else if (command.equals("view")) {
                viewMyList(reader);
            } else if (command.equals("remove")) {
                removeFromList(reader);
            }
            else {
                System.out.println("Invalid command.");
            }

            System.out.println("Shopping List: would you like to add, remove, or view your list? Enter 'back' to return. ");

            try {
                command = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
    }

    public void addToList(BufferedReader reader) {
        System.out.println("Would you like to add 'ingredient' or from a 'recipe'? ");

        String command = "";

        try {
            command = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        if (command.equals("ingredient")) {
            System.out.println("Please enter your ingredient name: ");

            String name = "";

            try {
                name = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }

            System.out.println("Enter number of calories: ");

            String calS = "";

            try {
                calS = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
            int cal = Integer.parseInt(calS);

            Food f = new Food(name, cal);

            System.out.println("Enter amount of ingredient: ");

            String amountS = "";

            try {
                amountS = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
            double amount = Double.parseDouble(amountS);

            System.out.println("Enter units of measurement: ");

            String unit = "";

            try {
                unit = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }

            Ingredient i = new Ingredient(f, amount, unit);

            Account me = app.getCurrentUser();
            me.addToShoppingList(i);

            System.out.println("Ingredient has been added to your list.");

        } else if (command.equals("recipe")) {
            System.out.println("Enter the full name of the recipe to add from (case sensitive).");

            String recipeName = "";

            try {
                recipeName = reader.readLine();
                Account me = app.getCurrentUser();

                List<Recipe> recipe = app.getRecipeListByNameSearch(recipeName);
                if (recipe.size() == 0) {
                    System.out.println("Invalid recipe name.");
                } else {
                    int i = 0;
                    boolean found = false;
                    while (found == false){
                        String recipeCheck = recipe.get(i).getName();
                        System.out.println("Is this the recipe you're looking for - " + recipeCheck + " - y or n?");

                        String confirm = "";

                        try {
                            confirm = reader.readLine();
                        } catch (IOException e) {
                            System.out.println("Error reading input.");
                        }
                        if (confirm.equals("y")){
                            found = true;
                        }else{
                            i++;
                            found = false;
                        }
                    }
                    Recipe r = recipe.get(i);

                    me.addRecipeToShoppingList(r);

                    System.out.println("Ingredients have been added to your list.");
                }
                } catch(IOException e){
                    System.out.println("Error reading input.");
                }
            }
        }


    public void viewMyList(BufferedReader reader){
        System.out.println("Your current shopping list: ");

        shoppingList myList = app.getCurrentUserShoppingList();
        System.out.println(app.printShoppingList(myList));
    }

    public void removeFromList(BufferedReader reader){

        System.out.println("Enter the name of the ingredient you would like to remove: ");

        String name = "";

        try {
            name = reader.readLine();
            Account me = app.getCurrentUser();

            int index = app.getCurrentUserIngredientIndex(name);
            if (index == -1){
                System.out.println("This ingredient is not in your list.");
            }else{
                double amount = app.getCurrUserIngAmount(index);
                String unit = app.getCurrUserIngUnit(index);

                System.out.println("You currently have " + amount  + unit + ".");
                System.out.println("How much would you like to remove (enter a number): ");

                String removeAmountS = "";
                try {
                    removeAmountS = reader.readLine();
                } catch (IOException e) {
                    System.out.println("Error reading input.");
                }

                double removeAmount = Double.parseDouble(removeAmountS);

                if (removeAmount > amount){
                    System.out.println("Invalid amount.");
                }else{
                    me.removeFromShoppingList(name,removeAmount,unit);
                    System.out.println("Desired amount has been removed.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

    }

}

