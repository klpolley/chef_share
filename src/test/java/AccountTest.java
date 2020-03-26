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
        Account acct1 = new Account("username", "password1234", "the best user");
        assertEquals("username", acct1.getUsername());
        assertNotEquals("notmyuser", acct1.getUsername());
        assertTrue(acct1.confirmPassword("password1234"));
        assertFalse(acct1.confirmPassword("badpassword"));
        assertEquals("the best user", acct1.getBiography());
        assertNotEquals("the worst user", acct1.getBiography());

        Account acct2 = new Account("unknown", "#password!!", "");
        assertEquals("unknown", acct2.getUsername());
        assertNotEquals("knownuser", acct2.getUsername());
        assertTrue(acct2.confirmPassword("#password!!"));
        assertFalse(acct2.confirmPassword("notminelol"));
        assertEquals("", acct2.getBiography());
        assertNotEquals("description", acct2.getBiography());

        assertThrows(IllegalArgumentException.class, ()-> acct2.setUsername("bad"));
        assertThrows(IllegalArgumentException.class, ()-> acct2.setUsername("nogood$$$"));
        acct2.setUsername("newuser");
        assertEquals("newuser", acct2.getUsername());
        assertNotEquals("unknown", acct2.getUsername());

        acct2.setPassword("newpassword");
        assertThrows(IllegalArgumentException.class, ()-> acct2.setPassword("short"));
        assertTrue(acct2.confirmPassword("newpassword"));
        assertFalse(acct2.confirmPassword("#password!!"));

        acct2.setBiography("chef");
        assertEquals("chef", acct2.getBiography());
        assertNotEquals("", acct2.getBiography());


        assertThrows(IllegalArgumentException.class, ()-> new Account("user", "password", ""));
        assertThrows(IllegalArgumentException.class, ()-> new Account("username", "pass1", ""));
        assertThrows(IllegalArgumentException.class, ()-> new Account("user$name", "pass$word", ""));
    }


}
