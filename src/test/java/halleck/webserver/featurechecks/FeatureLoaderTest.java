package halleck.webserver.featurechecks;

import halleck.lms.AppContext;
import halleck.lms.Feature;
import halleck.lms.FeatureChecker;
import halleck.mocks.MockContext;
import halleck.mocks.MockSettings;
import halleck.webserver.ResourceLoader;
import org.junit.Test;

import java.util.Collection;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class FeatureLoaderTest {

    @Test
    public void canLoadFeaturePreferences() throws Exception {
        MockSettings settings = new MockSettings();
        settings.featureLocation = "resource://test-features.json";
        FeatureLoader loader = new FeatureLoader(settings, new ResourceLoader(), new MockContext());

        FeatureChecker result = loader.get();

        Collection<Predicate<AppContext>> rules = result.getMap().get(Feature.GEO_CITIES);
        assertEquals(3, rules.size());
    }
}