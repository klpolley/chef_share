public class Account {

    private String username;
    private String password;
    private String biography;
    //private List<Recipe> recipes;

    public Account(String username, String password, String bio) throws IllegalArgumentException {
        if (!isUserValid(username)) throw new IllegalArgumentException("Username must be between 6 and 15 characters, alphanumeric or _ only");
        if (!isPasswordValid(password)) throw new IllegalArgumentException("Password must be at least 8 characters long");
        this.username = username;
        this.password = password;
        this.biography = bio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUser) throws IllegalArgumentException {
        if (!isUserValid(newUser)) throw new IllegalArgumentException("Username must be between 6 and 15 characters, alphanumeric or _ only");
        this.username = newUser;
    }

    public boolean confirmPassword(String password) {
        if (password.equals(this.password)) return true;
        else return false;
    }

    public void setPassword(String newPassword) {
        if (!isPasswordValid(newPassword)) throw new IllegalArgumentException("Password must be at least 8 characters long");
        this.password = newPassword;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String newBio) {
        this.biography = newBio;
    }

    public static boolean isUserValid(String username){
        //valid username has between 6 and 15 characters, alphanumeric or underscore
        //taken usernames will be handled by the account container class
        int length = username.length();
        if (length < 6 || length > 15) {
            return false;
        } else {
            for (int i = 0; i < length; i++) {
                //nums = 48-57, upper = 65-90, lower = 97-122, _ = 95
                char c = username.charAt(i);
                if (c > 47 && c < 58);
                else if (c > 64 && c < 91);
                else if (c > 96 && c < 123);
                else if (c == 95);
                else return false;
            }
            return true;
         }
    }

    public static boolean isPasswordValid(String password) {
        //valid password is at least 8 characters long
        //alphanumeric and special characters allowed
        int length = password.length();
        if (length < 8) {
            return false;
        } else {
            return true;
        }
    }

}