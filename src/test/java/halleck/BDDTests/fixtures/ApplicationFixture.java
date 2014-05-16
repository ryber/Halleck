package halleck.BDDTests.fixtures;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.inject.Guice;
import com.google.inject.Injector;
import halleck.BDDTests.StaticContext;
import halleck.BDDTests.TestBindings;
import halleck.BDDTests.mocks.MockSettings;
import halleck.api.Course;
import halleck.api.Halleck;
import halleck.api.OnlineCourse;
import halleck.lms.InMemoryCourseRepository;

import javax.annotation.Nullable;
import java.util.Arrays;

public class ApplicationFixture {
    private static StaticContext context = new StaticContext();

    private static Injector injector = Guice.createInjector(new TestBindings());
    private static Halleck system = injector.getInstance(Halleck.class);


    public static void init() {
        system = injector.getInstance(Halleck.class);
    }

    public static CourseFixture givenCourse(String id, String name) {
        return givenCourse(id, name, null, null, null);
    }

    public static CourseFixture givenCourse(String id, String name, String desc, String url, Integer max) {
        Course c = new OnlineCourse(id, name, desc, url, max, null);
        system.createCourse(c);
        return new CourseFixture(c);
    }

    public static void registerUserForCourse(String courseName, String userName){
        system.register(courseName, userName);
    }

    public static void reset() {
        InMemoryCourseRepository.reset();
        MockSettings.admin = null;
        StaticContext.reset();
    }

    public static void setContext(String name) {
        context.setCurrentUser(name);
    }

    public static String getContext() {
        return context.currentUser();
    }

    public static void givenAdminIsLoggedIn(String name) {
        setContext(name);
        MockSettings.admin = name;
    }

    public static int getRegistrationCount(String courseName) {
        return system.getRegistrations(courseName).size();
    }

    public static boolean canUserRegisterForCourse(String courseName, String userMoe) {
        return system.getRegistration(courseName, userMoe).canRegister();
    }

    public static int getCountOfOpenSeatsForCourse(String abc) {
        return system.getCourse(abc).get().getFreeSeats();
    }

    public static boolean userIsRegisteredForCourse(final String userName, String... couseIds) {
        return FluentIterable.from(Arrays.asList(couseIds))
                .allMatch(new Predicate<String>() {
                    @Override
                    public boolean apply(@Nullable String input) {
                        return system.getRegistration(input, userName).isRegistered();
                    }
                });
    }
}
