package halleck.lms;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class CourseTest {

    @Test
    public void ifMaxCapacityIsNullThenFreeSeatsIsAlways99() throws Exception {
        Course o = new Course(null);
        o.setMaxCapacity(null);
        assertEquals(Course.UNLIMITED_ENROLLMENT, o.getFreeSeats());
    }

    @Test
    public void canAddChildCourseOnlyOnce() throws Exception {
        Course parent = new Course("Jessica");

        parent.addChild("Paul");
        parent.addChild("Paul");

        List<String> childIds = parent.getChildIds();

        assertEquals(1, childIds.size());
        assertEquals("Paul", childIds.get(0));
    }

    @Test
    public void canRemoveChild() throws Exception {
        Course parent = new Course("Jessica");

        parent.addChild("Paul");

        parent.removeChild("Paul");

        assertEquals(0, parent.getChildIds().size());

        parent.removeChild("Paul");

        assertEquals(0, parent.getChildIds().size());
    }
}
