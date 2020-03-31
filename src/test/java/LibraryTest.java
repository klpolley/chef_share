import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    @Test
    void libraryCreationTest(){
        Library foodLibrary = new Library();

        assertEquals(0, foodLibrary.getLength());
    }

}
