package BDDTests;

import halleck.lms.AppContext;

public class StaticContext implements AppContext {
    private static String user;

    @Override
    public String currentUser() {
        return user;
    }

    @Override
    public void setCurrentUser(String username) {
        user = username;
    }

    @Override
    public void clear() {
        user = null;
    }

    public static void reset() {
        user = null;
    }
}
