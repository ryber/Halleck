package halleck.mocks;

import halleck.lms.AppContext;
import halleck.lms.CurrentUser;

import static halleck.lms.CurrentUser.tryGet;

public class MockContext implements AppContext{
    private CurrentUser user;

    @Override
    public CurrentUser currentUser() {
        return tryGet(this.user);
    }

    @Override
    public void setCurrentUser(CurrentUser username) {
        this.user = username;
    }

    @Override
    public void clear() {
        this.user = null;
    }
}
