package halleck.lms;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FeatureCheckerTest {

    @Test
    public void willReturnFalseByDefault() throws Exception {
        FeatureChecker check = new FeatureChecker();

        assertFalse(check.test(Feature.LEARING_DOJOS));
    }

    @Test
    public void willReturnTrueIfMatchesAndTrue() {
        FeatureChecker check = new FeatureChecker();
        check.add(Feature.LEARING_DOJOS, (f) -> true);

        assertTrue(check.test(Feature.LEARING_DOJOS));
    }

    @Test
    public void willReturnTrueIfAnyMatchesAndTrue() {
        FeatureChecker check = new FeatureChecker();
        check.add(Feature.LEARING_DOJOS, (f) -> false);
        check.add(Feature.LEARING_DOJOS, (f) -> true);

        assertTrue(check.test(Feature.LEARING_DOJOS));
    }
}