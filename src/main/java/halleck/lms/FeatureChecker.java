package halleck.lms;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

import java.util.Collection;
import java.util.function.Predicate;

public class FeatureChecker implements Predicate<Feature> {

    private Multimap<Feature, Predicate<AppContext>> featureChecks = HashMultimap.create();
    private AppContext context;

    public FeatureChecker(AppContext context){

        this.context = context;
    }

    @Override
    public boolean test(Feature feature) {
        return featureChecks.get(feature)
                            .stream()
                            .anyMatch(c -> c.test(context));
    }

    public void add(Feature feature, Predicate<AppContext> predicate) {
        featureChecks.put(feature, predicate);
    }
}
