package integrationTests;

import halleck.api.Course;
import halleck.api.OnlineCourse;
import integrationTests.mocks.MockSettings;
import halleck.lms.InMemoryCourseRepository;

public class SetupFixtures {
    private static String currentUser;

    public static Course givenCourse(String id, String name) {
        return givenCourse(id, name, null, null, null);
    }

    public static Course givenCourse(String id, String name, String desc, String url, Integer max) {
        Course c = new OnlineCourse(id, name, desc, url, max, null);
        InMemoryCourseRepository.addCourse(c);
        return c;
    }

    public static void givenUser(String barry) {

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
}
