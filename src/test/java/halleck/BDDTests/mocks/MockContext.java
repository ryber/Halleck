package halleck.BDDTests.mocks;

import halleck.lms.AppContext;

public class MockContext implements AppContext{
    @Override
    public String currentUser() {
        return null;
    }

    @Override
    public void setCurrentUser(String username) {

    }

    @Override
    public void clear() {

    }
}
