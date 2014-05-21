package halleck.lms;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OnlineCourseTest {

    @Test
    public void ifMaxCapacityIsNullThenFreeSeatsIsAlways99() throws Exception {
        OnlineCourse o = new OnlineCourse(null,null,null);
        o.setMaxCapacity(null);
        assertEquals(OnlineCourse.UNLIMITED_ENROLLMENT, o.getFreeSeats());
    }

    @Test
    public void willCopyFromInputToMakeACourse(){
        Course i = mock(Course.class);

        when(i.getId()).thenReturn("fooid");
        when(i.getName()).thenReturn("Underwater Basketweaving");
        when(i.getDescription()).thenReturn("boo!");
        when(i.getUrl()).thenReturn("http://bar");
        when(i.getMaxEnrollment()).thenReturn(54);
        when(i.getContent()).thenReturn("some content");

        OnlineCourse t = new OnlineCourse(i);

        assertEquals("fooid", t.getId());
        assertEquals("Underwater Basketweaving", t.getName());
        assertEquals("http://bar", t.getUrl());
        assertEquals("boo!", t.getDescription());
        assertEquals(54, t.getMaxEnrollment().intValue());
        assertEquals("some content", t.getContent());
    }
}
