package halleck.webserver;

import halleck.lms.Feature;

import java.io.Serializable;
import java.util.Locale;
import java.util.Set;

public class FeaturePreference implements Serializable {
    private Feature feature;
    private Set<String> userNames;
    private Set<Locale> locales;
    

    public Set<String> getUserNames() {
        return userNames;
    }

    public Feature getFeature() {
        return feature;
    }

    public Set<Locale> getLocales() {
        return locales;
    }
}
