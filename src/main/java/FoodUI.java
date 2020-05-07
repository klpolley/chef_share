import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;

public class FoodUI {

    private ChefShare app;

    public FoodUI(ChefShare app) { this.app = app; }

    public void foodMenu(BufferedReader reader){  //Called from MainUI when the food menu is pulled up
        System.out.println("Food Menu");
        System.out.println("Enter 'help' to view available commands.'");

        String command = "";

        try {
            command = reader.readLine().toLowerCase();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        while (!command.equals("back")) {
            if (command.equals("help")) {
                help();
            }
            else if (command.equals("add")) {
                add(reader);
            }
            else if (command.equals("view")) {
                view(reader);
            }
            else if (command.equals("edit")) {
                edit(reader);
            }
            else {
                System.out.println("Invalid command.");
            }

            System.out.println("Enter command. Enter 'help' to view available commands.");

            try {
                command = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
        System.out.println("Chef Share:");
    }

    private void help(){
        System.out.println("Food Menu:");
        System.out.println("help");
        System.out.println("add");
        System.out.println("view");
        System.out.println("edit");
        System.out.println("back");
    }

    private Food add(BufferedReader reader){
        try {
            System.out.println("Enter the name of the food.");
            String name = reader.readLine();
            System.out.println("Enter the number of calories per 100 grams.");
            double calories = Double.parseDouble(reader.readLine());
            System.out.println("Enter the density in grams per cubic centimeter.");
            double density = Double.parseDouble(reader.readLine());

            Food f = new Food(name, calories, density);
            app.addFood(f);
            return f;

        } catch (IOException e) {
            System.out.println("Error reading input.");
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Invalid Calorie or Density value.");
            return null;
        } catch (IllegalArgumentException e) {
            System.out.println("Error:  " + e.getMessage());
            return null;
        } catch (IllegalStateException e) {
            System.out.println("How did you even get here?");
            return null;
        }
    }

    private void view(BufferedReader reader){
        Food f = search(reader);
        if(f != null)
            System.out.println(f.details());
    }

    private void edit(BufferedReader reader){
        try {
            Food foodToEdit = search(reader);
            System.out.println("Please enter a new calorie value for \"" + foodToEdit.getName() + "\"");
            double calories = Double.parseDouble(reader.readLine());
            System.out.println("Please enter a new density value for \"" + foodToEdit.getName() + "\"");
            double density = Double.parseDouble(reader.readLine());

            foodToEdit.setCalories(calories);
            foodToEdit.setDensity(density);
            app.addFood(foodToEdit);
        } catch (IOException e) {
            System.out.println("Error reading input.");
            return;
        } catch (NumberFormatException e) {
            System.out.println("Invalid Calorie or Density value.");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Error:  " + e.getMessage());
            return;
        } catch (IllegalStateException e) {
            System.out.println("How did you even get here?");
            return;
        }
    }

    private Food search(BufferedReader reader){
        System.out.println("To find your food, please enter some or all of its name, or enter \"list all food\" to view all food, or enter back to cancel");

        String foodName = "";

        try {
            foodName = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        while (!foodName.equals("back")) {
            if (app.containsFood(foodName)) {
                return app.getFood(foodName);
            }
            else if(foodName.equalsIgnoreCase("list all food")){
                System.out.println(app.listAllFood());
            }
            else {
                System.out.println("No exact match found, here is a list of all the food containing that string:");
                printFoods(app.searchFood(foodName));
            }

            System.out.println("To find your food, please enter some or all of its name, or enter \"list all food\" to view all food, or enter back to cancel");

            try {
                foodName = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
        return null;
    }

    private void printFoods(ArrayList<Food> a){
        for (Food f:
             a) {
            System.out.println(f.getName());
        }
    }

    public Food foodTool(BufferedReader reader){
        System.out.println("Please enter \"create\" to create a new food, or \"select\" to select an existing food");

        String command = "";
        Food result = null;

        try {
            command = reader.readLine().toLowerCase();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        while (result == null) {
            if (command.equals("create")) {
                result = add(reader);
            }
            else if (command.equals("select")) {
                result = search(reader);
            }
            else {
                System.out.println("Invalid command.");
            }

            System.out.println("Please enter \"create\" to create a new food, or \"select\" to select an existing food");

            try {
                command = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }

        return result;
    }

}
