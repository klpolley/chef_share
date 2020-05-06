import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InventoryTest {

    @Test
    void addIngredientTest(){
        //Testing that ingredient is added properly
        Inventory i = new Inventory();
        Food food = new Food("Banana", 100, 1);
        Ingredient ingredient = new Ingredient(food, 1100, "g");
        i.addIngredient(ingredient);
        assertTrue(i.haveIngredient(ingredient));

        //Testing that both ingredients are still in the inventory when adding another one
        Food food2 = new Food("Orange", 100, 10);
        Ingredient ingredient2 = new Ingredient(food2, 1, "g");
        assertFalse(i.haveIngredient(ingredient2));
        i.addIngredient(ingredient2);
        assertTrue(i.haveIngredient(ingredient));
        assertTrue(i.haveIngredient(ingredient2));

        assertTrue(i.validIngredient("Banana", 1, "kg"));
        assertTrue(i.validIngredient("Banana", 1, "fl oz"));
        assertFalse(i.validIngredient("Banana", 10, "gal"));
    }

    @Test
    void toStringTest(){
        Inventory i = new Inventory();
        Food food = new Food("Banana", 100);
        Ingredient ingredient = new Ingredient(food, 1, "g");
        i.addIngredient(ingredient);
        Ingredient i1 = new Ingredient(food, 1, "kg");
        assertEquals("1.00 g\nBanana\n", i.toString());
        i.addIngredient(i1);
        assertEquals("1001.00 g\nBanana\n", i.toString());
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
        Inventory inventory = new Inventory();

        Food food = new Food("Banana", 100, 1);
        Food food2 = new Food("Orange", 100);
        Food food3 = new Food("Apple", 100);

        Ingredient ing1 = new Ingredient(food, 1, "g");
        inventory.addIngredient(ing1);
        Ingredient ing2 = new Ingredient(food, 2, "cup");
        inventory.addIngredient(ing2);
        Ingredient ing3 = new Ingredient(food, 3, "g");
        inventory.addIngredient(ing3);
        Ingredient ing4 = new Ingredient(food2, 2, "g");
        inventory.addIngredient(ing4);
        Ingredient ing5 = new Ingredient(food3, 5, "lb");
        inventory.addIngredient(ing5);

        String shouldBe = "5.0 lb\nApple\n" +
                "4.0 g\nBanana\n" +
                "2.0 cup\nBanana\n" +
                "2.0 g\nOrange\n";
        assertEquals(shouldBe, inventory.toString());

        assertThrows(IllegalArgumentException.class, ()->inventory.removeIngredient("Peach", 1, "g"));
        assertThrows(IllegalArgumentException.class, ()->inventory.removeIngredient("Banana", 1, "tsp"));
        assertThrows(IllegalArgumentException.class, ()->inventory.removeIngredient("Banana", 6, "g"));
        assertThrows(IllegalArgumentException.class, ()->inventory.removeIngredient("Apple", 1, "g"));
        assertThrows(IllegalArgumentException.class, ()->inventory.removeIngredient("Orange", 2.01, "g"));

        inventory.removeIngredient("Banana", 2, "g");

        shouldBe = "5.0 lb\nApple\n" +
                "2.0 g\nBanana\n" +
                "2.0 cup\nBanana\n" +
                "2.0 g\nOrange\n";
        assertEquals(shouldBe, inventory.toString());

        inventory.removeIngredient("Apple", 1.5, "lb");

        shouldBe = "3.5 lb\nApple\n" +
                "2.0 g\nBanana\n" +
                "2.0 cup\nBanana\n" +
                "2.0 g\nOrange\n";
        assertEquals(shouldBe, inventory.toString());

        inventory.removeIngredient("Banana", 2, "cup");

        shouldBe = "3.5 lb\nApple\n" +
                "2.0 g\nBanana\n" +
                "2.0 g\nOrange\n";
        assertEquals(shouldBe, inventory.toString());

        inventory.removeIngredient("Orange", 2, "g");

        shouldBe = "3.5 lb\nApple\n" +
                "2.0 g\nBanana\n";
        assertEquals(shouldBe, inventory.toString());

        assertThrows(IllegalArgumentException.class, ()->inventory.removeIngredient("Banana", 2, "cup"));

        inventory.removeIngredient("Apple", 3.5, "lb");
        inventory.removeIngredient("Banana", 2, "g");

        assertEquals("", inventory.toString());

    }

}
