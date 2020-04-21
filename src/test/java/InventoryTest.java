import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InventoryTest {

    @Test
    void addIngredientTest(){
        //Testing that ingredient is added properly
        Inventory i = new Inventory();
        Food food = new Food("Banana", 100);
        Ingredient ingredient = new Ingredient(food, 1, "g");
        i.addIngredient(ingredient);
        assertTrue(i.haveIngredient(ingredient));

        //Testing that both ingredients are still in the inventory when adding another one
        Food food2 = new Food("Orange", 100);
        Ingredient ingredient2 = new Ingredient(food2, 1, "g");
        assertFalse(i.haveIngredient(ingredient2));
        i.addIngredient(ingredient2);
        assertTrue(i.haveIngredient(ingredient));
        assertTrue(i.haveIngredient(ingredient2));
    }

    @Test
    void toStringTest(){
        Inventory i = new Inventory();
        Food food = new Food("Banana", 100);
        Ingredient ingredient = new Ingredient(food, 1, "g");
        i.addIngredient(ingredient);
        assertEquals("1.0 g\nBanana\n1.0 Calories\n", i.toString());
    }

    @Test
    void haveIngredientTest(){
        //Testing that ingredient is found properly
        Inventory i = new Inventory();
        Food food = new Food("Banana", 100);
        Ingredient ingredient = new Ingredient(food, 1, "g");
        i.addIngredient(ingredient);
        assertTrue(i.haveIngredient(ingredient));

        //Testing that a random ingredient is not already in the inventory
        Food food2 = new Food("Orange", 100);
        Ingredient ingredient2 = new Ingredient(food2, 1, "g");
        assertFalse(i.haveIngredient(ingredient2));

        //Testing that both ingredients are still in the inventory when adding another one
        i.addIngredient(ingredient2);
        assertTrue(i.haveIngredient(ingredient));
        assertTrue(i.haveIngredient(ingredient2));

        //Testing that an identical ingredient is identified correctly
        Ingredient ingredient3 = new Ingredient(food, 1, "g");
        assertTrue(i.haveIngredient(ingredient3));
    }

    @Test
    void removeIngredientTest() {

    }

}
