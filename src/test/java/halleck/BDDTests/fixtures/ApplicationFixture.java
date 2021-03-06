package halleck.BDDTests.fixtures;

import com.google.inject.Guice;
import com.google.inject.Injector;
import halleck.BDDTests.StaticContext;
import halleck.BDDTests.TestBindings;
import halleck.lms.CurrentUser;
import halleck.mocks.MockContext;
import halleck.mocks.MockSettings;
import halleck.lms.Course;
import halleck.lms.Halleck;
import halleck.lms.InMemoryCourseRepository;

import java.util.stream.Stream;

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
        Course c = new Course(id, name){{
            setDecription(desc);
            setUrl(url);
            setMaxCapacity(max);
        }};
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
        context.setCurrentUser(new CurrentUser(name));
    }

    public static String getContext() {
        return context.currentUser().getUserName();
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
        return Stream.of(couseIds)
                     .allMatch(
                             input -> system.getRegistration(input, userName).isRegistered()
                     );
    }

    public static ChildFixture assertCourseChildListFor(String id){
        return new ChildFixture(system.getCourseDojo(id));
    }
}
