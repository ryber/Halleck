package halleck.lms;

import com.google.common.collect.ImmutableList;
import halleck.api.Settings;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FeatureCheckerTest {

    @Test
    public void willCheckAgainstSettingsIfFeatureIsOn() throws Exception {
        Settings settings = mock(Settings.class);
        when(settings.getEnabledFeatures()).thenReturn(ImmutableList.of(Feature.DOJOS));

        FeatureChecker check = new FeatureChecker(settings);

        assertTrue(check.test(Feature.DOJOS));
    }

    @Test
    public void willNotReturnTrueIfFeatureIsNotInList() throws Exception {
        Settings settings = mock(Settings.class);
        when(settings.getEnabledFeatures()).thenReturn(ImmutableList.of());

        FeatureChecker check = new FeatureChecker(settings);

        assertFalse(check.test(Feature.DOJOS));
    }
}