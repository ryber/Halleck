package ioc;

import com.google.inject.AbstractModule;
import halleck.Halleck;
import lms.CourseRepository;
import lms.Gurney;
import lms.MongoCourseRepository;

public class BindingModule  extends AbstractModule {
    @Override
    protected void configure() {
         bind(Halleck.class).to(Gurney.class);
        bindRepo();
    }

    protected void bindRepo() {
        bind(CourseRepository.class).to(MongoCourseRepository.class);
    }
}
