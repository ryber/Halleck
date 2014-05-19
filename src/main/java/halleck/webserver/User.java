package halleck.webserver;

import static com.google.common.base.Strings.isNullOrEmpty;


public class User {
    public static final User EMPTY = new User("", false);
    public String name;
    public boolean isAdmin;
    public boolean isLoggedIn;

    public User(String userName, boolean isAdmin){
        this.name = userName;
        this.isLoggedIn = !isNullOrEmpty(name);
        this.isAdmin = isAdmin;
    }
}
