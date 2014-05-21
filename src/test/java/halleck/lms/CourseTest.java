package halleck.lms;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CourseTest {

    @Test
    public void ifMaxCapacityIsNullThenFreeSeatsIsAlways99() throws Exception {
        Course o = new Course(null);
        o.setMaxCapacity(null);
        assertEquals(Course.UNLIMITED_ENROLLMENT, o.getFreeSeats());
    }
}
