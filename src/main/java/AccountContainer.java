import java.util.HashMap;
import java.util.Map;

public class AccountContainer {

    private Map<String, Account> accounts = new HashMap<>();

    private Account currentAccount = null;

    public void createAccount(String username, String password, String bio) throws IllegalArgumentException {
        if (accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username already exists");
        Account acct = new Account(username, password, bio);
        accounts.put(username, acct);
    }

    public void removeAccount(String username) {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
        accounts.remove(username);
    }

    public void updateUsername(String username, String newUsername) {

    }

    public void updatePassword(String username, String newPassword) {

    }

    public void updateBiography(String username, String newBio) {

    }

    public String getUserBio(String username) {
        return null;
    }

    public void login(String username, String password) {

    }

    public void logout(String username) {

    }

    public boolean confirmCredentials(String username, String password) {
        return false;
    }

    public boolean accountExists(String username) {
        return accounts.containsKey(username);
    }

    public Account getAccount(String username) {
        return null;
    }

    public Account getCurrentAccount() {
        return null;
    }




}