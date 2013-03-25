package halleck;


import java.util.Set;

public interface Halleck {
    Iterable<Course> getAllCourses();
    Course getCourse(String id);
    Registration getRegistration(String courseID, String userID);
    void register(String courseId, String userID);
    Set<Registration> getRegistrations(String courseID);
    void createCourse(Course course);
}
