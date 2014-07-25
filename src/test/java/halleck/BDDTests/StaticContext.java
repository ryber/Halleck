package halleck.BDDTests;

import halleck.lms.AppContext;
import halleck.lms.CurrentUser;

public class StaticContext implements AppContext {
    private static CurrentUser user;

    @Override
    public CurrentUser currentUser() {
        return CurrentUser.tryGet(user);
    }

    @Override
    public void setCurrentUser(CurrentUser username) {
        user = username;
    }

    @Override
    public void clear() {

    }

    public static void reset() {
        user = null;
    }
}