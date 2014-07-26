package halleck.lms;

import com.google.common.base.Strings;

import java.io.Serializable;
import java.util.Locale;
import java.util.Optional;

public class CurrentUser implements Serializable {
    private final String userName;
    private final Locale lanaguage;
    private final Optional<String> sessionId;

    public static final CurrentUser LOGGED_OUT_USER = new CurrentUser("");

    public CurrentUser(String userName, Locale lanaguage, String sessionId){
        this.userName = userName;
        this.lanaguage = lanaguage;
        this.sessionId = Optional.ofNullable(sessionId);
    }

    public CurrentUser(String userName) {
        this(userName, Locale.ENGLISH, null);
    }

    public String getUserName() {
        return userName;
    }

    public Locale getLocale() {
        return lanaguage;
    }

    public boolean isAuthenticated(){
        return sessionId.isPresent();
    }

    public static CurrentUser tryGet(CurrentUser user){
        return Optional.ofNullable(user)
                       .orElseGet(() -> CurrentUser.LOGGED_OUT_USER);
    }

    public String getSessionId() {
        return sessionId.get();
    }
}
