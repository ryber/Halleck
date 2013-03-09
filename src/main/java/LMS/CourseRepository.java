package lms;

import halleck.Course;
import lms.learningobjects.Olt;

import java.util.List;

import static lms.learningobjects.LoUtils.newCourseList;

public class CourseRepository {
    List<Course> getAllCourses() {
        return newCourseList(new Olt("foo","Foo",""), new Olt("bar", "Bar", ""));
    }
}
