package halleck.webserver.featurechecks;

import com.google.common.collect.Sets;
import halleck.lms.AppContext;
import halleck.lms.CurrentUser;
import halleck.mocks.MockContext;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class LocaleCheckerTest {

    @Test
    public void willTestTrueIfSuppliedLocalesAreInTheContext() {
        LocaleChecker check = new LocaleChecker(Sets.newHashSet(Locale.CANADA));

        AppContext context = new MockContext();
        context.setCurrentUser(new CurrentUser("Bob", Locale.CANADA,null));

        assertEquals(true, check.test(context));
    }

    @Test
    public void willTestFalseIfCOntextLocaleIsNotInSet() {
        LocaleChecker check = new LocaleChecker(Sets.newHashSet(Locale.CANADA));

        AppContext context = new MockContext();
        context.setCurrentUser(new CurrentUser("Bob", Locale.CHINA,null));

        assertEquals(false, check.test(context));
    }
}