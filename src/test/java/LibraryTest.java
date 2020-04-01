import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    @Test
    void libraryCreationTest(){
        Library foodLibrary = new Library();

        assertEquals(0, foodLibrary.getLength());
    }

    @Test
    void addFoodtoLib() {
        Library foodLibrary = new Library();

        //add a bunch of food
        Food pickle = new Food("pickle", 550);
        foodLibrary.addFood(pickle);
        assertEquals(1, foodLibrary.getLength());

        Food cookie = new Food("cookie", 800);
        foodLibrary.addFood(cookie);
        assertEquals(2, foodLibrary.getLength());

        Food banana = new Food("banana", 250);
        foodLibrary.addFood(banana);
        assertEquals(3, foodLibrary.getLength());
    }
        @Test
        void isFoodPresentTest(){

            Library foodLibrary = new Library();

            //add a bunch of food
            Food pickle = new Food("pickle", 550);
            foodLibrary.addFood(pickle);

            Food cookie = new Food("cookie", 800);
            foodLibrary.addFood(cookie);

            Food banana = new Food("banana", 250);
            foodLibrary.addFood(banana);

            //test foods that are present
            assertTrue(foodLibrary.isFoodPresent("pickle"));
            assertTrue(foodLibrary.isFoodPresent("cookie"));
            assertTrue(foodLibrary.isFoodPresent("banana"));
            //test foods that aren't present
            assertFalse(foodLibrary.isFoodPresent("apple"));
            assertFalse(foodLibrary.isFoodPresent("cheese"));
            assertFalse(foodLibrary.isFoodPresent("bananas")); //checking that very close string isn't considered true

    }

}
