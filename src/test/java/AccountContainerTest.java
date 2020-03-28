import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountContainerTest {

    @Test
    void createAccountTest() {
        AccountContainer accounts = new AccountContainer();

        //should be all good
        accounts.createAccount("username", "password", "gourmet chef");
        assertTrue(accounts.accountExists("username"));
        accounts.createAccount("user12345", "p.a!s#w%o^r&d", "secure chef");
        assertTrue(accounts.accountExists("user12345"));
        accounts.createAccount("1broccoli", "get1those1greens", "");
        assertTrue(accounts.accountExists("username"));
        assertTrue(accounts.accountExists("user12345"));
        assertTrue(accounts.accountExists("1broccoli"));

        //same password/bio is fine
        accounts.createAccount("newuser", "password", "haha");
        assertTrue(accounts.accountExists("newuser"));
        accounts.createAccount("anothernewuser", "suchanewuser", "gourmet chef");
        assertTrue(accounts.accountExists("newuser"));

        //bad - username already exists
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("username", "password", "gourmet chef"));
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("username", "imsosecure", ""));
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("user12345", "password", "I'm so cool"));
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("1broccoli", "get1those1greens", "I love cake"));

        //bad - invalid username and/or password
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("user", "password", ""));
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("thecoolestuserevertoexist", "password", ""));
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("username", "pass", ""));
        assertThrows(IllegalArgumentException.class, ()->accounts.createAccount("user", "pass", "Rules are for losers"));
    }

    @Test
    void removeAccountTest() {
        AccountContainer accounts = new AccountContainer();
        accounts.createAccount("username", "password", "gourmet chef");
        accounts.createAccount("user12345", "p.a!s#w%o^r&d", "secure chef");
        accounts.createAccount("1broccoli", "get1those1greens", "");

        //remove existing account
        accounts.removeAccount("username");
        assertFalse(accounts.accountExists("username"));
        assertTrue(accounts.accountExists("user12345"));
        assertTrue(accounts.accountExists("1broccoli"));

        //try to remove account that is not in container
        assertThrows(IllegalArgumentException.class, ()->accounts.removeAccount("username"));
        assertThrows(IllegalArgumentException.class, ()->accounts.removeAccount("notreal"));

        accounts.removeAccount("user12345");
        assertFalse(accounts.accountExists("username"));
        assertFalse(accounts.accountExists("user12345"));
        assertTrue(accounts.accountExists("1broccoli"));

        assertThrows(IllegalArgumentException.class, ()->accounts.removeAccount("user12345"));

        accounts.removeAccount("1broccoli");
        assertFalse(accounts.accountExists("username"));
        assertFalse(accounts.accountExists("user12345"));
        assertFalse(accounts.accountExists("1broccoli"));

        //try to remove from empty container
        assertThrows(IllegalArgumentException.class, ()->accounts.removeAccount("1broccoli"));
        assertThrows(IllegalArgumentException.class, ()->accounts.removeAccount("neverexisted"));
    }

    @Test
    void getBioTest() {
        AccountContainer accounts = new AccountContainer();
        accounts.createAccount("username", "password", "gourmet chef");
        accounts.createAccount("user12345", "p.a!s#w%o^r&d", "secure chef");
        accounts.createAccount("1broccoli", "get1those1greens", "");

        assertEquals("gourmet chef", accounts.getUserBio("username"));
        assertEquals("secure chef", accounts.getUserBio("user12345"));
        assertEquals("", accounts.getUserBio("1broccoli"));

        //account that does not exist throws exception
        assertThrows(IllegalArgumentException.class, ()->accounts.getUserBio("nonexistent"));
    }

    @Test
    void updateUserTest() {
        AccountContainer accounts = new AccountContainer();
        accounts.createAccount("username", "password", "gourmet chef");
        accounts.createAccount("user12345", "p.a!s#w%o^r&d", "secure chef");
        accounts.createAccount("1broccoli", "get1those1greens", "");

        //update username without affecting other accounts
        accounts.updateUsername("username", "newname");
        assertTrue(accounts.accountExists("newname"));
        assertFalse(accounts.accountExists("username"));
        assertEquals("gourmet chef", accounts.getUserBio("newname"));
        assertTrue(accounts.accountExists("user12345"));
        assertTrue(accounts.accountExists("1broccoli"));

        //update username to one that was previously taken
        accounts.updateUsername("user12345", "username");
        assertTrue(accounts.accountExists("username"));
        assertFalse(accounts.accountExists("user12345"));
        assertEquals("secure chef", accounts.getUserBio("username"));
        assertTrue(accounts.accountExists("newname"));
        assertTrue(accounts.accountExists("1broccoli"));

        //try to update username to name that already exists
        assertThrows(IllegalArgumentException.class, ()->accounts.updateUsername("username", "1broccoli"));
        assertThrows(IllegalArgumentException.class, ()->accounts.updateUsername("newname", "username"));
    }

}
