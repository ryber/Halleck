package halleck.lms.contexts;

import halleck.lms.AppContext;
import halleck.lms.CurrentUser;

public class PerThreadContext implements AppContext {

    private static final ThreadLocal<CurrentUser> user = new ThreadLocal<>();

    @Override
    public CurrentUser currentUser() {
        return CurrentUser.tryGet(user.get());
    }

    @Override
    public void setCurrentUser(CurrentUser username) {
        user.set(username);
    }

    @Override
    public void clear() {
        user.remove();
    }
}
