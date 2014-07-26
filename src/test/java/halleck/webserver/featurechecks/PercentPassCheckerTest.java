package halleck.webserver.featurechecks;

import halleck.lms.AppContext;
import halleck.lms.CurrentUser;
import halleck.mocks.MockContext;
import org.junit.Test;

import static org.junit.Assert.*;

public class PercentPassCheckerTest {

    @Test
    public void willPassIfPercentIsGreaterThanRandomNumber() {
        PercentPassChecker checker = new PercentPassChecker(20, new MockDie(15));

        AppContext context = getValidSession("foo");

        assertEquals(true, checker.test(context));
    }

    @Test
    public void willFailIfLessThanRandomNumber() {
        PercentPassChecker checker = new PercentPassChecker(20, new MockDie(25));

        AppContext context = getValidSession("foo");

        assertEquals(false, checker.test(context));
    }

    @Test
    public void willCacheResult() {
        MockDie die = new MockDie(15);
        PercentPassChecker checker = new PercentPassChecker(20, die);

        AppContext context = getValidSession("foo");

        assertEquals(true, checker.test(context));
        die.alwaysRolls = 55;
        assertEquals(true, checker.test(context));
    }

    @Test
    public void alwaysReturnsFalseForUnAuthenticatedUsers() {
        PercentPassChecker checker = new PercentPassChecker(20, new MockDie(15));

        AppContext context = getValidSession(null);

        assertEquals(false, checker.test(context));
    }

    private AppContext getValidSession(String id) {
        AppContext context = new MockContext();
        context.setCurrentUser(new CurrentUser("Fred", null, id));
        return context;
    }


    private static class MockDie implements Die {

        public int alwaysRolls;

        public MockDie(int alwaysRolls){
            this.alwaysRolls = alwaysRolls;
        }
        
        @Override
        public int roll() {
            return alwaysRolls;
        }
    }
}