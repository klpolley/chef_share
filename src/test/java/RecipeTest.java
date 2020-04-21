import org.junit.jupiter.api.Test;

import java.nio.channels.FileLockInterruptionException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {

    @Test
    void constructorTest(){
        //Invalid blank names:
        assertThrows(IllegalArgumentException.class, ()-> new Recipe(""));
        assertThrows(IllegalArgumentException.class, ()-> new Recipe(" "));
        assertThrows(IllegalArgumentException.class, ()-> new Recipe("           "));

        //Valid names:
        Recipe r;
        r = new Recipe("valid name");
        r = new Recipe("571%25&3 and this still works");
    }

    @Test
    void getPrintableTest(){
        ArrayList<String> steps = new ArrayList<String>();
        steps.add("First add the eggs");
        Food food = new Food("Eggs", 100);
        Ingredient ingredient = new Ingredient(food, 100, "g");
        ArrayList<Ingredient> ingredients = new ArrayList();
        ingredients.add(ingredient);

        Recipe recipe = new Recipe("Raw Eggs", steps, ingredients);
        assertEquals("1: First add the eggs\n", recipe.getPrintableSteps());
        assertEquals("100.0g Eggs\n", recipe.getPrintableIngredients());
    }

    @Test
    void getStepTest(){
        ArrayList<String> steps = new ArrayList<>();
        for(int x  =0; x < 15; x++){
            steps.add("step " + (x + 1));
        }

        ArrayList<Ingredient> food = new ArrayList<>();
        Recipe r = new Recipe("test", steps, food);

        assertEquals("step 1", r.getStep(1));
        assertEquals("step 2", r.getStep(2));
        assertEquals("step 3", r.getStep(3));
        assertEquals("step 10", r.getStep(10));
        assertEquals("step 15", r.getStep(15));

        assertEquals(15, r.getNumberSteps());

        assertThrows(IllegalArgumentException.class, ()-> r.getStep(100));
        assertThrows(IllegalArgumentException.class, ()-> r.getStep(-1));
        assertThrows(IllegalArgumentException.class, ()-> r.getStep(0));
        assertThrows(IllegalArgumentException.class, ()-> r.getStep(16));
    }

    @Test
    void addStepTest(){
        Recipe r = new Recipe("Test");
        assertEquals(0, r.getNumberSteps());
        r.addStep("step 1");
        assertEquals(1, r.getNumberSteps());
        assertEquals("step 1", r.getStep(1));
        assertEquals("1: step 1\n", r.getPrintableSteps());
        r.addStep("step 2");
        assertEquals(2, r.getNumberSteps());
        assertEquals("step 2", r.getStep(2));
        assertEquals("1: step 1\n2: step 2\n", r.getPrintableSteps());

        r.addStep("new step 2", 2);
        assertEquals("new step 2", r.getStep(2));
        assertEquals("step 2", r.getStep(3));

        r.addStep("Step 4", 4);
        assertEquals("Step 4", r.getStep(4));

        assertThrows(IllegalArgumentException.class, ()-> r.addStep("test fail", 6));
    }

    @Test
    void changeStepTest(){
        Recipe r = new Recipe("test");
        r.addStep("step 1");
        r.addStep("step 2");
        r.editStep(2, "new step 2");
        assertEquals("new step 2", r.getStep(2));
        assertThrows(IllegalArgumentException.class, ()-> r.getStep(3));

        r.editStep(1, "new step 1");
        assertEquals("new step 1", r.getStep(1));
        assertThrows(IllegalArgumentException.class, ()-> r.getStep(3));

        assertThrows(IllegalArgumentException.class, ()-> r.editStep(3, "test"));
        assertThrows(IllegalArgumentException.class, ()-> r.editStep(-3, "test"));
        assertThrows(IllegalArgumentException.class, ()-> r.editStep(0, "test"));
    }

    @Test
    void removeStepTest(){
        Recipe r =  new Recipe("Test");
        for(int x = 0; x < 15 ; x++){
            r.addStep("step " + (x+1));
        }

        assertEquals(15, r.getNumberSteps());
        r.removeStep(10);
        assertEquals(14, r.getNumberSteps());
        assertEquals("step 9",r.getStep(9));
        assertEquals("step 11",r.getStep(10));
    }

    @Test
    void getIngredientTest(){
        ArrayList<String> steps = new ArrayList<>();

        Food food = new Food("Eggs", 100);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for(int x  = 1; x <= 15; x++){
            ingredients.add(new Ingredient(food, x, "g"));
        }

        Recipe r = new Recipe("test", steps, ingredients);

        assertEquals("1.0g Eggs", r.getIngredient(1));
        assertEquals("2.0g Eggs", r.getIngredient(2));
        assertEquals("3.0g Eggs", r.getIngredient(3));
        assertEquals("10.0g Eggs", r.getIngredient(10));
        assertEquals("15.0g Eggs", r.getIngredient(15));

        assertEquals(15, r.getNumberIngredients());

        assertThrows(IllegalArgumentException.class, ()-> r.getIngredient(100));
        assertThrows(IllegalArgumentException.class, ()-> r.getIngredient(-1));
        assertThrows(IllegalArgumentException.class, ()-> r.getIngredient(0));
        assertThrows(IllegalArgumentException.class, ()-> r.getIngredient(16));
    }

    @Test
    void addIngredientTest(){
        Recipe r = new Recipe("Test");
        Food eggs = new Food("Eggs", 100);
        assertEquals(0, r.getNumberIngredients());
        r.addIngredient(new Ingredient(eggs, 1, "g"));
        assertEquals(1, r.getNumberIngredients());
        assertEquals("1.0g Eggs", r.getIngredient(1));
        assertEquals("1.0g Eggs\n", r.getPrintableIngredients());
        r.addIngredient(new Ingredient(eggs, 2, "g"));
        assertEquals(2, r.getNumberIngredients());
        assertEquals("2.0g Eggs", r.getIngredient(2));
        assertEquals("1.0g Eggs\n2.0g Eggs\n", r.getPrintableIngredients());

        r.addIngredient(new Ingredient(eggs, 3, "g"), 2);
        assertEquals("3.0g Eggs", r.getIngredient(2));
        assertEquals("2.0g Eggs", r.getIngredient(3));

        r.addIngredient(new Ingredient(eggs, 4, "g"), 4);
        assertEquals("4.0g Eggs", r.getIngredient(4));

        assertThrows(IllegalArgumentException.class, ()-> r.addIngredient(new Ingredient(eggs, 5, "g"), 6));
    }

    @Test
    void changeIngredientTest(){
        Recipe r = new Recipe("test");
        Food eggs = new Food("Eggs", 100);
        r.addIngredient(new Ingredient(eggs, 1, "g"));
        r.addIngredient(new Ingredient(eggs, 2, "g"));
        r.editIngredient(new Ingredient(eggs, 3, "g"), 2);
        assertEquals("3.0g Eggs", r.getIngredient(2));
        assertThrows(IllegalArgumentException.class, ()-> r.getIngredient(3));

        r.editIngredient(new Ingredient(eggs, 4, "g"), 1);
        assertEquals("4.0g Eggs", r.getIngredient(1));
        assertThrows(IllegalArgumentException.class, ()-> r.getIngredient(3));

        assertThrows(IllegalArgumentException.class, ()-> r.editIngredient(new Ingredient(eggs, 1, "g"), 3));
        assertThrows(IllegalArgumentException.class, ()-> r.editIngredient(new Ingredient(eggs, 1, "g"), -3));
        assertThrows(IllegalArgumentException.class, ()-> r.editIngredient(new Ingredient(eggs, 1, "g"), 0));
    }

    @Test
    void removeIngredientTest(){
        Recipe r =  new Recipe("Test");
        Food food = new Food("Eggs", 100);
        for(int x = 1; x <= 15 ; x++){
            r.addIngredient(new Ingredient(food, x, "g"));
        }

        assertEquals(15, r.getNumberIngredients());
        r.removeIngredient(10);
        assertEquals(14, r.getNumberIngredients());
        assertEquals("9.0g Eggs",r.getIngredient(9));
        assertEquals("11.0g Eggs",r.getIngredient(10));
    }

    @Test
    void addTagTest(){
        Recipe r = new Recipe("Test");
        assertEquals(0, r.numTags());
        r.addTag("tag");
        assertEquals(1, r.numTags());
        r.addTag("tagtwo");
        assertEquals(2, r.numTags());

        assertThrows(IllegalArgumentException.class, ()-> r.addTag("tag"));
        assertThrows(IllegalArgumentException.class, ()-> r.addTag("tag1"));
        assertThrows(IllegalArgumentException.class, ()-> r.addTag(""));

    }

    @Test
    void removeTagTest(){
        Recipe r = new Recipe("Test");
        r.addTag("tag");
        r.addTag("tagtwo");

        r.removeTag("tag");
        assertEquals(1, r.numTags());
        r.removeTag("tagtwo");
        assertEquals(0, r.numTags());


        assertThrows(IllegalArgumentException.class, ()-> r.removeTag("tag"));
        assertThrows(IllegalArgumentException.class, ()-> r.removeTag("tag1"));
        assertThrows(IllegalArgumentException.class, ()-> r.removeTag(""));

    }

    @Test
    void hasTagTest() {
        Recipe r = new Recipe("Test");
        r.addTag("tag");
        r.addTag("tagtwo");

        assertTrue(r.hasTag("tag"));
        assertFalse(r.hasTag("tab"));
        assertTrue(r.hasTag("tagtwo"));
        assertFalse(r.hasTag("hsadfh"));

        assertThrows(IllegalArgumentException.class, ()-> r.hasTag("tag1"));
    }

    @Test
    void getTagsTest(){
        Recipe r = new Recipe("Test");
        r.addTag("tag");
        r.addTag("tagtwo");

        HashSet<String> tagExpected = new HashSet<>();
        tagExpected.add("tag");
        tagExpected.add("tagtwo");

        Iterator<String> itr = r.getTags();
        while(itr.hasNext()){
            assertTrue(tagExpected.contains(itr.next()));
        }
    }

}
