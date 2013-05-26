package halleck.api;


import java.util.Set;

public interface Halleck {
    Iterable<Course> getAllCourses();
    Iterable<Course> getUsersCourses(String userID);
    Course getCourse(String id);
    Registration getRegistration(String courseID, String userID);
    void register(String courseId, String userID);
    Set<Registration> getRegistrations(String courseID);
    void createCourse(Course course);
    Iterable<Course> search(String q);
    void createCourses(Iterable<Course> courseArray);
    void addChild(String parentId, String childId);
}
