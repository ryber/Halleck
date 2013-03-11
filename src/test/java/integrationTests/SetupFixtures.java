package integrationTests;

import halleck.Course;
import lms.CourseRepository;
import lms.learningobjects.Olt;

public class SetupFixtures {
    public static Course givenCourse(String id, String name) {
        Course c = new Olt(id, name, null);
        CourseRepository.addCourse(c);
        return c;
    }



    public static void givenUser(String barry) {

    }

    public static void reset() {
        CourseRepository.reset();
    }
}
