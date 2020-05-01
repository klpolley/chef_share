import java.util.ArrayList;

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

    public void addFood(Food f){
        foods.addFood(f);
    }

    public boolean containsFood(String s){
        return foods.isFoodPresent(s);
    }

    public Food getFood(String s){
        return foods.getFood(s);
    }

    public ArrayList<Food> searchFood(String s){
        return foods.search(s);
    }

    public String listAllFood(){
        return foods.listAllFood();
    }

}
