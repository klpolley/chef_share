import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IngredientTest {

    @Test
    void validUnitTest(){
        //invalid units
        assertFalse(Ingredient.validUnit(""));
        assertFalse(Ingredient.validUnit("labs"));
        assertFalse(Ingredient.validUnit("greeb"));
        assertFalse(Ingredient.validUnit("f"));


        //valid units full name
        assertTrue(Ingredient.validUnit("gram"));
        assertTrue(Ingredient.validUnit("kilogram"));
        assertTrue(Ingredient.validUnit("ounce"));
        assertTrue(Ingredient.validUnit("pound"));
        assertTrue(Ingredient.validUnit("gallon"));
        assertTrue(Ingredient.validUnit("quart"));
        assertTrue(Ingredient.validUnit("pint"));
        assertTrue(Ingredient.validUnit("cup"));
        assertTrue(Ingredient.validUnit("tablespoon"));
        assertTrue(Ingredient.validUnit("teaspoon"));
        assertTrue(Ingredient.validUnit("fluid ounce"));
        assertTrue(Ingredient.validUnit("milliliter"));
        assertTrue(Ingredient.validUnit("liter"));
        assertTrue(Ingredient.validUnit("millilitre"));
        assertTrue(Ingredient.validUnit("litre"));


        //Abreviations
        assertTrue(Ingredient.validUnit("g"));
        assertTrue(Ingredient.validUnit("kg"));
        assertTrue(Ingredient.validUnit("oz"));
        assertTrue(Ingredient.validUnit("lb"));
        assertTrue(Ingredient.validUnit("#"));
        assertTrue(Ingredient.validUnit("lbs"));
        assertTrue(Ingredient.validUnit("gal"));
        assertTrue(Ingredient.validUnit("q"));
        assertTrue(Ingredient.validUnit("qt"));
        assertTrue(Ingredient.validUnit("p"));
        assertTrue(Ingredient.validUnit("pt"));
        assertTrue(Ingredient.validUnit("c"));
        assertTrue(Ingredient.validUnit("T"));
        assertTrue(Ingredient.validUnit("tbs"));
        assertTrue(Ingredient.validUnit("tbl"));
        assertTrue(Ingredient.validUnit("tbsp"));
        assertTrue(Ingredient.validUnit("t"));
        assertTrue(Ingredient.validUnit("tsp"));
        assertTrue(Ingredient.validUnit("fl oz"));
        assertTrue(Ingredient.validUnit("cc"));
        assertTrue(Ingredient.validUnit("ml"));
        assertTrue(Ingredient.validUnit("l"));

        //capitals
        assertTrue(Ingredient.validUnit("Gram"));

        //periods in abreviations. Only tablespoon and teaspoon abv. can have the dot and not the single letter ones.
        assertTrue(Ingredient.validUnit("tbs."));
        assertTrue(Ingredient.validUnit("tbl."));
        assertTrue(Ingredient.validUnit("tbsp."));
        assertTrue(Ingredient.validUnit("tsp."));
        assertFalse(Ingredient.validUnit("g."));
        assertFalse(Ingredient.validUnit(".tsp"));
        assertFalse(Ingredient.validUnit("tbsp.ghj"));
        assertFalse(Ingredient.validUnit("tbspghj."));

    }

    @Test
    void constructorTest(){
        Food food = new Food("Broccoli", 34);
        Ingredient i1 = new Ingredient(food, 16, "oz");
        assertEquals("Broccoli", i1.getName());
        assertEquals(154.22, i1.getCalories(), 10);
        assertEquals(16, i1.getAmount(), 10);
        assertEquals("oz", i1.getUnit());

        Food food2 = new Food("rice",111, 1.452);
        Ingredient i2 = new Ingredient(food2, 100, "g");
        assertEquals("rice", i2.getName());
        assertEquals(111, i2.getCalories(), 10);
        assertEquals(100, i2.getAmount(), 10);
        assertEquals("g", i2.getUnit());

        Ingredient i3 = new Ingredient(food2, 2, "cup");
        assertEquals("rice", i3.getName());
        assertEquals(762.62, i3.getCalories(), 10);
        assertEquals(2, i3.getAmount(), 10);
        assertEquals("cup", i3.getUnit());

        assertThrows(IllegalArgumentException.class, ()-> new Ingredient(null, 4, "c"));
        assertThrows(IllegalArgumentException.class, ()-> new Ingredient(food, 4, "cop"));
        assertThrows(IllegalArgumentException.class, ()-> new Ingredient(food, -4, "c"));
    }

    @Test
    void convertCalToUnitTest(){
        assertEquals(0.34, Ingredient.convertCalToUnit("g", 34, 0.39), 10);
        assertEquals(340, Ingredient.convertCalToUnit("kg", 34, 0.39), 10);
        assertEquals(9.6388, Ingredient.convertCalToUnit("oz", 34, 0.39), 10);
        assertEquals(154.2212, Ingredient.convertCalToUnit("lb", 34, 0.39), 10);
        assertEquals(501.9454, Ingredient.convertCalToUnit("gal", 34, 0.39), 10);
        assertEquals(125.4863, Ingredient.convertCalToUnit("qt", 34, 0.39), 10);
        assertEquals(62.7432, Ingredient.convertCalToUnit("pt", 34, 0.39), 10);
        assertEquals(31.3716, Ingredient.convertCalToUnit("c", 34, 0.39), 10);
        assertEquals(1.9607, Ingredient.convertCalToUnit("T", 34, 0.39), 10);
        assertEquals(0.6536, Ingredient.convertCalToUnit("t", 34, 0.39), 10);
        assertEquals(3.9214, Ingredient.convertCalToUnit("fl oz", 34, 0.39), 10);
        assertEquals(0.1326, Ingredient.convertCalToUnit("ml", 34, 0.39), 10);
        assertEquals(132.6, Ingredient.convertCalToUnit("l", 34, 0.39), 10);

        assertEquals(1, Ingredient.convertCalToUnit("g", 100, 1), 10);
        assertEquals(1000, Ingredient.convertCalToUnit("kg", 100, 1), 10);
        assertEquals(28.3495, Ingredient.convertCalToUnit("oz", 100, 1), 10);
        assertEquals(453.5919, Ingredient.convertCalToUnit("lb", 100, 1), 10);
        assertEquals(3785.41, Ingredient.convertCalToUnit("gal", 100, 1), 10);
        assertEquals(946.3525, Ingredient.convertCalToUnit("qt", 100, 1), 10);
        assertEquals(473.1763, Ingredient.convertCalToUnit("pt", 100, 1), 10);
        assertEquals(236.5881, Ingredient.convertCalToUnit("c", 100, 1), 10);
        assertEquals(14.7868, Ingredient.convertCalToUnit("T", 100, 1), 10);
        assertEquals(4.9289, Ingredient.convertCalToUnit("t", 100, 1), 10);
        assertEquals(29.5735, Ingredient.convertCalToUnit("fl oz", 100, 1), 10);
        assertEquals(1, Ingredient.convertCalToUnit("ml", 100, 1), 10);
        assertEquals(1000, Ingredient.convertCalToUnit("l", 100, 1), 10);

        assertThrows(IllegalArgumentException.class, ()-> Ingredient.convertCalToUnit("asdf", 100, 1));
        assertThrows(IllegalArgumentException.class, ()-> Ingredient.convertCalToUnit("gal", 100, 0.0));
        assertEquals(1, Ingredient.convertCalToUnit("g", 100, 0.0));
        assertEquals(1, Ingredient.convertCalToUnit("gram", 100, 1));
    }
}
