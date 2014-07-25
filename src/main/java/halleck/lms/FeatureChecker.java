package halleck.lms;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

import java.util.function.Predicate;

public class FeatureChecker implements Predicate<Feature> {

    private Multimap<Feature, Predicate<Feature>> featureChecks = HashMultimap.create();

    @Override
    public boolean test(Feature feature) {
        return featureChecks.get(feature)
                            .stream()
                            .anyMatch(c -> c.test(feature));
    }

    public void add(Feature feature, Predicate<Feature> predicate) {
        featureChecks.put(feature, predicate);
    }
}
