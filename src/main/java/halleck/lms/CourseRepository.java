package halleck.lms;

import halleck.api.Course;

import java.util.List;

public interface CourseRepository {
    List<Course> getAllCourses();
    void putCourse(Course course);
    Course getCourse(String courseId);
}