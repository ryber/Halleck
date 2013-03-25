package lms;

import halleck.Course;

public interface CourseRepository {
    void putCourse(Course course);
    Course getCourse(String courseId);
}
