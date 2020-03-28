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

}
