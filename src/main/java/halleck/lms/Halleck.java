package halleck.lms;


import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface Halleck {
    Stream<Course> getAllCourses();
    Stream<Course> getUsersCourses(String userID);
    Optional<Course> getCourse(String id);
    Registration getRegistration(String courseID, String userID);
    void register(String courseId, String userID);
    Set<Registration> getRegistrations(String courseID);
    void createCourse(Course course);
    void createCourses(Iterable<Course> courseArray);
}
