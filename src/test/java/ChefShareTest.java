public class ChefShareTest {

    public static ChefShare createTestApp() {
        ChefShare app = new ChefShare();
        app.addAccount("username1", "password1", "");
        app.addAccount("username2", "password2", "");
        app.addAccount("username3", "password3", "");
        return app;
    }

}
