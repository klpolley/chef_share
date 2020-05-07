import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class shoppingListTest {

    @Test
    void makeShoppingListTest() {
        shoppingList myList = new shoppingList();

        assertEquals(0, myList.getLength());
    }

    @Test
    void addIngredientsTest() {
        shoppingList myList = new shoppingList();

        //create ingredients
        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 16, "oz");

        Food food2 = new Food("rice", 111, 1.452);
        Ingredient i2 = new Ingredient(food2, 100, "g");

        Food food3 = new Food("apple", 70, 1.987);
        Ingredient i3 = new Ingredient(food3, 85, "g");

        //add each ingredient and check list length
        myList.addIngredient(i1);
        assertEquals(1, myList.getLength());

        myList.addIngredient(i2);
        assertEquals(2, myList.getLength());

        myList.addIngredient(i3);
        assertEquals(3, myList.getLength());


        //check ingredient names in list
        assertEquals("Broccoli", myList.getIngredientName(0));

        assertEquals("rice", myList.getIngredientName(1));

        assertEquals("apple", myList.getIngredientName(2));

    }

    @Test
    void getIngredientIndexTest() {
        //create list and add ingredients
        shoppingList myList = new shoppingList();

        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 16, "oz");

        Food food2 = new Food("rice", 111, 1.452);
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
    void removeIngredientsTest() {
        //create list and add ingredients
        shoppingList myList = new shoppingList();

        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 16, "oz");

        Food food2 = new Food("rice", 111, 1.452);
        Ingredient i2 = new Ingredient(food2, 100, "g");

        Food food3 = new Food("apple", 70, 1.987);
        Ingredient i3 = new Ingredient(food3, 85, "g");

        myList.addIngredient(i1);

        myList.addIngredient(i2);

        myList.addIngredient(i3);

        //remove items not in list - check that length is the same

        assertThrows(IllegalArgumentException.class, () -> myList.removeIngredient("cheese", 1, "cup"));
        assertEquals(3, myList.getLength());

        assertThrows(IllegalArgumentException.class, () -> myList.removeIngredient("chocolate", 10, "g"));
        assertEquals(3, myList.getLength());

        assertThrows(IllegalArgumentException.class, () -> myList.removeIngredient("bacon", 5, "lb"));
        assertEquals(3, myList.getLength());

        //remove items that are in the list - check that length changes and item isn't found in list

        myList.removeIngredient("Broccoli", 16, "oz");
        assertEquals(2, myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("Broccoli"));

        myList.removeIngredient("rice", 100, "g");
        assertEquals(1, myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("rice"));

        myList.removeIngredient("apple", 85, "g");
        assertEquals(0, myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("apple"));

    }


    @Test
    void getAmountTest() {
        shoppingList myList = new shoppingList();

        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 16, "oz");

        Food food2 = new Food("rice", 111, 1.452);
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
    void addingAmountTest() {
        shoppingList myList = new shoppingList();

        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 16, "oz");

        Food food2 = new Food("rice", 111, 1.452);
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

        assertEquals(3, myList.getLength());

        assertEquals(26.35, myList.getAmount(0), 0.001);
        assertEquals(393.50, myList.getAmount(1), 0.001);
        assertEquals(378.50, myList.getAmount(2), 0.001);

        Ingredient i7 = new Ingredient(food, 10, "T");
        Ingredient i8 = new Ingredient(food2, 10, "T");

        myList.addIngredient(i8);
        assertEquals(3, myList.getLength());
        assertEquals(651.35, myList.getAmount(1), 0.001);
        assertThrows(IllegalArgumentException.class, () -> myList.addIngredient(i7));
    }

    @Test
    void removingAmountsTest() {
        shoppingList myList = new shoppingList();

        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 16, "oz");

        Food food2 = new Food("rice", 111, 1.452);
        Ingredient i2 = new Ingredient(food2, 100, "g");

        Food food3 = new Food("apple", 70, 1.987);
        Ingredient i3 = new Ingredient(food3, 85, "g");


        myList.addIngredient(i1);

        myList.addIngredient(i2);

        myList.addIngredient(i3);


        //remove some of an ingredient with correct unit
        myList.removeIngredient("Broccoli", 6, "oz");
        assertEquals(3, myList.getLength());
        assertEquals(10, myList.getAmount(0));

        myList.removeIngredient("rice", 50, "g");
        assertEquals(3, myList.getLength());
        assertEquals(50, myList.getAmount(1));

        myList.removeIngredient("apple", 25, "g");
        assertEquals(3, myList.getLength());
        assertEquals(60, myList.getAmount(2));

        Ingredient i4 = new Ingredient(food2, 30, "t");
        myList.addIngredient(i4);
        assertEquals(3, myList.getLength());
        assertEquals(307.85, myList.getAmount(1), 0.001);

        myList.removeIngredient("rice", 10, "tablespoon");
        assertEquals(3, myList.getLength());
        assertEquals(50, myList.getAmount(1), 0.001);

        assertThrows(IllegalArgumentException.class, () -> myList.removeIngredient("Broccoli", 10, "T"));
        //remove 0
        myList.removeIngredient("Broccoli", 0, "oz");
        assertEquals(3, myList.getLength());
        assertEquals(10, myList.getAmount(0));

        //remove more than available
        assertThrows(IllegalArgumentException.class, () -> myList.removeIngredient("Broccoli", 11, "oz"));
        assertThrows(IllegalArgumentException.class, () -> myList.removeIngredient("Broccoli", 15, "oz"));

        //remove all of an ingredient
        myList.removeIngredient("Broccoli", 10, "oz");
        assertEquals(2, myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("Broccoli"));

        myList.removeIngredient("rice", 50, "g");
        assertEquals(1, myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("rice"));

        myList.removeIngredient("apple", 60, "g");
        assertEquals(0, myList.getLength());
        assertEquals(-1, myList.getIngredientIndex("apple"));


    }

    @Test
    void unitConversionTest() {
        Food f1 = new Food("Test", 10, 10);
        Ingredient i1 = new Ingredient(f1, 16, "oz");
        assertEquals(1, shoppingList.unitConversion("lb", i1), 0.001);
        i1 = new Ingredient(f1, 16, "oz");
        assertEquals(453.6, shoppingList.unitConversion("g", i1), 0.001);
        i1 = new Ingredient(f1, 16, "oz");
        assertEquals(7.66, shoppingList.unitConversion("teaspoon", i1), 0.001);

        i1 = new Ingredient(f1, 1000, "g");
        assertEquals(1, shoppingList.unitConversion("kg", i1), 0.001);
        i1 = new Ingredient(f1, 160, "g");
        assertEquals(5.64, shoppingList.unitConversion("oz", i1), 0.001);
        i1 = new Ingredient(f1, 160, "g");
        assertEquals(2.70, shoppingList.unitConversion("teaspoon", i1), 0.001);
        i1 = new Ingredient(f1, 1300, "g");
        assertEquals(4.58, shoppingList.unitConversion("fl oz", i1), 0.001);
        i1 = new Ingredient(f1, 16, "g");
        assertEquals(1.6, shoppingList.unitConversion("ml", i1), 0.001);

        i1 = new Ingredient(f1, 768, "teaspoon");
        assertEquals(256, shoppingList.unitConversion("tablespoon", i1), 0.001);
        i1 = new Ingredient(f1, 768, "teaspoon");
        assertEquals(16, shoppingList.unitConversion("cup", i1), 0.001);
        i1 = new Ingredient(f1, 768, "teaspoon");
        assertEquals(8, shoppingList.unitConversion("pint", i1), 0.001);
        i1 = new Ingredient(f1, 768, "teaspoon");
        assertEquals(4, shoppingList.unitConversion("quart", i1), 0.001);
        i1 = new Ingredient(f1, 768, "teaspoon");
        assertEquals(1, shoppingList.unitConversion("gal", i1), 0.001);

        i1 = new Ingredient(f1, 768, "ounce");
        assertEquals(768, shoppingList.unitConversion("ounce", i1), 0.001);
        i1 = new Ingredient(f1, 768, "ml");
        assertEquals(768, shoppingList.unitConversion("ml", i1), 0.001);
        i1 = new Ingredient(f1, 768, "ounce");
        assertEquals(768, shoppingList.unitConversion("oz", i1), 0.001);

        i1 = new Ingredient(f1, 10, "gram");
        assertEquals(0.02, shoppingList.unitConversion("pound", i1), 0.001);


        Ingredient finalI = i1;
        assertThrows(IllegalArgumentException.class, () -> shoppingList.unitConversion("invalidUnit", finalI));
        assertThrows(IllegalArgumentException.class, () -> shoppingList.unitConversion("cup", null));
    }

    @Test
    void unitSimpTest() {
        String[] units = {"gram", "g", "kilogram", "kg", "ounce", "oz", "pound", "lb", "#", "lbs", "gallon", "gal", "quart", "q", "qt", "pint", "p", "pt", "cup", "c",//18
                "tablespoon", "T", "tbs", "tbl", "tbsp", "tbs.", "tbl.", "tbsp.", "teaspoon", "t", "tsp", "tsp.", "fluid ounce", "fl oz", "milliliter", "millilitre", "cc", "ml", "liter", "litre", "l"};
        for (int x = 0; x < units.length; x++) {
            if (x < 2)
                assertEquals(shoppingList.Unit.GRAM, shoppingList.unitSimp(units[x]));
            else if (x < 4)
                assertEquals(shoppingList.Unit.KILOGRAM, shoppingList.unitSimp(units[x]));
            else if (x < 6)
                assertEquals(shoppingList.Unit.OUNCE, shoppingList.unitSimp(units[x]));
            else if (x < 10)
                assertEquals(shoppingList.Unit.POUND, shoppingList.unitSimp(units[x]));
            else if (x < 12)
                assertEquals(shoppingList.Unit.GALLON, shoppingList.unitSimp(units[x]));
            else if (x < 15)
                assertEquals(shoppingList.Unit.QUART, shoppingList.unitSimp(units[x]));
            else if (x < 18)
                assertEquals(shoppingList.Unit.PINT, shoppingList.unitSimp(units[x]));
            else if (x < 20)
                assertEquals(shoppingList.Unit.CUP, shoppingList.unitSimp(units[x]));
            else if (x < 28)
                assertEquals(shoppingList.Unit.TABLESPOON, shoppingList.unitSimp(units[x]));
            else if (x < 32)
                assertEquals(shoppingList.Unit.TEASPOON, shoppingList.unitSimp(units[x]));
            else if (x < 34)
                assertEquals(shoppingList.Unit.FLUID_OUNCE, shoppingList.unitSimp(units[x]));
            else if (x < 38)
                assertEquals(shoppingList.Unit.MILLILITER, shoppingList.unitSimp(units[x]));
            else
                assertEquals(shoppingList.Unit.LITER, shoppingList.unitSimp(units[x]));

            assertEquals(shoppingList.Unit.NONE, shoppingList.unitSimp("unit"));
            assertEquals(shoppingList.Unit.NONE, shoppingList.unitSimp(""));


        }
    }
}
