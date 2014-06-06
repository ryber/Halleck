package halleck.mocks;

import halleck.lms.AppContext;

public class MockContext implements AppContext{
    private String username;

    @Override
    public String currentUser() {
        return this.username ;
    }

    @Override
    public void setCurrentUser(String username) {
        this.username = username;
    }

    @Override
    public void clear() {
        this.username = null;
    }
}
