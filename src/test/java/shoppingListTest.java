import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

        assertThrows(IllegalArgumentException.class, ()-> myList.removeIngredient("cheese"));
        assertEquals(3,myList.getLength());

        assertThrows(IllegalArgumentException.class, ()-> myList.removeIngredient("chocolate"));
        assertEquals(3,myList.getLength());

        assertThrows(IllegalArgumentException.class, ()-> myList.removeIngredient("bacon"));
        assertEquals(3,myList.getLength());

        //remove items that are in the list - check that length changes and item isn't found in list

        myList.removeIngredient("Broccoli");
        assertEquals(2,myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("Broccoli"));

        myList.removeIngredient("rice");
        assertEquals(1,myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("rice"));

        myList.removeIngredient("apple");
        assertEquals(0,myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("apple"));

    }

    @Test
    void addRecipeToListTest() {

        //create recipes to add to list

        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 2, "cup");
        Food food2 = new Food("Cheese",111, 1.452);
        Ingredient i2 = new Ingredient(food2, 3, "lb");
        Food food3 = new Food("Bread", 70, 1.987);
        Ingredient i3 = new Ingredient(food3, 92, "g");

        ArrayList<Ingredient> ingrs = new ArrayList<>();
        ingrs.add(i1);
        ingrs.add(i2);
        ingrs.add(i3);

        ArrayList<String> steps = new ArrayList<>();
        steps.add("melt cheese and dip my dudes");

        Recipe r1 = new Recipe("Fondue", steps, ingrs);

        Food food4 = new Food("Pasta", 200);
        Ingredient i4 = new Ingredient(food4, 16, "oz");
        Food food5 = new Food("Tomato",50, 2);
        Ingredient i5 = new Ingredient(food5, 100, "g");

        ArrayList<Ingredient> ingr2 = new ArrayList<>();
        ingr2.add(i4);
        ingr2.add(i5);

        ArrayList<String> steps2 = new ArrayList<>();
        steps2.add("cook pasta");
        steps2.add("add tomatoes");

        Recipe r2 = new Recipe("Pasta Thing", steps2, ingr2);

        shoppingList list = new shoppingList();

        list.addRecipeToList(r1);

        assertNotEquals(-1, list.getIngredientIndex("Broccoli"));
        assertNotEquals(-1, list.getIngredientIndex("Bread"));
        assertNotEquals(-1, list.getIngredientIndex("Cheese"));
        assertEquals(-1, list.getIngredientIndex("Pasta"));
        assertEquals(-1, list.getIngredientIndex("Tomato"));
        assertEquals(-1, list.getIngredientIndex("What"));

        list.addRecipeToList(r2);

        assertNotEquals(-1, list.getIngredientIndex("Broccoli"));
        assertNotEquals(-1, list.getIngredientIndex("Bread"));
        assertNotEquals(-1, list.getIngredientIndex("Cheese"));
        assertNotEquals(-1, list.getIngredientIndex("Pasta"));
        assertNotEquals(-1, list.getIngredientIndex("Tomato"));
        assertEquals(-1, list.getIngredientIndex("Nope"));

    }

}
