package halleck.BddTests.fixtures;

import halleck.api.Course;

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
}
