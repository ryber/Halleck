package halleck.webserver.featurechecks;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Provider;
import halleck.lms.AppContext;
import halleck.lms.FeatureChecker;
import halleck.lms.Settings;
import halleck.webserver.FeaturePreference;
import halleck.webserver.ResourceLoader;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class FeatureLoader implements Provider<FeatureChecker> {
    private Settings settings;
    private ResourceLoader loader;
    private AppContext context;

    @Inject
    public FeatureLoader(Settings settings, ResourceLoader loader, AppContext context){
        this.settings = settings;
        this.loader = loader;
        this.context = context;
    }

    public Collection<FeaturePreference> load() {
        String featureLoadLocation = settings.getFeatureLoadLocation();
        Optional<String> content = loader.getContent(featureLoadLocation);

        if(content.isPresent()){
            System.out.println("Loading feature preferences from " + featureLoadLocation);
            return createPreferences(content.get());
        }
        return Lists.newArrayList();
    }

    private Collection<FeaturePreference> createPreferences(String content) {
        Gson g = new Gson();
        return g.fromJson(content, new TypeToken<List<FeaturePreference>>(){}.getType());
    }

    @Override
    public FeatureChecker get() {
        FeatureChecker checker = new FeatureChecker(context);
        load().forEach((p) -> {
            addUserChecks(checker, p);
        });

        return checker;
    }

    private void addUserChecks(FeatureChecker checker, FeaturePreference p) {
        if(!p.getUserNames().isEmpty()){
            checker.add(p.getFeature(), new UserChecker(p.getUserNames()));
        }

        if(!p.getLocales().isEmpty()){
            checker.add(p.getFeature(), new LocaleChecker(p.getLocales()));
        }

        if(!(p.getPercent() == null)){
            checker.add(p.getFeature(), new PercentPassChecker(p.getPercent()));
        }
    }


}
