package halleck.lms;

import halleck.mocks.MockContext;
import halleck.mocks.MockCourseRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;


public class GurneyTest {

    private MockCourseRepo courseRepo;

    private Gurney gurney;

    @Before
    public void setUp() throws Exception {
        courseRepo = new MockCourseRepo();
        gurney = new Gurney(courseRepo, new MockContext());
    }

    @After
    public void tearDown() throws Exception {
        InMemoryCourseRepository.reset();
    }

    @Test
    public void getAllReturnsAllFromRepo() throws Exception {
        Course mock = new Course("f");
        courseRepo.putCourse(mock);

        assertEquals(mock, gurney.getAllCourses().findFirst().get());
    }

    @Test
    public void willReturnDesiredCourse() throws Exception {
        Course foo = new Course("foo");
        Course bar = new Course("bar");
        courseRepo.putCourse(foo);
        courseRepo.putCourse(bar);

        assertEquals(bar, gurney.getCourse("bar").get());
    }

    @Test
    public void willSaveInputToCourseRepo() throws Exception {

        gurney.createCourse(new Course("42"));

        assertEquals("42", courseRepo.getAllCourses().findAny().get().getId());
    }

    @Test
    public void willSortCoursesAlphabetically() throws Exception {
        Course a = new Course("foo","A");
        Course b = new Course("bar","B");
        Course c = new Course("bar","C");

        courseRepo.putAll(a, b, c);

        assertEquals(a, gurney.getAllCourses().collect(Collectors.toList()).get(0));
    }

    @Test
    public void canGetChildCourseList() throws Exception {
        Course a = new Course("A");
        Course b = new Course("B");
        Course c = new Course("C");
        a.addChild("B");
        a.addChild("C");

        courseRepo.putAll(a, b, c);

        List<Course> children = gurney.getCourseDojo("A").collect(Collectors.toList());

        assertTrue(children.contains(b));
        assertTrue(children.contains(c));
    }

    @Test(expected = Gurney.CourseNotFoundException.class)
    public void willThrowExceptionIfParentDoesntExist() throws Exception {
        gurney.getCourseDojo("foo");
    }

    @Test
    public void ifChildrenDontExistJustKeepOnRolling() throws Exception {
        Course a = new Course("A");
        Course b = new Course("B");
        a.addChild("B");
        a.addChild("C");

        courseRepo.putAll(a, b);

        List<Course> children = gurney.getCourseDojo("A").collect(Collectors.toList());

        assertTrue(children.contains(b));
        assertEquals(1, children.size());
    }
}
