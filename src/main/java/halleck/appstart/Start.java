package halleck.appstart;

import com.google.inject.Guice;
import com.google.inject.Injector;
import halleck.lms.FeatureChecker;
import halleck.lms.FeatureContext;
import halleck.webserver.CourseLoader;
import halleck.webserver.HttpRouts;

public class Start {

    public static void main(String[] args) {
        SettingsProvider.setCustomConfigFile(args);
        startServer(new BindingModule());
    }

    public static void startServer(BindingModule bindingModule) {
        Injector injector = Guice.createInjector(bindingModule);
        loadFetures(injector);
        loadCourses(injector);
        initRouts(injector);
    }

    private static void loadFetures(Injector injector) {
        FeatureContext.setChecker(injector.getInstance(FeatureChecker.class));
    }

    private static void loadCourses(Injector injector) {
        CourseLoader loader = injector.getInstance(CourseLoader.class);
        loader.load();
    }

    private static void initRouts(Injector injector) {
        HttpRouts routs = injector.getInstance(HttpRouts.class);
        routs.init();
    }
}
