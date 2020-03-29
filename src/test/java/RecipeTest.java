import org.junit.jupiter.api.Test;

import java.nio.channels.FileLockInterruptionException;
import java.util.ArrayList;

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
        assertEquals("1:  First add the eggs\n", recipe.getPrintableSteps());
        assertEquals("Eggs\n", recipe.getPrintableIngredients());
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
        assertEquals("1:  step 1\n", r.getPrintableSteps());
        r.addStep("step 2");
        assertEquals(2, r.getNumberSteps());
        assertEquals("step 2", r.getStep(2));
        assertEquals("1:  step 1\n2:  step 2\n", r.getPrintableSteps());

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


}
