import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FoodTest {

    @Test
    void constructorTest(){

        //food with positive calorie amount
        Food pickle = new Food("pickle", 550);
        Food cookie = new Food("cookie", 800);
        Food banana = new Food("banana", 250);

        assertEquals("pickle", pickle.getName());
        assertEquals(550, pickle.getCalories());
        assertEquals("cookie", cookie.getName());
        assertEquals(800, cookie.getCalories());
        assertEquals("banana", banana.getName());
        assertEquals(250, banana.getCalories());

        //negative calorie amount
        assertThrows(IllegalArgumentException.class, ()-> new Food("squash", -400));
        assertThrows(IllegalArgumentException.class, ()-> new Food("brownie", -900));

        //zero calorie amount
        assertThrows(IllegalArgumentException.class, ()-> new Food("squash", 0));

        //empty string name
        //no space
        assertThrows(IllegalArgumentException.class, ()-> new Food("", 700));
        //one space
        assertThrows(IllegalArgumentException.class, ()-> new Food(" ", 700));
    }


}
