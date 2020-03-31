import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    @Test
    void libraryCreationTest(){
        Library foodLibrary = new Library();

        assertEquals(0, foodLibrary.getLength());
    }

    @Test
    void addFoodtoLib(){
        Library foodLibrary = new Library();

        //add a bunch of food
        Food pickle = new Food("pickle", 550);
        foodLibrary.addFood(pickle);
        assertEquals(1,foodLibrary.getLength());

        Food cookie = new Food("cookie", 800);
        foodLibrary.addFood(cookie);
        assertEquals(2,foodLibrary.getLength());

        Food banana = new Food("banana", 250);
        foodLibrary.addFood(banana);
        assertEquals(3,foodLibrary.getLength());

        //test that each food name is correct
        String foodName1 = foodLibrary.getFoodName(0);
        assertTrue("pickle" == foodName1);

        String foodName2 = foodLibrary.getFoodName(1);
        assertTrue("cookie" == foodName2);

        String foodName3 = foodLibrary.getFoodName(2);
        assertTrue("banana" == foodName3);
    }

}
