import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class AccountContainerTest {

    @Test
    void createAccountTest() {
        AccountContainer accounts = new AccountContainer();

        //should be all good
        accounts.createAccount("username", "password", "gourmet chef");
        assertTrue(accounts.accountExists("username"));
        accounts.createAccount("user12345", "p.a!s#w%o^r&d", "secure chef");
        assertTrue(accounts.accountExists("user12345"));
        accounts.createAccount("1broccoli", "get1those1greens", "");
        assertTrue(accounts.accountExists("username"));
        assertTrue(accounts.accountExists("user12345"));
        assertTrue(accounts.accountExists("1broccoli"));

        //same password/bio is fine
        accounts.createAccount("newuser", "password", "haha");
        assertTrue(accounts.accountExists("newuser"));
        accounts.createAccount("anothernewuser", "suchanewuser", "gourmet chef");
        assertTrue(accounts.accountExists("newuser"));

        //bad - username already exists
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("username", "password", "gourmet chef"));
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("username", "imsosecure", ""));
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("user12345", "password", "I'm so cool"));
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("1broccoli", "get1those1greens", "I love cake"));

        //bad - invalid username and/or password
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("user", "password", ""));
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("thecoolestuserevertoexist", "password", ""));
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("username", "pass", ""));
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("user", "pass", "Rules are for losers"));

        //can't create account when logged in
        accounts.login("username", "password");
        assertThrows(IllegalStateException.class, ()->accounts.createAccount("newest", "passwordiest",""));
    }

    @Test
    void removeAccountTest() {
        AccountContainer accounts = new AccountContainer();
        accounts.createAccount("username", "password", "gourmet chef");
        accounts.createAccount("user12345", "p.a!s#w%o^r&d", "secure chef");
        accounts.createAccount("1broccoli", "get1those1greens", "");

        //no one logged in- definitely can't do things
        assertThrows(IllegalStateException.class, ()->accounts.removeAccount("user12345"));

        accounts.login("username", "password");

        //remove existing account
        accounts.removeAccount("username");
        assertFalse(accounts.accountExists("username"));
        assertTrue(accounts.accountExists("user12345"));
        assertTrue(accounts.accountExists("1broccoli"));

        //try to remove account that is not in container
        assertThrows(IllegalArgumentException.class, ()->accounts.removeAccount("username"));
        assertThrows(IllegalArgumentException.class, ()->accounts.removeAccount("notreal"));

        //try to remove account not logged in
        assertThrows(IllegalStateException.class, ()->accounts.removeAccount("user12345"));

        accounts.logout();
        accounts.login("user12345", "p.a!s#w%o^r&d");

        accounts.removeAccount("user12345");
        assertFalse(accounts.accountExists("username"));
        assertFalse(accounts.accountExists("user12345"));
        assertTrue(accounts.accountExists("1broccoli"));

        //try to remove account already removed
        assertThrows(IllegalArgumentException.class, ()->accounts.removeAccount("user12345"));

        accounts.logout();
        accounts.login("1broccoli", "get1those1greens");

        accounts.removeAccount("1broccoli");
        assertFalse(accounts.accountExists("username"));
        assertFalse(accounts.accountExists("user12345"));
        assertFalse(accounts.accountExists("1broccoli"));

        //try to remove from empty container
        assertThrows(IllegalArgumentException.class, ()->accounts.removeAccount("1broccoli"));
        assertThrows(IllegalArgumentException.class, ()->accounts.removeAccount("neverexisted"));
    }

    @Test
    void getBioTest() {
        AccountContainer accounts = new AccountContainer();
        accounts.createAccount("username", "password", "gourmet chef");
        accounts.createAccount("user12345", "p.a!s#w%o^r&d", "secure chef");
        accounts.createAccount("1broccoli", "get1those1greens", "");

        assertEquals("gourmet chef", accounts.getUserBio("username"));
        assertEquals("secure chef", accounts.getUserBio("user12345"));
        assertEquals("", accounts.getUserBio("1broccoli"));

        //account that does not exist throws exception
        assertThrows(IllegalArgumentException.class, ()->accounts.getUserBio("nonexistent"));
    }

    @Test
    void updateUserTest() {
        AccountContainer accounts = new AccountContainer();
        accounts.createAccount("username", "password", "gourmet chef");
        accounts.createAccount("user12345", "p.a!s#w%o^r&d", "secure chef");
        accounts.createAccount("1broccoli", "get1those1greens", "");

        accounts.login("username", "password");

        //update username without affecting other accounts
        accounts.updateUsername("username", "newname");
        assertTrue(accounts.accountExists("newname"));
        assertFalse(accounts.accountExists("username"));
        assertEquals("gourmet chef", accounts.getUserBio("newname"));
        assertTrue(accounts.accountExists("user12345"));
        assertTrue(accounts.accountExists("1broccoli"));

        accounts.logout();
        accounts.login("user12345", "p.a!s#w%o^r&d");

        //update username to one that was previously taken
        accounts.updateUsername("user12345", "username");
        assertTrue(accounts.accountExists("username"));
        assertFalse(accounts.accountExists("user12345"));
        assertEquals("secure chef", accounts.getUserBio("username"));
        assertTrue(accounts.accountExists("newname"));
        assertTrue(accounts.accountExists("1broccoli"));

        //try to update username not logged in
        assertThrows(IllegalStateException.class, ()->accounts.updateUsername("newname", "whatever"));

        //try to update username to name that already exists
        assertThrows(IllegalArgumentException.class, ()->accounts.updateUsername("username", "1broccoli"));

        //try to update username of acct that doesn't exist
        assertThrows(IllegalArgumentException.class, ()->accounts.updateUsername("user12345", "thebestuser"));

        //update with bad username
        assertThrows(IllegalArgumentException.class, ()->accounts.updateUsername("username", "user"));
        assertThrows(IllegalArgumentException.class, ()->accounts.updateUsername("username", "?username"));
        assertThrows(IllegalArgumentException.class, ()->accounts.updateUsername("username", "1234!@#$"));
    }

    @Test
    void updatePasswordTest() {
        AccountContainer accounts = new AccountContainer();
        accounts.createAccount("username", "password", "gourmet chef");
        accounts.createAccount("user12345", "p.a!s#w%o^r&d", "secure chef");
        accounts.createAccount("1broccoli", "get1those1greens", "");
        Account acct = accounts.getAccount("username");
        Account acct2 = accounts.getAccount("user12345");
        Account acct3 = accounts.getAccount("1broccoli");

        accounts.login("username", "password");

        //update username without affecting other accounts
        accounts.updatePassword("username", "betterpassword?123");
        assertTrue(acct.confirmPassword("betterpassword?123"));
        assertTrue(acct2.confirmPassword("p.a!s#w%o^r&d"));
        assertTrue(acct3.confirmPassword("get1those1greens"));

        accounts.logout();
        accounts.login("user12345", "p.a!s#w%o^r&d");

        //update password to same as others - works
        accounts.updatePassword("user12345", "get1those1greens");
        assertTrue(acct2.confirmPassword("get1those1greens"));

        //try to update account not logged in
        assertThrows(IllegalStateException.class, ()->accounts.updatePassword("username", "betterpassword?123"));

        //try to update username of acct that doesn't exist
        assertThrows(IllegalArgumentException.class, ()->accounts.updatePassword("fakeuser", "password"));

        //update with bad password
        assertThrows(IllegalArgumentException.class, ()->accounts.updatePassword("user12345", "short!"));
    }

    @Test
    void updateBioTest() {
        AccountContainer accounts = new AccountContainer();
        accounts.createAccount("username", "password", "gourmet chef");
        accounts.createAccount("user12345", "p.a!s#w%o^r&d", "secure chef");
        accounts.createAccount("1broccoli", "get1those1greens", "");

        accounts.login("1broccoli", "get1those1greens");

        //update bio without impacting other accounts
        accounts.updateBiography("1broccoli", "i <3 broccoli");
        assertEquals("i <3 broccoli", accounts.getUserBio("1broccoli"));
        assertEquals("gourmet chef", accounts.getUserBio("username"));
        assertEquals("secure chef", accounts.getUserBio("user12345"));

        accounts.logout();
        accounts.login("user12345", "p.a!s#w%o^r&d");

        //update to empty
        accounts.updateBiography("user12345", "");
        assertEquals("", accounts.getUserBio("user12345"));

        accounts.logout();
        accounts.login("username", "password");

        //update to existing bio (fine)
        accounts.updateBiography("username", "i <3 broccoli");
        assertEquals("i <3 broccoli", accounts.getUserBio("username"));

        //try to update description of acct that doesn't exist
        assertThrows(IllegalArgumentException.class, ()->accounts.updateBiography("fakeuser", "description"));

        //try to update accounts not logged in
        assertThrows(IllegalStateException.class, ()->accounts.updateBiography("user12345", ""));
    }

    @Test
    void loginAndLogoutTest() {
        AccountContainer accounts = new AccountContainer();
        accounts.createAccount("username", "password", "gourmet chef");
        accounts.createAccount("user12345", "p.a!s#w%o^r&d", "secure chef");
        accounts.createAccount("1broccoli", "get1those1greens", "");

        //login to account that does not exist
        assertThrows(IllegalArgumentException.class, ()->accounts.login("fakeuser", "password"));

        //account login with bad password
        assertThrows(IllegalArgumentException.class, ()->accounts.login("username", "notcorrect"));

        //good login
        accounts.login("username", "password");
        assertEquals("username", accounts.getCurrentAccount().getUsername());
        assertEquals("gourmet chef", accounts.getUserBio(accounts.getCurrentAccount().getUsername()));

        //login while account is logged in
        assertThrows(IllegalStateException.class, ()->accounts.login("username", "password"));
        assertThrows(IllegalStateException.class, ()->accounts.login("user12345", "p.a!s#w%o^r&d"));

        //logout
        accounts.logout();
        assertNull(accounts.getCurrentAccount());

        //try to logout without being logged in
        assertThrows(IllegalStateException.class, ()->accounts.logout());

        //log back in
        accounts.login("username", "password");
        assertEquals("username", accounts.getCurrentAccount().getUsername());

        //logout again
        accounts.logout();
        assertNull(accounts.getCurrentAccount());

        //log in with different account
        accounts.login("user12345", "p.a!s#w%o^r&d");
        assertEquals("user12345", accounts.getCurrentAccount().getUsername());

        //same tests again with new account
        assertThrows(IllegalStateException.class, ()->accounts.login("username", "password"));
        assertThrows(IllegalStateException.class, ()->accounts.login("user12345", "p.a!s#w%o^r&d"));
        accounts.logout();
        assertNull(accounts.getCurrentAccount());
        assertThrows(IllegalStateException.class, ()->accounts.logout());
    }


    @Test
    void getAllRecipesTest() {

        //GET SOME ACCOUNTS WITH SOME RECIPES AND SO MANY EGGS
        //account creation, login and recipe creation out of order to test sorting
        AccountContainer accounts = new AccountContainer();
        accounts.createAccount("username2", "password", "");
        accounts.createAccount("username1", "password", "");
        accounts.createAccount("username3", "password", "");

        accounts.login("username1", "password");
        Account current = accounts.getCurrentAccount();

        ArrayList<String> steps = new ArrayList<>();
        steps.add("step 1");
        steps.add("step 2");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Food eggs = new Food("Eggs", 100);
        ingredients.add(new Ingredient(eggs, 2, "g"));

        Recipe recipe = new Recipe("Thing With Eggs", steps, ingredients);
        current.createRecipe(recipe);

        accounts.logout();
        accounts.login("username3", "password");
        current = accounts.getCurrentAccount();

        steps = new ArrayList<>();
        steps.add("add eggs");
        steps.add("add more eggs");
        steps.add("add infinite eggs");
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(eggs, 100, "g"));
        Food superEggs = new Food("Super Eggs", 1000);
        ingredients.add(new Ingredient(superEggs, 100, "g"));

        recipe = new Recipe("Thing With SO MANY Eggs", steps, ingredients);
        current.createRecipe(recipe);

        accounts.logout();
        accounts.login("username2", "password");
        current = accounts.getCurrentAccount();

        steps = new ArrayList<>();
        steps.add("egg 1");
        steps.add("egg 2");
        steps.add("egg 3");
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(eggs, 3, "g"));

        recipe = new Recipe("Thing With Eggs", steps, ingredients);
        current.createRecipe(recipe);

        recipe = new Recipe("XEggs", steps, ingredients);
        current.createRecipe(recipe);

        //get all recipes
        List<Recipe> all = accounts.getAllRecipes();
        String[][] tuples = accounts.getRecipeListTuples(all);

        //make sure there are 4 recipes, each recipe has name + user
        assertEquals(4, tuples.length);
        for (String[] rec: tuples) {
            assertEquals(2, rec.length);
        }
        //make sure recipes are given in order - recipes in alphabetical order, then order by username
        assertEquals("Thing With Eggs", tuples[0][0]);
        assertEquals("username1", tuples[0][1]);
        assertEquals("Thing With Eggs", tuples[1][0]);
        assertEquals("username2", tuples[1][1]);
        assertEquals("Thing With SO MANY Eggs", tuples[2][0]);
        assertEquals("username3", tuples[2][1]);
        assertEquals("XEggs", tuples[3][0]);
        assertEquals("username2", tuples[3][1]);

        //make sure the printout is correct for selection
        String printout = accounts.getRecipeListString(all);
        String shouldBe = "#\tName, Author\n" +
                "1\tThing With Eggs, username1\n" +
                "2\tThing With Eggs, username2\n" +
                "3\tThing With SO MANY Eggs, username3\n" +
                "4\tXEggs, username2\n";
        assertEquals(shouldBe, printout);

        String recPrint = accounts.printSelectedRecipe(1, all);
        shouldBe = "Thing With Eggs by username1\n\n" +
                "Ingredients\n" +
                "2.0g Eggs\n" +
                "\n" +
                "Steps\n" +
                "1: step 1\n" +
                "2: step 2\n";
        assertEquals(shouldBe, recPrint);

        recPrint = accounts.printSelectedRecipe(2, all);
        shouldBe = "Thing With Eggs by username2\n\n" +
                "Ingredients\n" +
                "3.0g Eggs\n" +
                "\n" +
                "Steps\n" +
                "1: egg 1\n" +
                "2: egg 2\n" +
                "3: egg 3\n";
        assertEquals(shouldBe, recPrint);

        recPrint = accounts.printSelectedRecipe(3, all);
        shouldBe = "Thing With SO MANY Eggs by username3\n\n" +
                "Ingredients\n" +
                "100.0g Eggs\n" +
                "100.0g Super Eggs\n" +
                "\n" +
                "Steps\n" +
                "1: add eggs\n" +
                "2: add more eggs\n" +
                "3: add infinite eggs\n";
        assertEquals(shouldBe, recPrint);

        recPrint = accounts.printSelectedRecipe(4, all);
        shouldBe = "XEggs by username2\n\n" +
                "Ingredients\n" +
                "3.0g Eggs\n" +
                "\n" +
                "Steps\n" +
                "1: egg 1\n" +
                "2: egg 2\n" +
                "3: egg 3\n";
        assertEquals(shouldBe, recPrint);
    }

    @Test
    void getRecipeWithTagTest(){
        //GET SOME ACCOUNTS WITH SOME RECIPES AND SO MANY EGGS
        //account creation, login and recipe creation out of order to test sorting
        AccountContainer accounts = new AccountContainer();
        accounts.createAccount("username2", "password", "");
        accounts.createAccount("username1", "password", "");
        accounts.createAccount("username3", "password", "");

        accounts.login("username1", "password");
        Account current = accounts.getCurrentAccount();

        Recipe recipe = new Recipe("Thing With Eggs");
        recipe.addTag("tag");
        recipe.addTag("two");
        recipe.addTag("double");
        current.createRecipe(recipe);

        recipe = new Recipe("test");
        recipe.addTag("tag");
        recipe.addTag("one");
        current.createRecipe(recipe);

        accounts.logout();
        accounts.login("username3", "password");
        current = accounts.getCurrentAccount();

        recipe = new Recipe("Thing With SO MANY Eggs");
        recipe.addTag("tag");
        recipe.addTag("two");
        current.createRecipe(recipe);

        accounts.logout();
        accounts.login("username2", "password");
        current = accounts.getCurrentAccount();

        recipe = new Recipe("Thing With Eggs");
        recipe.addTag("one");
        recipe.addTag("double");
        current.createRecipe(recipe);

        recipe = new Recipe("XEggs");
        recipe.addTag("tag");
        recipe.addTag("one");
        recipe.addTag("two");
        current.createRecipe(recipe);

        recipe = new Recipe("Never See");
        current.createRecipe(recipe);



        String[] names = new String[] {"Thing With Eggs", "Thing With SO MANY Eggs", "XEggs", "test"};
        HashSet<String> namesTemp = new HashSet<>();
        for(int x = 0; x < names.length; x ++) namesTemp.add(names[x]);
        List<Recipe> subList = accounts.getRecipeByTag("tag");

        assertEquals(4, subList.size());
        for(int x = 0; x < subList.size(); x++){
            assertTrue(namesTemp.contains(subList.get(x).getName()));
            namesTemp.remove(subList.get(x).getName());
        }
        assertEquals(0,namesTemp.size());


        names = new String[] {"Thing With Eggs", "XEggs", "test"};
        for(int x = 0; x < names.length; x ++) namesTemp.add(names[x]);
        subList = accounts.getRecipeByTag("one");

        assertEquals(3, subList.size());
        for(int x = 0; x < subList.size(); x++){
            assertTrue(namesTemp.contains(subList.get(x).getName()));
            namesTemp.remove(subList.get(x).getName());
        }
        assertEquals(0,namesTemp.size());



        names = new String[] {"Thing With Eggs", "XEggs", "Thing With SO MANY Eggs"};
        for(int x = 0; x < names.length; x ++) namesTemp.add(names[x]);
        subList = accounts.getRecipeByTag("two");

        assertEquals(3, subList.size());
        for(int x = 0; x < subList.size(); x++){
            assertTrue(namesTemp.contains(subList.get(x).getName()));
            namesTemp.remove(subList.get(x).getName());
        }
        assertEquals(0,namesTemp.size());



        subList = accounts.getRecipeByTag("double");
        assertEquals(2, subList.size());

        for(int x = 0; x < subList.size(); x ++){
            assertEquals(subList.get(x).getName(), "Thing With Eggs");
        }

        subList = accounts.getRecipeByTag("notHere");
        assertEquals(0, subList.size());
    }

}
