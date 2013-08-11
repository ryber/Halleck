package halleck.lms;

import halleck.api.Course;
import halleck.api.OnlineCourse;
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
    public void canSetDescripToNullAndNotDie() throws Exception {
        OnlineCourse o = new OnlineCourse(null,null,null);
        assertEquals("", o.getDescription());
        assertEquals("", o.getDescriptionShort());
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

    @Test
    public void ifCourseIsAHtml5VideoThenSayItIs() throws Exception {
        OnlineCourse isEmbed = new OnlineCourse("id","name","desc", "http://foo/video.mp4",3, null);

        assertTrue(isEmbed.isEmbedVideo());

        OnlineCourse notembed = new OnlineCourse("id","name","desc", "http://foo/video.html",3, null);

        assertFalse(notembed.isEmbedVideo());
    }

}
