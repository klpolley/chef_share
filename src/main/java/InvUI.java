import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

public class InvUI {

    private ChefShare app;

    public InvUI(ChefShare appIn){
        app = appIn;
    }

    public void InvMenu(BufferedReader reader){
        System.out.println("Inventory Menu");
        System.out.println("'Back' to return");
        System.out.println("Enter 'help' to view available commands.");

        Inventory current = app.getCurrentUser().getInventory();

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
            else if(command.equals("view"))
                view(current);
            else if (command.equals("add"))
                add(reader, current);
            else if (command.equals("remove"))
                remove(reader, current);
            else if (command.equals("get"))
                get(reader, current);
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
        System.out.println("Available Commands: ");
        System.out.println("Help");
        System.out.println("View");
        System.out.println("Add");
        System.out.println("Remove");
        System.out.println("Get");
    }

    private void view(Inventory current){
        System.out.println(current.toString());
    }
    private void add(BufferedReader reader, Inventory current){
        System.out.println("Would you like to add to ingredient already in Inventory? (y/n)");
        String command = "";
        try {
            command = reader.readLine().toLowerCase();
        } catch (IOException e) {
            System.out.println("Error reading input. Returning to Inventory Menu");
            return;
        }
        Food f = null;
        if(command.equals("y")) {
            System.out.println("Please Enter the name of the ingredient you wish to add to: ");
            try {
                command = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input. Returning to Inventory Menu");
                return;
            }
            try {
                f = current.getIngredient(command).getFood();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + "\n Returning to Inventory Menu.");
                return;
            }
        }

        else if(command.equals("n")) {
            boolean loop = true;
            while (loop) {
                loop = false;
                System.out.println("Please Enter the name of the ingredient you wish to add or done: ");
                try {
                    command = reader.readLine();
                } catch (IOException e) {
                    System.out.println("Error reading input.");
                    loop = true;
                }
                if (command.equalsIgnoreCase("done"))
                    return;
                if (!loop) {
                    f = app.getFood(command);
                    if (f == null) {
                        System.out.println("No food with said name. Please add food in the food menu or input different name.");
                        loop = true;
                    }
                }
            }
        }
        else{
            System.out.println("Invalid Input Returning To Inventory Menu.");
            return;
        }

