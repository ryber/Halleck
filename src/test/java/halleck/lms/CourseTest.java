package halleck.lms;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CourseTest {

    @Test
    public void ifMaxCapacityIsNullThenFreeSeatsIsAlways99() throws Exception {
        Course o = new Course(null);
        o.setMaxCapacity(null);
        assertEquals(Course.UNLIMITED_ENROLLMENT, o.getFreeSeats());
    }
}
