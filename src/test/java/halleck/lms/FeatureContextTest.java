package halleck.lms;

import org.junit.Test;

import static org.junit.Assert.*;

public class FeatureContextTest {
    @Test
    public void byDefaultEverythingIsFalse() {
        assertFalse(FeatureContext.check(Feature.DOJOS));
    }

    @Test
    public void ifFeatureCheckerSaysItsOkThenItIs() {
        FeatureContext.setChecker((f) -> true);

        assertTrue(FeatureContext.check(Feature.DOJOS));
    }
}