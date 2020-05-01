import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            /*else if (command.equals("delete")) {
                delete(reader);
            }*/
            else if (command.equals("view")) {
                view(reader);
            }
            /*else if (command.equals("edit")) {
                edit(reader);
            }*/
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
    }

    private void help(){
        System.out.println("Food Menu:");
        System.out.println("help");
        System.out.println("add");
        //System.out.println("delete");
        System.out.println("view");
        //System.out.println("edit");
    }

    private void add(BufferedReader reader){
        try {
            System.out.println("Enter the name of the food.");
            String name = reader.readLine();
            System.out.println("Enter the number of calories per 100 grams.");
            double calories = Double.parseDouble(reader.readLine());
            System.out.println("Enter the density in grams per cubic centimeter.");
            double density = Double.parseDouble(reader.readLine());

            Food f = new Food(name, calories, density);
            app.addFood(f);

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

    private void delete(BufferedReader reader){
        //No backend function for delete yet
    }

    private void view(BufferedReader reader){
        Food f = search(reader);
        if(f != null)
            System.out.println(f.details());
    }

    private void edit(BufferedReader reader){
        //No backend function for edit, and no way to replicate it with deleting old one and adding a new one
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

}
