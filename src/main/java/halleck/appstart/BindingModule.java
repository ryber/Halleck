package halleck.appstart;

import com.google.inject.AbstractModule;
import com.mongodb.Mongo;
import halleck.api.Halleck;
import halleck.api.Settings;
import halleck.lms.CourseRepository;
import halleck.lms.Gurney;
import halleck.webserver.Authenticator;

public class BindingModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(Halleck.class).to(Gurney.class);
        bind(CourseRepository.class).toProvider(RepositoryProvider.class);
        bind(Mongo.class).toProvider(MongoClientProvider.class);
        bind(Authenticator.class).toProvider(AuthenticatorProvider.class);
        bindRepo();
    }

    protected void bindRepo() {
        bind(Settings.class).toProvider(SettingsProvider.class);
    }
}
