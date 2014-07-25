package halleck.appstart;

import com.google.inject.AbstractModule;
import com.mongodb.Mongo;
import halleck.lms.*;
import halleck.lms.contexts.PerThreadContext;
import halleck.webserver.Authenticator;
import halleck.webserver.CourseRenderer;
import halleck.webserver.featurechecks.FeatureLoader;
import halleck.webserver.renderers.RenderingDispatcher;

public class BindingModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(Halleck.class).to(Gurney.class);
        bind(CourseRepository.class).toProvider(RepositoryProvider.class);
        bind(Mongo.class).toProvider(MongoClientProvider.class);
        bind(Authenticator.class).toProvider(AuthenticatorProvider.class);
        bind(CourseRenderer.class).to(RenderingDispatcher.class);
        bind(FeatureChecker.class).toProvider(FeatureLoader.class);

        bindRepo();
    }


    protected void bindRepo() {
        bind(Settings.class).toProvider(SettingsProvider.class);
        bind(AppContext.class).to(PerThreadContext.class);
    }
}
