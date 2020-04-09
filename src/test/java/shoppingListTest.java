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

        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 16, "oz");

        Food food2 = new Food("rice",111, 1.452);
        Ingredient i2 = new Ingredient(food2, 100, "g");

        Food food3 = new Food("apple", 70, 1.987);
        Ingredient i3 = new Ingredient(food3, 85, "g");

        myList.addIngredient(i1);
        assertEquals(1,myList.getLength());

        myList.addIngredient(i2);
        assertEquals(2, myList.getLength());

        myList.addIngredient(i3);
        assertEquals(3,myList.getLength());
    }


}
