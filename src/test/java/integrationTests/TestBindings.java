package integrationTests;

import halleck.Settings;
import integrationTests.mocks.MockSettings;
import webserver.AppSettings;
import ioc.BindingModule;
import lms.CourseRepository;
import lms.InMemoryCourseRepository;

public class TestBindings extends BindingModule {
    @Override
    protected void bindRepo() {
        bind(Settings.class).to(MockSettings.class);
    }
}
