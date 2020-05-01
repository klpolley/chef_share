import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    @Test
    void isUserValidTest() {
        //too short
        assertFalse(Account.isUserValid(""));
        assertFalse(Account.isUserValid("m_e"));
        assertFalse(Account.isUserValid("User1"));

        //too long
        assertFalse(Account.isUserValid("Characters151515"));
        assertFalse(Account.isUserValid("whatalongusernamehowcoulditbe"));
        assertFalse(Account.isUserValid("the_longest_username_ever_to_be_written_in_these_tests"));

        //just right
        assertTrue(Account.isUserValid("LenOf6"));
        assertTrue(Account.isUserValid("1_username"));
        assertTrue(Account.isUserValid("fifteencharzWOO"));

        //bad characters, fine length
        assertFalse(Account.isUserValid("$$$$$$"));
        assertFalse(Account.isUserValid("hello#bad"));
        assertFalse(Account.isUserValid("my.Username"));
    }


    @Test
    void isPasswordValidTest() {
        //too short
        assertFalse(Account.isPasswordValid(""));
        assertFalse(Account.isPasswordValid("pass"));
        assertFalse(Account.isPasswordValid("badnum7"));

        //fine length alphanumeric
        assertTrue(Account.isPasswordValid("password"));
        assertTrue(Account.isPasswordValid("alongerpassword12345"));
        assertTrue(Account.isPasswordValid("thelongestpasswordyouwillneverguess"));

        //fine length with special characters
        assertTrue(Account.isPasswordValid("pass_word"));
        assertTrue(Account.isPasswordValid("#pass!word41"));
        assertTrue(Account.isPasswordValid("00password55"));

        //has spaces
        assertFalse(Account.isPasswordValid("test pass"));
        assertFalse(Account.isPasswordValid("fine except spaces"));
        assertFalse(Account.isPasswordValid("you choose your security level"));
    }

    @Test
    void constructorTest() {
        //create account and check info
        Account acct1 = new Account("username", "password1234", "the best user");
        assertEquals("username", acct1.getUsername());
        assertNotEquals("notmyuser", acct1.getUsername());
        assertTrue(acct1.confirmPassword("password1234"));
        assertFalse(acct1.confirmPassword("badpassword"));
        assertEquals("the best user", acct1.getBiography());
        assertNotEquals("the worst user", acct1.getBiography());

        //create another account and check info
        Account acct2 = new Account("unknown", "#password!!", "");
        assertEquals("unknown", acct2.getUsername());
        assertNotEquals("knownuser", acct2.getUsername());
        assertTrue(acct2.confirmPassword("#password!!"));
        assertFalse(acct2.confirmPassword("notminelol"));
        assertEquals("", acct2.getBiography());
        assertNotEquals("description", acct2.getBiography());

        //update username - error on bad input, success on good input
        assertThrows(IllegalArgumentException.class, ()-> acct2.setUsername("bad"));
        assertThrows(IllegalArgumentException.class, ()-> acct2.setUsername("nogood$$$"));
        acct2.setUsername("newuser");
        assertEquals("newuser", acct2.getUsername());
        assertNotEquals("unknown", acct2.getUsername());

        //update password - good and bad inputs
        acct2.setPassword("newpassword");
        assertThrows(IllegalArgumentException.class, () -> acct2.setPassword("short"));
        assertTrue(acct2.confirmPassword("newpassword"));
        assertFalse(acct2.confirmPassword("#password!!"));

        //update biography
        acct2.setBiography("chef");
        assertEquals("chef", acct2.getBiography());
        assertNotEquals("", acct2.getBiography());

        //try to create accounts with bad input
        assertThrows(IllegalArgumentException.class, () -> new Account("user", "password", ""));
        assertThrows(IllegalArgumentException.class, () -> new Account("username", "pass1", ""));
        assertThrows(IllegalArgumentException.class, () -> new Account("user$name", "pass$word", ""));
    }

    @Test
    void createRecipeTest() {
        Account a = new Account("testasdf", "testpassword", "bio");
        assertEquals(0, a.numOfRecipes());
        a.createRecipe("testRecipe");
        assertEquals(1, a.numOfRecipes());
        a.createRecipe("2");
        assertEquals(2, a.numOfRecipes());
        assertThrows(IllegalArgumentException.class, ()-> a.createRecipe("2"));
    }

    @Test
    void getPrintableTest() {
        ArrayList<String> steps = new ArrayList<String>();
        steps.add("First add the eggs");
        Food food = new Food("Eggs", 100);
        Ingredient ingredient = new Ingredient(food, 100, "g");
        ArrayList<Ingredient> ingredients = new ArrayList();
        ingredients.add(ingredient);

        Recipe recipe = new Recipe("Raw Eggs", steps, ingredients);
        Account r = new Account("Testasdf", "12345678", "bio");
        r.createRecipe(recipe);
        assertEquals("1: First add the eggs\n", r.recipeToString("Raw Eggs"));

        assertThrows(IllegalArgumentException.class, ()-> r.recipeToString("tasdest"));
    }

    @Test
    void getStepTest() {
        ArrayList<String> steps = new ArrayList<>();
        for (int x = 0; x < 15; x++) {
            steps.add("step " + (x + 1));
        }

        ArrayList<Ingredient> food = new ArrayList<>();
        Recipe recipe = new Recipe("test", steps, food);
        Account r = new Account("Testasdf", "12345678", "bio");
        r.createRecipe(recipe);
        assertEquals("step 1", r.getStep("test", 1));
        assertEquals("step 2", r.getStep("test", 2));
        assertEquals("step 3", r.getStep("test", 3));
        assertEquals("step 10", r.getStep("test", 10));
        assertEquals("step 15", r.getStep("test", 15));

        assertEquals(15, r.getNumberSteps("test"));

        assertThrows(IllegalArgumentException.class, () -> r.getStep("test", 100));
        assertThrows(IllegalArgumentException.class, () -> r.getStep("test", -1));
        assertThrows(IllegalArgumentException.class, () -> r.getStep("test", 0));
        assertThrows(IllegalArgumentException.class, () -> r.getStep("test", 16));


        assertThrows(IllegalArgumentException.class, ()-> r.getStep("tasdest", 100));
    }

    @Test
    void addStepTest() {
        Account r = new Account("Testasdf", "12345678", "bio");
        r.createRecipe("Test");
        assertEquals(0, r.getNumberSteps("Test"));
        r.addStep("Test", "step 1");
        assertEquals(1, r.getNumberSteps("Test"));
        assertEquals("step 1", r.getStep("Test", 1));
        assertEquals("1: step 1\n", r.recipeToString("Test"));
        r.addStep("Test", "step 2");
        assertEquals(2, r.getNumberSteps("Test"));
        assertEquals("step 2", r.getStep("Test", 2));
        assertEquals("1: step 1\n2: step 2\n", r.recipeToString("Test"));

        r.addStep("Test", "new step 2", 2);
        assertEquals("new step 2", r.getStep("Test", 2));
        assertEquals("step 2", r.getStep("Test", 3));

        r.addStep("Test", "Step 4", 4);
        assertEquals("Step 4", r.getStep("Test", 4));

        assertThrows(IllegalArgumentException.class, () -> r.addStep("Test", "test fail", 6));
    }

    @Test
    void changeStepTest() {
        Recipe recipe = new Recipe("test");
        Account r = new Account("Testasdf", "Tewasdfsad", "bio");
        r.createRecipe(recipe);
        r.addStep("test","step 1");
        r.addStep("test","step 2");
        r.editStep("test",2, "new step 2");
        assertEquals("new step 2", r.getStep("test",2));
        assertThrows(IllegalArgumentException.class, () -> r.getStep("test",3));

        r.editStep("test",1, "new step 1");
        assertEquals("new step 1", r.getStep("test",1));
        assertThrows(IllegalArgumentException.class, () -> r.getStep("test",3));

        assertThrows(IllegalArgumentException.class, () -> r.editStep("test",3, "test"));
        assertThrows(IllegalArgumentException.class, () -> r.editStep("test",-3, "test"));
        assertThrows(IllegalArgumentException.class, () -> r.editStep("test",0, "test"));
    }

    @Test
    void recipeListToStringTest(){
        Account a = new Account("sadfdgas","pasdpfapsdf","");
        a.createRecipe("test");
        a.createRecipe("2");
        a.createRecipe("wowowow");

        assertEquals("2\ntest\nwowowow\n", a.recipeListToString());

        Account b = new Account("sadfdgas","pasdpfapsdf","");
        b.createRecipe("a");
        b.createRecipe("b");
        b.createRecipe("c");

        assertEquals("a\nb\nc\n", b.recipeListToString());

        Account c = new Account("sadfdgas","pasdpfapsdf","");
        c.createRecipe("c");
        c.createRecipe("b");
        c.createRecipe("a");

        assertEquals("a\nb\nc\n", c.recipeListToString());
    }

    @Test
    void addToShoppingListTest() {
        Account acct = new Account("username", "password", "");

        Food f = new Food("Broccoli", 100);
        Ingredient i = new Ingredient(f, 1, "cup");
        Food f3 = new Food("Eggs", 100);
        Ingredient i2 = new Ingredient(f3, 2, "g");

        acct.addToShoppingList(i);
        acct.addToShoppingList(i2);

        assertEquals(2, acct.getShoppingList().getLength());

        Food f2 = new Food("Chocolate", 200);
        Ingredient i3 = new Ingredient(f2, 3, "tbsp");
        Food f4 = new Food("Apples", 100);
        Ingredient i4 = new Ingredient(f4, 5, "tbsp");

        acct.addToShoppingList(i3);
        acct.addToShoppingList(i4);

        assertEquals(4, acct.getShoppingList().getLength());
        assertEquals("Broccoli", acct.getShoppingList().getIngredientName(0));
        assertEquals("Eggs", acct.getShoppingList().getIngredientName(1));
        assertEquals("Chocolate", acct.getShoppingList().getIngredientName(2));
        assertEquals("Apples", acct.getShoppingList().getIngredientName(3));
    }

    @Test
    void removeFromShoppingListTest() {
        Account acct = new Account("username", "password", "");

        Food f = new Food("Broccoli", 100);
        Food f2 = new Food("Chocolate", 200);
        Food f3 = new Food("Eggs", 100);
        Food f4 = new Food("Apples", 100);
        Ingredient i = new Ingredient(f, 1, "cup");
        Ingredient i2 = new Ingredient(f2, 2, "g");
        Ingredient i3 = new Ingredient(f3, 3, "tbsp");
        Ingredient i4 = new Ingredient(f4, 5, "tbsp");
        acct.addToShoppingList(i);
        acct.addToShoppingList(i2);
        acct.addToShoppingList(i3);
        acct.addToShoppingList(i4);

        assertEquals(4, acct.getShoppingList().getLength());

        assertThrows(IllegalArgumentException.class, ()->acct.removeFromShoppingList("Beans", 1, "cup"));
        assertThrows(IllegalArgumentException.class, ()->acct.removeFromShoppingList("Gala Apples", 1, "cup"));

        assertThrows(IllegalArgumentException.class, ()->acct.removeFromShoppingList("Chocolate", 1, "cup"));

        acct.removeFromShoppingList("Chocolate", 1, "g");

        assertEquals(4, acct.getShoppingList().getLength());
        assertEquals("Broccoli", acct.getShoppingList().getIngredientName(0));
        assertEquals("Chocolate", acct.getShoppingList().getIngredientName(1));
        assertEquals(1, acct.getShoppingList().getAmount(1));
        assertEquals("Eggs", acct.getShoppingList().getIngredientName(2));
        assertEquals("Apples", acct.getShoppingList().getIngredientName(3));

        acct.removeFromShoppingList("Chocolate", 1, "g");
        assertEquals("Broccoli", acct.getShoppingList().getIngredientName(0));
        assertEquals("Eggs", acct.getShoppingList().getIngredientName(1));
        assertEquals("Apples", acct.getShoppingList().getIngredientName(2));

        assertThrows(IllegalArgumentException.class, ()->acct.removeFromShoppingList("Chocolate", 1, "g"));

        acct.removeFromShoppingList("Broccoli", 1, "cup");

        assertEquals(2, acct.getShoppingList().getLength());
        assertEquals("Eggs", acct.getShoppingList().getIngredientName(0));
        assertEquals("Apples", acct.getShoppingList().getIngredientName(1));

    }

    @Test
    void printListTest() {
        Account acct = new Account("username", "password", "");

        Food f = new Food("Broccoli", 100);
        Food f2 = new Food("Chocolate", 200);
        Food f3 = new Food("Eggs", 100);
        Food f4 = new Food("Apples", 100);
        Ingredient i = new Ingredient(f, 1, "cup");
        Ingredient i2 = new Ingredient(f2, 2, "g");
        Ingredient i3 = new Ingredient(f3, 3, "tbsp");
        Ingredient i4 = new Ingredient(f4, 5, "tbsp");
        acct.addToShoppingList(i);
        acct.addToShoppingList(i2);
        acct.addToShoppingList(i3);
        acct.addToShoppingList(i4);

        String shouldbe = "1.0cup Broccoli\n2.0g Chocolate\n3.0tbsp Eggs\n5.0tbsp Apples\n";
        assertEquals(shouldbe, acct.printShoppingList());
    }


    @Test
    void createCookedList(){
        Account acct = new Account("username", "password", "");
        //check that this creates an empty list of recipes
        assertEquals(0, acct.numOfCooked());
    }

    @Test
    void addCookedRecipes(){
        Account acct = new Account("username", "password", "");

        Recipe test = new Recipe("test");
        acct.addToCookedList(test);
        assertEquals(1,acct.numOfCooked());

        Recipe test2 = new Recipe("test2");
        acct.addToCookedList(test2);
        assertEquals(2,acct.numOfCooked());

        Recipe alsoTest = new Recipe("alsoTest");
        acct.addToCookedList(alsoTest);
        assertEquals(3,acct.numOfCooked());

        Recipe wowTests = new Recipe("wowTests");
        acct.addToCookedList(wowTests);
        assertEquals(4,acct.numOfCooked());

    }

    @Test
    void ingredientInInventoryTest() {

        //also tests adding and removing from inventory

        Account acct = new Account("username", "password", "");

        Food food = new Food("Banana", 100);
        Food food2 = new Food("Orange", 100);
        Food food3 = new Food("Apple", 100);

        Ingredient ing1 = new Ingredient(food, 1, "g");
        acct.addToInventory(ing1);
        assertTrue(acct.getInventory().haveIngredient(ing1));

        Ingredient ing2 = new Ingredient(food, 2, "cup");
        acct.addToInventory(ing2);
        assertTrue(acct.getInventory().haveIngredient(ing2));

        Ingredient ing3 = new Ingredient(food, 3, "g");
        acct.addToInventory(ing3);

        Ingredient ing4 = new Ingredient(food2, 2, "g");
        acct.addToInventory(ing4);

        Ingredient ing5 = new Ingredient(food3, 5, "lb");
        acct.addToInventory(ing5);

        String shouldBe = "5.0 lb\nApple\n" +
                "4.0 g\nBanana\n" +
                "2.0 cup\nBanana\n" +
                "2.0 g\nOrange\n";

        assertEquals(shouldBe, acct.printInventory());

        assertTrue(acct.ingredientInInventory("Banana", 2, "g"));
        assertTrue(acct.ingredientInInventory("Banana", 2, "cup"));
        assertTrue(acct.ingredientInInventory("Orange", 1, "g"));
        assertTrue(acct.ingredientInInventory("Orange", 2, "g"));
        assertTrue(acct.ingredientInInventory("Apple", 5, "lb"));
        assertTrue(acct.ingredientInInventory("Apple", 3, "lb"));
        assertTrue(acct.ingredientInInventory("Apple", 1, "lb"));

        assertFalse(acct.ingredientInInventory("Banana", 1, "lb"));
        assertFalse(acct.ingredientInInventory("Banana", 5, "g"));
        assertFalse(acct.ingredientInInventory("Banana", 3, "cup"));
        assertFalse(acct.ingredientInInventory("Orange", 3, "cup"));
        assertFalse(acct.ingredientInInventory("Orange", 3, "g"));
        assertFalse(acct.ingredientInInventory("Apple", 3, "cup"));
        assertFalse(acct.ingredientInInventory("Apple", 6, "lb"));

        assertFalse(acct.ingredientInInventory("Pear", 4, "g"));
        assertFalse(acct.ingredientInInventory("Peach", 5, "lb"));

        assertThrows(IllegalArgumentException.class, ()->acct.removeFromInventory("Peach", 1, "g"));
        assertThrows(IllegalArgumentException.class, ()->acct.removeFromInventory("Banana", 1, "tsp"));
        assertThrows(IllegalArgumentException.class, ()->acct.removeFromInventory("Banana", 6, "g"));
        assertThrows(IllegalArgumentException.class, ()->acct.removeFromInventory("Apple", 1, "g"));
        assertThrows(IllegalArgumentException.class, ()->acct.removeFromInventory("Orange", 2.01, "g"));

        acct.removeFromInventory("Banana", 2, "g");

        shouldBe = "5.0 lb\nApple\n" +
                "2.0 g\nBanana\n" +
                "2.0 cup\nBanana\n" +
                "2.0 g\nOrange\n";
        assertEquals(shouldBe, acct.printInventory());

        acct.removeFromInventory("Apple", 1.5, "lb");

        shouldBe = "3.5 lb\nApple\n" +
                "2.0 g\nBanana\n" +
                "2.0 cup\nBanana\n" +
                "2.0 g\nOrange\n";
        assertEquals(shouldBe,  acct.printInventory());

        acct.removeFromInventory("Banana", 2, "cup");

        shouldBe = "3.5 lb\nApple\n" +
                "2.0 g\nBanana\n" +
                "2.0 g\nOrange\n";
        assertEquals(shouldBe,  acct.printInventory());

        acct.removeFromInventory("Orange", 2, "g");

        shouldBe = "3.5 lb\nApple\n" +
                "2.0 g\nBanana\n";
        assertEquals(shouldBe,  acct.printInventory());

        assertThrows(IllegalArgumentException.class, ()->acct.removeFromInventory("Banana", 2, "cup"));

        acct.removeFromInventory("Apple", 3.5, "lb");
        acct.removeFromInventory("Banana", 2, "g");

        assertEquals("",  acct.printInventory());


    }

    @Test
    void ingredientFromShoppingToInventoryTest(){
        Account acct = new Account("username", "password", "");

        Food f = new Food("Broccoli", 100);
        Ingredient i = new Ingredient(f, 1, "cup");

        Food f3 = new Food("Eggs", 100);
        Ingredient i2 = new Ingredient(f3, 2, "g");

        Food f2 = new Food("Chocolate", 200);
        Ingredient i3 = new Ingredient(f2, 3, "tbsp");

        Food f4 = new Food("Apples", 100);
        Ingredient i4 = new Ingredient(f4, 5, "tbsp");

        acct.addToShoppingList(i);
        acct.addToShoppingList(i2);
        acct.addToShoppingList(i3);
        acct.addToShoppingList(i4);

        assertEquals(4, acct.getShoppingList().getLength());

        acct.ingredientFromShoppingToInventory("Broccoli",1,"cup");
        assertEquals(3, acct.getShoppingList().getLength());
        assertTrue(acct.ingredientInInventory("Broccoli",1,"cup"));

        acct.ingredientFromShoppingToInventory("Eggs",2,"g");
        assertEquals(2, acct.getShoppingList().getLength());
        assertTrue(acct.ingredientInInventory("Eggs",2,"g"));

        acct.ingredientFromShoppingToInventory("Chocolate",3,"tbsp");
        assertEquals(1, acct.getShoppingList().getLength());
        assertTrue(acct.ingredientInInventory("Chocolate",3,"tbsp"));

        acct.ingredientFromShoppingToInventory("Apples",5,"tbsp");
        assertEquals(0, acct.getShoppingList().getLength());
        assertTrue(acct.ingredientInInventory("Apples",5,"tbsp"));

    void cookRecipeTest() throws InsufficientIngredientsException {
        Account acct = new Account("username", "password", "");
        Recipe rec = new Recipe("recipe");

        Food food = new Food("Banana", 100);
        Food food2 = new Food("Orange", 100);
        Food food3 = new Food("Apple", 100);

        Ingredient ing1 = new Ingredient(food, 1, "g");
        acct.addToInventory(ing1);


        Ingredient ing2 = new Ingredient(food2, 2, "g");
        acct.addToInventory(ing2);
        rec.addIngredient(ing2);

        Ingredient ing3 = new Ingredient(food3, 5, "lb");


        acct.cookRecipe(rec);

        assertTrue(acct.ingredientInInventory(ing1));
        assertFalse(acct.ingredientInInventory(ing2));

        acct.addToInventory(ing1);
        rec.addIngredient(ing3);

        assertThrows(InsufficientIngredientsException.class, ()->acct.cookRecipe(rec));
    }
}
