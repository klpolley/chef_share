import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class AccountTest {

    @Test
    void isUserValidTest() {
        //too short
        assertFalse(Account.isUserValid(""));
        assertFalse(Account.isUserValid("m_e"));
        assertFalse(Account.isUserValid("User1"));

        //too long
        assertFalse(Account.isUserValid("Characters151515"));
        assertFalse(Account.isUserValid("whatalongusernamehowcoulditbe"));
        assertFalse(Account.isUserValid("the_longest_username_ever_to_be_written_in_these_tests"));

        //just right
        assertTrue(Account.isUserValid("LenOf6"));
        assertTrue(Account.isUserValid("1_username"));
        assertTrue(Account.isUserValid("fifteencharzWOO"));

        //bad characters, fine length
        assertFalse(Account.isUserValid("$$$$$$"));
        assertFalse(Account.isUserValid("hello#bad"));
        assertFalse(Account.isUserValid("my.Username"));
    }


    @Test
    void isPasswordValidTest() {
        //too short
        assertFalse(Account.isPasswordValid(""));
        assertFalse(Account.isPasswordValid("pass"));
        assertFalse(Account.isPasswordValid("badnum7"));

        //fine length alphanumeric
        assertTrue(Account.isPasswordValid("password"));
        assertTrue(Account.isPasswordValid("alongerpassword12345"));
        assertTrue(Account.isPasswordValid("thelongestpasswordyouwillneverguess"));

        //fine length with special characters
        assertTrue(Account.isPasswordValid("pass_word"));
        assertTrue(Account.isPasswordValid("#pass!word41"));
        assertTrue(Account.isPasswordValid("00password55"));
    }

    @Test
    void constructorTest() {


    }


}
