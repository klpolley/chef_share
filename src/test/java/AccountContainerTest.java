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

        //try to update username of acct that doesn't exist
        assertThrows(IllegalArgumentException.class, ()->accounts.updateUsername("user12345", "thebestuser"));

        //update with bad username
        assertThrows(IllegalArgumentException.class, ()->accounts.updateUsername("username", "user"));
        assertThrows(IllegalArgumentException.class, ()->accounts.updateUsername("username", "?username"));
        assertThrows(IllegalArgumentException.class, ()->accounts.updateUsername("username", "1234!@#$"));
    }

    @Test
    void updatePasswordTest() {
        AccountContainer accounts = new AccountContainer();
        accounts.createAccount("username", "password", "gourmet chef");
        accounts.createAccount("user12345", "p.a!s#w%o^r&d", "secure chef");
        accounts.createAccount("1broccoli", "get1those1greens", "");
        Account acct = accounts.getAccount("username");
        Account acct2 = accounts.getAccount("user12345");
        Account acct3 = accounts.getAccount("1broccoli");

        //update username without affecting other accounts
        accounts.updatePassword("username", "betterpassword?123");
        assertTrue(acct.confirmPassword("betterpassword?123"));
        assertTrue(acct2.confirmPassword("p.a!s#w%o^r&d"));
        assertTrue(acct3.confirmPassword("get1those1greens"));

        //update password to same as others - works
        accounts.updatePassword("user12345", "get1those1greens");
        assertTrue(acct2.confirmPassword("get1those1greens"));

        //try to update username of acct that doesn't exist
        assertThrows(IllegalArgumentException.class, ()->accounts.updatePassword("fakeuser", "password"));

        //update with bad password
        assertThrows(IllegalArgumentException.class, ()->accounts.updatePassword("user12345", "short!"));
    }

    @Test
    void updateBioTest() {
        AccountContainer accounts = new AccountContainer();
        accounts.createAccount("username", "password", "gourmet chef");
        accounts.createAccount("user12345", "p.a!s#w%o^r&d", "secure chef");
        accounts.createAccount("1broccoli", "get1those1greens", "");

        //update bio without impacting other accounts
        accounts.updateBiography("1broccoli", "i <3 broccoli");
        assertEquals("i <3 broccoli", accounts.getUserBio("1broccoli"));
        assertEquals("gourmet chef", accounts.getUserBio("username"));
        assertEquals("secure chef", accounts.getUserBio("user12345"));

        //update to empty
        accounts.updateBiography("user12345", "");
        assertEquals("", accounts.getUserBio("user12345"));

        //update to existing bio (fine)
        accounts.updateBiography("username", "i <3 broccoli");
        assertEquals("i <3 broccoli", accounts.getUserBio("username"));

        //try to update username of acct that doesn't exist
        assertThrows(IllegalArgumentException.class, ()->accounts.updateBiography("fakeuser", "description"));
    }

    @Test
    void loginTest() {
        AccountContainer accounts = new AccountContainer();
        accounts.createAccount("username", "password", "gourmet chef");
        accounts.createAccount("user12345", "p.a!s#w%o^r&d", "secure chef");
        accounts.createAccount("1broccoli", "get1those1greens", "");

        //login to account that does not exist
        assertThrows(IllegalArgumentException.class, ()->accounts.login("fakeuser", "password"));

        //account login with bad password
        assertThrows(IllegalArgumentException.class, ()->accounts.login("username", "notcorrect"));

        //good login
        accounts.login("username", "password");

        //login while account is logged in
        assertThrows(IllegalArgumentException.class, ()->accounts.login("username", "password"));
        assertThrows(IllegalArgumentException.class, ()->accounts.login("user12345", "p.a!s#w%o^r&d"));

    }

}
