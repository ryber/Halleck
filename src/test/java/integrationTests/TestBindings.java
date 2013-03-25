package integrationTests;

import ioc.BindingModule;
import lms.CourseRepository;
import lms.InMemoryCourseRepository;

public class TestBindings extends BindingModule {
    @Override
    protected void bindRepo() {
        bind(CourseRepository.class).to(InMemoryCourseRepository.class);
    }
}
