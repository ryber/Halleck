package halleck.BDDTests.fixtures;

import halleck.lms.Course;

import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;

public class CourseFixture {
    private final Course course;

    public CourseFixture(Course course) {
        this.course = course;
    }

    public CourseFixture withMaxCapacity(Integer capacity) {
        course.setMaxCapacity(capacity);
        return this;
    }

    public void assertHasOwner(String ownerName) {
        assertEquals(ownerName, course.getOwner());
    }

    public CourseFixture withChildren(String... childIds) {
        Stream.of(childIds).forEach(course::addChild);
        return this;
    }
}
