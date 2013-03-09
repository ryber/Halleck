package LMS;

import LMS.learningobjects.Course;

import static com.google.common.collect.Lists.newArrayList;

public class Gurney implements Halleck {
    @Override
    public Iterable<Course> getAllCourses() {
        return newArrayList(new Course("Foo"), new Course("Bar"));
    }
}
