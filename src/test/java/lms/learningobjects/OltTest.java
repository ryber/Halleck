package lms.learningobjects;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

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
}
