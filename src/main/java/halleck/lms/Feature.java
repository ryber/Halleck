package halleck.lms;


import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public enum Feature {

    VIDEOS,
    EMBEDDED_CONTENT,
    LEARING_DOJOS,
    GEO_CITIES;

    public boolean isActive() {
        return FeatureContext.check(this);
    }

    public static Set<Feature> getAllEnabled() {
        return Arrays.stream(Feature.values())
                     .filter(Feature::isActive)
                     .collect(toSet());
    }
}
