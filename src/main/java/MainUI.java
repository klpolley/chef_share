import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MainUI {

    private ChefShare app;
    private RecipeUI recipeUI;
    private shoppingListUI shoppingListUI;

    public MainUI(ChefShare app) {
        this.app = app;
        recipeUI = new RecipeUI(app);
        shoppingListUI = new shoppingListUI(app);
    }

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        welcome(reader);
    }

    private void exit() {
        System.out.println("Thanks for using Chef Share!");
        System.exit(0);
    }

    public void welcome(BufferedReader reader) {

        System.out.println("Welcome to Chef Share!");
        System.out.println("Enter 'help' to view available commands.'");

        String command = "";

        try {
            command = reader.readLine().toLowerCase();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        while (!command.equals("exit")) {

            if (command.equals("help")) {
                System.out.println("Available Commands:");
                System.out.println("login");
                //System.out.println("create account");
                System.out.println("exit");
            }
            else if (command.equals("login")) {
                login(reader);
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

        exit();

    }

    public void login(BufferedReader reader) {
        try {
            System.out.println("Enter your username.");
            String user = reader.readLine();
            System.out.println("Enter your password.");
            String password = reader.readLine();

            app.login(user, password);

        } catch (IOException e) {
            System.out.println("Error reading input.");
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect username or password.");
            return;
        } catch (IllegalStateException e) {
            System.out.println("How did you even get here?");
            return;
        }

        accountHome(reader);
    }

    public void logout(BufferedReader reader) {
        app.logout();
        welcome(reader);
    }

    public void accountHome(BufferedReader reader) {
        System.out.println("Welcome " + app.getCurrentUser().getUsername() + "!");
        System.out.println("Enter command. Enter 'help' to view available commands.");

        String command = "";

        try {
            command = reader.readLine().toLowerCase();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        while (!command.equals("logout")) {

            if (command.equals("help")) {
                System.out.println("Available Commands:");
                System.out.println("recipes");
                System.out.println("inventory");
                System.out.println("shopping list");
                System.out.println("food");
                System.out.println("logout");
            }
            else if (command.equals("recipes")) {
                recipeUI.recipeView(reader);
            }
            else if (command.equals("inventory")) {
                inventoryView(reader);
            }
            else if (command.equals("shopping list")) {
                shoppingListUI.shoppingListView(reader);
            }
            else if (command.equals("food")) {
                foodView(reader);
            }
            else {
                System.out.println("Invalid command.");
            }

            System.out.println("Enter command. Enter 'help' to view available commands.");

            try {
                command = reader.readLine().toLowerCase();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }

        logout(reader);

    }

    public void inventoryView(BufferedReader reader) {
        System.out.println("Inventory Reached");
    }

    public void shoppingListView(BufferedReader reader) {
        System.out.println("Shopping List Reached");
    }

    public void foodView(BufferedReader reader) {
        FoodUI f = new FoodUI(app);
        f.foodMenu(reader);
    }


}
