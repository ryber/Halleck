package halleck.appstart;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.mongodb.Mongo;
import halleck.api.Halleck;
import halleck.api.Settings;
import halleck.lms.AppContext;
import halleck.lms.CourseRepository;
import halleck.lms.Gurney;
import halleck.lms.contexts.PerThreadContext;
import halleck.webserver.Authenticator;
import halleck.webserver.routs.AdminRouts;
import halleck.webserver.routs.AuthenticationRouts;
import halleck.webserver.routs.LearningRouts;
import halleck.webserver.routs.SparkRoutCollector;

public class BindingModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(Halleck.class).to(Gurney.class);
        bind(CourseRepository.class).toProvider(RepositoryProvider.class);
        bind(Mongo.class).toProvider(MongoClientProvider.class);
        bind(Authenticator.class).toProvider(AuthenticatorProvider.class);
        bind(AppContext.class).to(PerThreadContext.class);
        bindRouts();
        bindRepo();
    }

    private void bindRouts() {
        Multibinder<SparkRoutCollector> uriBinder = Multibinder.newSetBinder(binder(), SparkRoutCollector.class);
        uriBinder.permitDuplicates();
        uriBinder.addBinding().to(AdminRouts.class);
        uriBinder.addBinding().to(LearningRouts.class);
        uriBinder.addBinding().to(AuthenticationRouts.class);
    }

    protected void bindRepo() {
        bind(Settings.class).toProvider(SettingsProvider.class);
    }
}
