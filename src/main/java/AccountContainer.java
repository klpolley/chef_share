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
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
    }

    public void updatePassword(String username, String newPassword) {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
    }

    public void updateBiography(String username, String newBio) {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
    }

    public String getUserBio(String username) {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
        return accounts.get(username).getBiography();
    }

    public void login(String username, String password) {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
    }

    public void logout(String username) {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
    }

    public boolean confirmCredentials(String username, String password) {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
        return false;
    }

    public boolean accountExists(String username) {
        return accounts.containsKey(username);
    }

    public Account getAccount(String username) {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
        return accounts.get(username);
    }

    public Account getCurrentAccount() {
        return null;
    }




}