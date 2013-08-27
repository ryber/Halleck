package BDDTests.fixtures;

import BDDTests.TestBindings;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Guice;
import com.google.inject.Injector;
import halleck.api.Course;
import halleck.api.Halleck;
import halleck.api.OnlineCourse;
import BDDTests.mocks.MockSettings;
import halleck.lms.InMemoryCourseRepository;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ApplicationFixture {
    private static String currentUser;

    private static Injector injector = Guice.createInjector(new TestBindings());
    private static Halleck system = injector.getInstance(Halleck.class);

    public static void init() {
        system = injector.getInstance(Halleck.class);
    }

    public static Course givenCourse(String id, String name) {
        return givenCourse(id, name, null, null, null);
    }

    public static Course givenCourse(String id, String name, String desc, String url, Integer max) {
        Course c = new OnlineCourse(id, name, desc, url, max, null);
        InMemoryCourseRepository.addCourse(c);
        return c;
    }

    public static void registerUserForCourse(String courseName, String userName){
        system.register(courseName, userName);
    }

    public static boolean isRegisteredForCourse(String courseName, String userName) {
        return system.getRegistration(courseName, userName).isRegistered();
    }

    public static void reset() {
        InMemoryCourseRepository.reset();
        MockSettings.admin = null;
    }

    public static void setCurrentUser(String name) {
        currentUser = name;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setAdmin(String name) {
        setCurrentUser(name);
        MockSettings.admin = name;
    }

    public static int getRegistrationCount(String courseName) {
        return system.getRegistrations(courseName).size();
    }

    public static boolean canUserRegisterForCourse(String courseName, String userMoe) {
        return system.getRegistration(courseName, userMoe).canRegister();
    }

    public static int getCountOfOpenSeatsForCourse(String abc) {
        return system.getCourse(abc).getFreeSeats();
    }

    public static void userIsRegisteredForCourse(String userName, String... couseIds) {
        Iterable<Course> moesCourse = system.getUsersCourses(userName);

        assertEquals(couseIds.length, Iterables.size(moesCourse));
        for(String id : couseIds){
            assertTrue(Iterables.any(moesCourse, new HasId(id)));
        }
    }

    private static class HasId implements Predicate<Course> {
        private String id;

        private HasId(String id) {
            this.id = id;
        }

        @Override
        public boolean apply(Course course) {
            return course.getId().equals(id);
        }
    }
}
