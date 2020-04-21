import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class shoppingListTest {

    @Test
    void makeShoppingListTest(){
        shoppingList myList = new shoppingList();

        assertEquals(0,myList.getLength());
    }

    @Test
    void addIngredientsTest(){
        shoppingList myList = new shoppingList();

        //create ingredients
        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 16, "oz");

        Food food2 = new Food("rice",111, 1.452);
        Ingredient i2 = new Ingredient(food2, 100, "g");

        Food food3 = new Food("apple", 70, 1.987);
        Ingredient i3 = new Ingredient(food3, 85, "g");

        //add each ingredient and check list length
        myList.addIngredient(i1);
        assertEquals(1,myList.getLength());

        myList.addIngredient(i2);
        assertEquals(2, myList.getLength());

        myList.addIngredient(i3);
        assertEquals(3,myList.getLength());


        //check ingredient names in list
        assertEquals("Broccoli", myList.getIngredientName(0));

        assertEquals("rice", myList.getIngredientName(1));

        assertEquals("apple", myList.getIngredientName(2));

    }

    @Test
    void getIngredientIndexTest(){
        //create list and add ingredients
        shoppingList myList = new shoppingList();

        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 16, "oz");

        Food food2 = new Food("rice",111, 1.452);
        Ingredient i2 = new Ingredient(food2, 100, "g");

        Food food3 = new Food("apple", 70, 1.987);
        Ingredient i3 = new Ingredient(food3, 85, "g");

        myList.addIngredient(i1);

        myList.addIngredient(i2);

        myList.addIngredient(i3);

        //search for index of ingredients in the list
        assertEquals(0, myList.getIngredientIndex("Broccoli"));
        assertEquals(1, myList.getIngredientIndex("rice"));
        assertEquals(2, myList.getIngredientIndex("apple"));

        //search for index of ingredients not in list
        assertEquals(-1, myList.getIngredientIndex("cheese"));
        assertEquals(-1, myList.getIngredientIndex("bacon"));
        assertEquals(-1, myList.getIngredientIndex("chocolate"));

    }

    @Test
    void removeIngredientsTest(){
        //create list and add ingredients
        shoppingList myList = new shoppingList();

        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 16, "oz");

        Food food2 = new Food("rice",111, 1.452);
        Ingredient i2 = new Ingredient(food2, 100, "g");

        Food food3 = new Food("apple", 70, 1.987);
        Ingredient i3 = new Ingredient(food3, 85, "g");

        myList.addIngredient(i1);

        myList.addIngredient(i2);

        myList.addIngredient(i3);

        //remove items not in list - check that length is the same

        assertThrows(IllegalArgumentException.class, ()-> myList.removeIngredient("cheese", 1, "cup"));
        assertEquals(3,myList.getLength());

        assertThrows(IllegalArgumentException.class, ()-> myList.removeIngredient("chocolate", 10, "g"));
        assertEquals(3,myList.getLength());

        assertThrows(IllegalArgumentException.class, ()-> myList.removeIngredient("bacon", 5, "lb"));
        assertEquals(3,myList.getLength());

        //remove items that are in the list - check that length changes and item isn't found in list

        myList.removeIngredient("Broccoli", 16, "oz");
        assertEquals(2,myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("Broccoli"));

        myList.removeIngredient("rice", 100, "g");
        assertEquals(1,myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("rice"));

        myList.removeIngredient("apple", 85, "g");
        assertEquals(0,myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("apple"));

    }


    @Test
    void getAmountTest(){
        shoppingList myList = new shoppingList();

        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 16, "oz");

        Food food2 = new Food("rice",111, 1.452);
        Ingredient i2 = new Ingredient(food2, 100, "g");

        Food food3 = new Food("apple", 70, 1.987);
        Ingredient i3 = new Ingredient(food3, 85, "g");

        myList.addIngredient(i1);

        myList.addIngredient(i2);

        myList.addIngredient(i3);

        assertEquals(16, myList.getAmount(0));
        assertEquals(100, myList.getAmount(1));
        assertEquals(85, myList.getAmount(2));

    }

    @Test
    void addingAmountTest(){
        shoppingList myList = new shoppingList();

        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 16, "oz");

        Food food2 = new Food("rice",111, 1.452);
        Ingredient i2 = new Ingredient(food2, 100, "g");

        Food food3 = new Food("apple", 70, 1.987);
        Ingredient i3 = new Ingredient(food3, 85, "g");

        myList.addIngredient(i1);

        myList.addIngredient(i2);

        myList.addIngredient(i3);

        //add more of an ingredient with correct unit

        Ingredient newI1 = new Ingredient(food, 10, "oz");
        myList.addIngredient((newI1));
        Ingredient newI2 = new Ingredient(food2, 10, "g");
        myList.addIngredient((newI2));
        Ingredient newI3 = new Ingredient(food3, 10, "g");
        myList.addIngredient((newI3));

        assertEquals(3, myList.getLength());

        assertEquals(26, myList.getAmount(0));
        assertEquals(110, myList.getAmount(1));
        assertEquals(95, myList.getAmount(2));

        //adding ingredient with different unit
        Ingredient i4 = new Ingredient(food, 10, "g");
        myList.addIngredient((i4));
        Ingredient i5 = new Ingredient(food2, 10, "oz");
        myList.addIngredient((i5));
        Ingredient i6 = new Ingredient(food3, 10, "oz");
        myList.addIngredient((i6));

        assertEquals(6, myList.getLength());

        assertEquals(10, myList.getAmount(3));
        assertEquals(10, myList.getAmount(4));
        assertEquals(10, myList.getAmount(5));

    }

    @Test
    void removingAmountsTest(){
        shoppingList myList = new shoppingList();

        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 16, "oz");

        Food food2 = new Food("rice",111, 1.452);
        Ingredient i2 = new Ingredient(food2, 100, "g");

        Food food3 = new Food("apple", 70, 1.987);
        Ingredient i3 = new Ingredient(food3, 85, "g");


        myList.addIngredient(i1);

        myList.addIngredient(i2);

        myList.addIngredient(i3);


        //remove some of an ingredient with correct unit
        myList.removeIngredient("Broccoli",6,"oz");
        assertEquals(3, myList.getLength());
        assertEquals(10,myList.getAmount(0));

        myList.removeIngredient("rice",50,"g");
        assertEquals(3, myList.getLength());
        assertEquals(50,myList.getAmount(1));

        myList.removeIngredient("apple",25,"g");
        assertEquals(3, myList.getLength());
        assertEquals(60,myList.getAmount(2));


        //remove with wrong units (ingredient does not exist)
        assertThrows(IllegalArgumentException.class, ()->myList.removeIngredient("Broccoli",6,"g"));
        assertThrows(IllegalArgumentException.class, ()->myList.removeIngredient("rice",50,"oz"));

        //remove 0
        myList.removeIngredient("Broccoli",0,"oz");
        assertEquals(3, myList.getLength());
        assertEquals(10,myList.getAmount(0));

        //remove more than available
        assertThrows(IllegalArgumentException.class, ()->myList.removeIngredient("Broccoli",11,"oz"));
        assertThrows(IllegalArgumentException.class, ()->myList.removeIngredient("Broccoli",15,"oz"));

        //remove all of an ingredient
        myList.removeIngredient("Broccoli",10,"oz");
        assertEquals(2,myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("Broccoli"));

        myList.removeIngredient("rice",50,"g");
        assertEquals(1,myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("rice"));

        myList.removeIngredient("apple",60,"g");
        assertEquals(0,myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("apple"));


    }


}
