package halleck.lms;

import com.google.inject.Inject;

import java.util.function.Predicate;

public class FeatureChecker implements Predicate<Feature> {

    private final Settings settings;

    @Inject
    public FeatureChecker(Settings settings){
        this.settings = settings;
    }


    @Override
    public boolean test(Feature feature) {
        return settings.getEnabledFeatures().contains(feature);
    }
}
