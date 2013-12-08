package halleck.lms.contexts;

import halleck.lms.AppContext;

public class PerThreadContext implements AppContext {

    private static final ThreadLocal<String> user = new ThreadLocal<>();

    @Override
    public String currentUser() {
        return user.get();
    }

    @Override
    public void setCurrentUser(String username) {
        user.set(username);
    }

    @Override
    public void clear() {
        user.remove();
    }
}
