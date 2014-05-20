package halleck.lms;

import com.google.inject.Inject;
import halleck.api.Settings;

public class ContextChecker implements FeatureChecker {

    private final AppContext appContext;
    private final Settings settings;

    @Inject
    public ContextChecker(AppContext appContext,
                          Settings settings){
        this.appContext = appContext;
        this.settings = settings;
    }

    @Override
    public boolean check(Feature feature) {
        return false;
    }
}
