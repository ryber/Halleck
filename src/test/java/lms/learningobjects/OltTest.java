package lms.learningobjects;

import halleck.CourseInput;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OltTest {

    @Test
    public void ifMaxCapacityIsNullThenFreeSeatsIsAlways99() throws Exception {
        Olt o = new Olt(null,null,null);
        o.setMaxCapacity(null);
        assertEquals(Olt.UNLIMITED_ENROLLMENT, o.getFreeSeats());
    }

    @Test
    public void canSetDescripToNullAndNotDie() throws Exception {
        Olt o = new Olt(null,null,null);
        assertEquals("", o.getDescription());
        assertEquals("", o.getDescriptionShort());
    }

    @Test
    public void willCopyFromInputToMakeACourse(){
        CourseInput i = mock(CourseInput.class);

        when(i.getId()).thenReturn("fooid");
        when(i.getName()).thenReturn("Underwater Basketweaving");
        when(i.getDescription()).thenReturn("boo!");
        when(i.getUrl()).thenReturn("http://bar");
        when(i.getMaxEnrollment()).thenReturn(54);

        Olt t = new Olt(i);

        assertEquals("fooid", t.getId());
        assertEquals("Underwater Basketweaving", t.getName());
        assertEquals("http://bar", t.getUrl());
        assertEquals("boo!", t.getDescription());
        assertEquals(54, t.getMaxEnrollment().intValue());

    }
}
