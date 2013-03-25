package integrationTests;

import halleck.Course;
import lms.CourseRepository;
import halleck.OnlineCourse;

public class SetupFixtures {
    private static String currentUser;

    public static Course givenCourse(String id, String name) {
        Course c = new OnlineCourse(id, name, null);
        CourseRepository.addCourse(c);
        return c;
    }


    public static void givenUser(String barry) {

    }

    public static void reset() {
        CourseRepository.reset();
    }

    public static void setCurrentUser(String name) {
        currentUser = name;
    }

    public static String getCurrentUser() {
        return currentUser;
    }
}
