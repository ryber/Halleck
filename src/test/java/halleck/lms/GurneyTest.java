package halleck.lms;

import halleck.api.Course;
import halleck.api.OnlineCourse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GurneyTest {

    @Mock
    private InMemoryCourseRepository courseRepo;

    @Mock
    private AppContext context;

    @InjectMocks
    private Gurney gurney;

    @Test
    public void getAllReturnsAllFromRepo() throws Exception {
        Course mock = mock(Course.class);
        when(courseRepo.getAllCourses()).thenReturn(Stream.of(mock));

        assertEquals(mock, gurney.getAllCourses().findFirst().get());
    }

    @Test
    public void willReturnDesiredCourse() throws Exception {
        Course c1 = new OnlineCourse("foo",null,null);
        Course c2 = new OnlineCourse("bar",null,null);

        when(courseRepo.getAllCourses()).thenReturn(Stream.of(c1, c2));

        assertEquals(c2, gurney.getCourse("bar").get());
    }

    @Test
    public void willSaveInputToCourseRepo() throws Exception {

        ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
        doNothing().when(courseRepo).putCourse(captor.capture());

        gurney.createCourse(new OnlineCourse("42","Underwater Basketweaving", "a desc", "http://foo", null, "something different"));

        Course result = captor.getValue();

        assertEquals("42", result.getId());
        assertEquals("Underwater Basketweaving", result.getName());
        assertEquals("a desc", result.getDescription());
        assertEquals("http://foo", result.getUrl());
        assertEquals("something different", result.getContent());
    }

}
