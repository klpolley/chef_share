import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class AccountTest {

    @Test
    void isUserValidTest() {
        //valid username has between 6 and 15 characters, alphanumeric or underscore
        //taken usernames will be handled by the account container class

        //too short
        assertFalse(Account.isUserValid(""));
        assertFalse(Account.isUserValid("m_e"));
        assertFalse(Account.isUserValid("user1"));

        //too long
        assertFalse(Account.isUserValid("characters151515"));
        assertFalse(Account.isUserValid("whatalongusernamehowcoulditbe"));
        assertFalse(Account.isUserValid("the_longest_username_ever_to_be_written_in_these_tests"));

        //just right
        assertTrue(Account.isUserValid("lenof6"));
        assertTrue(Account.isUserValid("my_username"));
        assertTrue(Account.isUserValid("fifteencharswoo"));

        //bad characters, fine length
        assertFalse(Account.isUserValid("$$$$$$"));
        assertFalse(Account.isUserValid("hello#bad"));
        assertFalse(Account.isUserValid("my.username"));
    }

    @Test
    void constructorTest() {


    }


}
