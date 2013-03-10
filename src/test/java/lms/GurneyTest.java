package lms;

import halleck.Course;
import lms.learningobjects.Olt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GurneyTest {

    @Mock
    private CourseRepository courseRepo;

    @InjectMocks
    private Gurney gurney;

    @Test
    public void getAllReturnsAllFromRepo() throws Exception {
        List<Course> courses = newArrayList(mock(Course.class));
        when(courseRepo.getAllCourses()).thenReturn(courses);

        assertEquals(courses, gurney.getAllCourses());
    }

    @Test
    public void willReturnDesiredCourse() throws Exception {
        Course c1 = new Olt("foo",null,null);
        Course c2 = new Olt("bar",null,null);
        List<Course> courses = newArrayList(c1, c2);

        when(courseRepo.getAllCourses()).thenReturn(courses);

        assertEquals(c2, gurney.getCourse("bar"));
    }
}