        boolean loop = true;
        double amt = 0.0;
        String unit = "";
        while(loop){
            loop = false;
            System.out.println("Please enter the unit of the amount you wish to add or done:");
            try {
                command = reader.readLine();
            } catch (IOException e) {
                System.out.println("Error reading input.");
                loop = true;
            }
            if(command.equalsIgnoreCase("done")) return;
            unit = command;
            if(!Ingredient.validUnit(unit)){
                System.out.println("Invalid Unit");
                loop = true;
            }
            if(!loop){
                System.out.println("Please enter the amount you wish to add or done:");
                try {
                    command = reader.readLine();
                    if(command.equalsIgnoreCase("done")) return;
                    amt = Double.parseDouble(command);
                } catch (IOException e) {
                    System.out.println("Error reading input.");
                    loop = true;
                } catch (NumberFormatException e){
                    System.out.println("Error Converting to Double");
                    loop = true;
                }
                if(!loop){
                    try{
                        current.addIngredient(new Ingredient(f, amt, unit));
                        loop = false;
                    } catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                        loop = true;
                    }

                }
            }
        }
    }
    private void remove(BufferedReader reader, Inventory current ){
        System.out.println("Would you like to move recipe's worth of ingredient? (y/n)");
        String command = "";
        try {
            command = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input. Returning to Inventory Menu");
            return;
        }
        if(command.equalsIgnoreCase("y")){
            Recipe r = null;
            System.out.println("Please enter the name of the recipe that you want to remove:");
            try {
                command = reader.readLine();

            } catch (IOException e) {
                System.out.println("Error reading input. Returning to Inventory Menu");
                return;
            }
            r = app.getRecipeByName(command);
            if(r == null){
                System.out.println("Invalid Name. Returning to Inventory Menu.");
                return;
            }
            boolean complete = false;
            Iterator<Ingredient> i = r.getIngredients().iterator();
            while(i.hasNext()){
                Ingredient curr = i.next();
                complete = current.haveIngredient(curr);
                if(!complete)
                    break;
            }
            if(!complete){
                System.out.println("You don't have all the necessary ingredients.");
                System.out.println("Would you like to remove as much as you can? (y/n)");
                try {
                    command = reader.readLine();
                } catch (IOException e) {
                    System.out.println("Error reading input. Returning to Inventory Menu");
                    return;
                }
                if(command.equalsIgnoreCase("y")){
                    i = r.getIngredients().iterator();
                    while(i.hasNext()){
                        Ingredient curr = i.next();
                        try{
                            current.removeIngredient(curr.getName(), Math.max(curr.getAmount(), current.getIngredient(curr.getName()).getAmount()), curr.getUnit());
                        }
                        catch(IllegalArgumentException e){}
                    }
                }
                else{
                    System.out.println("Returning to Inventory Menu.");
                    return;
                }
            }
            else{
                i = r.getIngredients().iterator();
                while(i.hasNext()){
                    Ingredient curr = i.next();
                    try{
                        current.removeIngredient(curr.getName(), curr.getAmount(), curr.getUnit());
                    }
                    catch(IllegalArgumentException e){}
                }
            }
        }
        else if(command.equalsIgnoreCase("n")){
            System.out.println("Please Enter the Name of the ingredient you wish to remove or done:");
            try {
                command = reader.readLine();
                current.getIngredient(command);
            } catch (IOException e) {
                System.out.println("Error reading input. Returning to Inventory Menu");
                return;
            } catch(IllegalArgumentException e){
                System.out.println("No such Ingredient. Returning to Inventory Menu.");
            }
            String name = command;
            String unit;
            boolean loop = true;
            while (loop) {
                loop = false;
                System.out.println("Please Enter the unit of the amount you wish to remove or done");
                try {
                    command = reader.readLine();
                } catch (IOException e) {
                    System.out.println("Error reading input");
                    loop = true;
                }
                if(command.equalsIgnoreCase("done")) return;
                unit = command;
                if(!Ingredient.validUnit(command)){
                    System.out.println("Invalid Units");
                    loop = true;
                }
                if(!loop){
                    System.out.println("Please Enter the amount you wish to remove or done");
                    double amt = 0.0;
                    try {
                        command = reader.readLine();
                        if(command.equalsIgnoreCase("done"))return;
                        amt = Double.parseDouble(command);
                    } catch (IOException e) {
                        System.out.println("Error reading input");
                        loop = true;
                    } catch (NumberFormatException e){
                        System.out.println("Could not convert to number.");
                        loop = true;
                    }
                    if(!loop){
                        try{
                            current.removeIngredient(name, amt, unit);
                            loop = false;
                        }
                        catch(IllegalArgumentException e){
                            System.out.println(e.getMessage());
                            System.out.println("Returning to Inventory Menu");
                            return;
                        }
                    }
                }
            }
        }

    }
    private void get(BufferedReader reader, Inventory current){
        String command = "";
        System.out.println("Please enter the name of the ingredient you want to get:");
        try {
            command = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input. Returning to Inventory Menu");
            return;
        }
        String name = command;
        System.out.println("Please enter the unit you want to see this ingredient in or done:");
        try {
            command = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error reading input. Returning to Inventory Menu");
            return;
        }
        if(command.equalsIgnoreCase("done")) return;
        if(!Ingredient.validUnit(command)){
            System.out.println("Invalid Unit. Returning to Inventory Menu");
            return;
        }
        Ingredient i = null;
        try{
            i = current.getIngredient(name, command);
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage() + "\nReturning to Inventory Menu.");
            return;
        }
        System.out.println(i.toString());
    }
}
