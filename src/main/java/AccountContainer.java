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
        if (accounts.containsKey(newUsername)) throw new IllegalArgumentException("Account with that username already exists");
        Account acct = accounts.get(username);
        acct.setUsername(newUsername);
        accounts.remove(username);
        accounts.put(newUsername, acct);
    }

    public void updatePassword(String username, String newPassword) {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
        Account acct = accounts.get(username);
        acct.setPassword(newPassword);
    }

    public void updateBiography(String username, String newBio) {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
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

    public Account getAccount(String username) {
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
        return accounts.get(username);
    }

    public void login(String username, String password) {
        if (currentAccount != null) throw new IllegalStateException("Account already logged in");
        if (!accounts.containsKey(username)) throw new IllegalArgumentException("Account with that username does not exist");
        Account acct = accounts.get(username);
        if (!acct.confirmPassword(password)) throw new IllegalArgumentException("Password incorrect");
        currentAccount = acct;
    }

    public void logout() {
        if (currentAccount ==  null) throw new IllegalStateException("No account logged in");
        currentAccount = null;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }
}