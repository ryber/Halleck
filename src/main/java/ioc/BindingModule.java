package ioc;

import com.google.inject.AbstractModule;
import com.mongodb.Mongo;
import halleck.Settings;
import lms.InMemoryCourseRepository;
import webserver.AppSettings;
import halleck.Halleck;
import lms.CourseRepository;
import lms.Gurney;
import lms.MongoCourseRepository;

public class BindingModule extends AbstractModule {


    @Override
    protected void configure() {
        bind(Halleck.class).to(Gurney.class);

        //bind(Mongo.class).toProvider(MongoClientProvider.class);
        bindRepo();
    }

    protected void bindRepo() {
        bind(Settings.class).toProvider(SettingsProvider.class);
        //bind(CourseRepository.class).to(MongoCourseRepository.class);
        bind(CourseRepository.class).to(InMemoryCourseRepository.class);
    }
}
