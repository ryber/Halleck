package halleck.webserver;

import halleck.lms.Feature;

import java.io.Serializable;
import java.util.Set;

public class FeaturePreference implements Serializable {
    private Feature feature;
    private Set<String> userNames;

    public Set<String> getUserNames() {
        return userNames;
    }

    public Feature getFeature() {
        return feature;
    }
}
