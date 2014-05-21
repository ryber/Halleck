package halleck.lms;

import java.util.stream.Stream;

public interface CourseRepository {
    Stream<Course> getAllCourses();
    void putCourse(Course course);
    Course getCourse(String courseId);
}
