import java.util.HashMap;
import java.util.Map;

public class AccountContainer {

    private Map<String, Account> accounts = new HashMap<>();

    private Account currentAccount = null;

    public void createAccount(String username, String password, String bio) {

    }

    public void removeAccount(String username) {

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
        return false;
    }

    public Account getCurrentAccount() {
        return null;
    }




}