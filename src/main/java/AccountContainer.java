import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class AccountContainer {

    private Map<String, Account> accounts = new HashMap<>();

    private Account currentAccount = null;

    public void createAccount(String username, String password, String bio) throws IllegalArgumentException {
        if (currentAccount != null) throw new IllegalStateException("Must be logged out to create new account");
        if (accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username already exists");
        Account acct = new Account(username, password, bio);
        accounts.put(username, acct);
    }

    public void removeAccount(String username) throws IllegalArgumentException, IllegalStateException {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
        if (currentAccount == null || !username.equals(currentAccount.getUsername())) throw new IllegalStateException("Must log in to remove account");
        accounts.remove(username);
    }

    public void updateUsername(String username, String newUsername) throws IllegalArgumentException, IllegalStateException {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
        if (currentAccount == null || !username.equals(currentAccount.getUsername())) throw new IllegalStateException("Must be logged in to update");
        if (accounts.containsKey(newUsername)) throw new IllegalArgumentException("Account with that username already exists");
        Account acct = accounts.get(username);
        acct.setUsername(newUsername);
        accounts.remove(username);
        accounts.put(newUsername, acct);
    }

    public void updatePassword(String username, String newPassword) throws IllegalArgumentException, IllegalStateException {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
        if (currentAccount == null || !username.equals(currentAccount.getUsername())) throw new IllegalStateException("Must be logged in to update");
        Account acct = accounts.get(username);
        acct.setPassword(newPassword);
    }

    public void updateBiography(String username, String newBio) throws IllegalArgumentException, IllegalStateException {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
        if (currentAccount == null || !username.equals(currentAccount.getUsername())) throw new IllegalStateException("Must be logged in to update");
        Account acct = accounts.get(username);
        acct.setBiography(newBio);
    }

    public String getUserBio(String username) {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
        return accounts.get(username).getBiography();
    }

    public boolean accountExists(String username) {
        return accounts.containsKey(username);
    }

    public Account getAccount(String username) throws IllegalArgumentException {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
        return accounts.get(username);
    }

    public void login(String username, String password) throws IllegalArgumentException, IllegalStateException {
        if (currentAccount != null) throw new IllegalStateException("Account already loged in");
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
        Account acct = accounts.get(username);
        if (!acct.confirmPassword(password)) throw new IllegalArgumentException("Password incorrect");
        currentAccount = acct;
    }

    public void logout() throws IllegalStateException {
        if (currentAccount ==  null) throw new IllegalStateException("No account logged in");
        currentAccount = null;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    //returns list of all recipe objects from all accounts
    //add methods for getting filtered lists with only some recipes
    //other methods take list parameters so you can perform same functions
    //on different lists, not just all recipes
    public List<Recipe> getAllRecipes() {
        return null;
    }

    //get recipes as "tuples" with name and user - mostly for testing purposes
    public String[][] getRecipeListTuples(List<Recipe> recipes) {
        return null;
    }

    //returns string that can be printed for user to select from
    //each recipe will have the name, account username, and a number for selection purposes
    public String getRecipeListString(List<Recipe> recipes) {
        return null;
    }

    public String selectRecipeToPrint(int selection, List<Recipe> allRecipes) {
        return null;
    }

}