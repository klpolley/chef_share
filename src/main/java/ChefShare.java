public class ChefShare {

    private AccountContainer accounts;
    private Library foods;
    private TagLib tags;

    public ChefShare() {
        accounts = new AccountContainer();
        foods = new Library();
        tags = new TagLib();
    }

    public void login(String username, String password) throws IllegalArgumentException, IllegalStateException {
        accounts.login(username, password);
    }

    public Account getCurrentUser() {
        return accounts.getCurrentAccount();
    }

    public void addAccount(String user, String pass, String bio) throws IllegalArgumentException {
        accounts.createAccount(user, pass, bio);
    }

}
