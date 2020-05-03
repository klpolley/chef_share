import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MainUI {

    private ChefShare app;

    public MainUI(ChefShare app) {
        this.app = app;
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
            command = reader.readLine();
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
        welcome(reader);
    }

    public void accountHome(BufferedReader reader) {
        System.out.println("Welcome " + app.getCurrentUser().getUsername() + "!");
        System.out.println("Enter command. Enter 'help' to view available commands.");

        String command = "";

        try {
            command = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        while (!command.equals("logout")) {

            if (command.equals("help")) {
                System.out.println("Available Commands:");
                System.out.println("recipes");
                System.out.println("inventory");
                System.out.println("shopping list");
                System.out.println("logout");
            }
            else if (command.equals("recipes")) {
                recipeView(reader);
            }
            else if (command.equals("inventory")) {
                inventoryView(reader);
            }
            else if (command.equals("shopping list")) {
                shoppingListView(reader);
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

        logout(reader);

    }

    public void recipeView(BufferedReader reader) {
        System.out.println("Recipes: add, edit, delete, view, search.");

        String command = "";

        try {
            command = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        while (!command.equals("back")) {

            if (command.equals("search")) {
                recipeView(reader);
            }
            else {
                System.out.println("Invalid command.");
            }

            System.out.println("Recipes: add, edit, delete, view, search.");

            try {
                command = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }

        accountHome(reader);
    }

    public void searchRecipes(BufferedReader reader) {
        System.out.println("Enter search term or recipe name.");

        String search = "";

        try {
            search = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        List<Recipe> matches = app.getRecipeListByNameSearch(search);

        System.out.println(app.printRecipeList(matches));

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
                app.printRecipeSelection(num, matches);
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

        recipeView(reader);
    }

    public void inventoryView(BufferedReader reader) {
        System.out.println("Inventory Reached");
    }

    public void shoppingListView(BufferedReader reader) {
        System.out.println("Shopping List Reached");
    }


}
