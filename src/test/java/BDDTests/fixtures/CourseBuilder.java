package BDDTests.fixtures;

import halleck.api.Course;

public class CourseBuilder {
    private final Course course;

    public CourseBuilder(Course course) {
        this.course = course;
    }

    public CourseBuilder withMaxCapacity(Integer capacity) {
        course.setMaxCapacity(capacity);
        return this;
    }
}
