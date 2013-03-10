package halleck;


public interface Halleck {
    Iterable<Course> getAllCourses();
    Course getCourse(String id);
    Registration getRegistration(String courseID, String userID);
    void register(String params, String user);
}
