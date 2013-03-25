package ioc;

import com.google.inject.AbstractModule;
import halleck.Halleck;
import lms.CourseRepository;
import lms.Gurney;
import lms.InMemoryCourseRepository;

public class BindingModule  extends AbstractModule {
    @Override
    protected void configure() {
         bind(Halleck.class).to(Gurney.class);
         bind(CourseRepository.class).to(InMemoryCourseRepository.class);
    }
}
