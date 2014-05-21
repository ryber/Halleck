package halleck.lms;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FeatureContextTest {
    @After
    public void tearDown() throws Exception {
        FeatureContext.clear();
    }

    @Test
    public void byDefaultEverythingIsFalse() {
        FeatureContext.clear();
        assertEquals(false, FeatureContext.check(Feature.DOJOS));
    }

    @Test
    public void ifFeatureCheckerSaysItsOkThenItIs() {
        FeatureContext.setChecker((f) -> true);

        assertTrue(FeatureContext.check(Feature.DOJOS));
    }
}