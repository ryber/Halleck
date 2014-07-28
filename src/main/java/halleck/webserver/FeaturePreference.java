package halleck.webserver;

import com.google.common.collect.Sets;
import halleck.lms.Feature;

import java.io.Serializable;
import java.util.Locale;
import java.util.Set;

public class FeaturePreference implements Serializable {
    private Feature feature;
    private Set<String> userNames = Sets.newHashSet();
    private Set<Locale> locales = Sets.newHashSet();
    private Integer percent;
    private boolean released = false;

    public Set<String> getUserNames() {
        return userNames;
    }

    public Feature getFeature() {
        return feature;
    }

    public Set<Locale> getLocales() {
        return locales;
    }

    public Integer getPercent() {
        return percent;
    }

    public boolean isReleased() {
        return released;
    }
}
