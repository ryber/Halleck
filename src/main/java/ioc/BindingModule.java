package ioc;

import com.google.inject.AbstractModule;
import com.mongodb.Mongo;
import halleck.Halleck;
import halleck.Settings;
import lms.CourseRepository;
import lms.Gurney;
import lms.MongoCourseRepository;

public class BindingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Halleck.class).to(Gurney.class);
        bind(Settings.class).toProvider(SettingsProvider.class);
        bind(Mongo.class).toProvider(MongoClientProvider.class);
        bindRepo();
    }

    protected void bindRepo() {
        bind(CourseRepository.class).to(MongoCourseRepository.class);
    }
}
