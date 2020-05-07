import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

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

    public void addToList(BufferedReader reader){
        System.out.println("Would you like to add 'ingredient' or from a 'recipe'? ");


    }

    public void viewMyList(BufferedReader reader){
        System.out.println("Your current shopping list: ");

        shoppingList myList = app.getCurrentUserShoppingList();
        System.out.println(app.printShoppingList(myList));
    }

    public void removeFromList(BufferedReader reader){

    }

}

