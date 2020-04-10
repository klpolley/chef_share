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

}
