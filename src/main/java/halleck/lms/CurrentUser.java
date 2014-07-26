package halleck.lms;

import com.google.common.base.Strings;

import java.io.Serializable;
import java.util.Locale;
import java.util.Optional;

public class CurrentUser implements Serializable {
    private final String userName;
    private final Locale lanaguage;

    public static final CurrentUser LOGGED_OUT_USER = new CurrentUser("");

    public CurrentUser(String userName, Locale lanaguage){
        this.userName = userName;
        this.lanaguage = lanaguage;
    }

    public CurrentUser(String userName) {
        this(userName, Locale.ENGLISH);
    }

    public String getUserName() {
        return userName;
    }

    public Locale getLocale() {
        return lanaguage;
    }

    public boolean isAuthenticated(){
        return !Strings.isNullOrEmpty(userName);
    }

    public static CurrentUser tryGet(CurrentUser user){
        return Optional.ofNullable(user)
                       .orElseGet(() -> CurrentUser.LOGGED_OUT_USER);
    }
}
