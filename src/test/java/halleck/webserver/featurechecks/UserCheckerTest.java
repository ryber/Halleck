package halleck.webserver.featurechecks;

import com.google.common.collect.Sets;
import halleck.lms.AppContext;
import halleck.lms.CurrentUser;
import halleck.mocks.MockContext;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class UserCheckerTest {

    @Test
    public void willTestTrueIfSuppliedNamesAreInTheContext() {
        UserChecker check = new UserChecker(Sets.newHashSet("Feyd"));

        AppContext context = new MockContext();
        context.setCurrentUser(new CurrentUser("Feyd", null,null));

        assertEquals(true, check.test(context));
    }

    @Test
    public void willTestFalseIfContextNameIsNotInSet() {
        UserChecker check = new UserChecker(Sets.newHashSet("Feyd"));

        AppContext context = new MockContext();
        context.setCurrentUser(new CurrentUser("Paul", null,null));

        assertEquals(false, check.test(context));
    }

}