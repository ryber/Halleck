package halleck.lms;

import halleck.api.Course;
import halleck.lms.UserRegistration;
import org.junit.Test;

import static com.google.common.collect.Sets.newHashSet;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRegistrationTest {

    @Test
    public void isRegisteredIfUserInCourse() throws Exception {
        Course course = mock(Course.class);
        when(course.getRegisteredUsers()).thenReturn(newHashSet("bob"));

        UserRegistration u = new UserRegistration(course, "bob");

        assertTrue(u.isRegistered());
    }

    @Test
    public void notRegisteredIfNot() throws Exception {
        Course course = mock(Course.class);
        when(course.getRegisteredUsers()).thenReturn(newHashSet("bob"));

        UserRegistration u = new UserRegistration(course, "Chauncey");

        assertFalse(u.isRegistered());
    }

    @Test
    public void canRegisterIfNotAlreadyRegisteredAndThereAreOpenSeats() throws Exception {
        Course course = mock(Course.class);

        when(course.getRegisteredUsers()).thenReturn(newHashSet("bob"));
        when(course.hasFreeSeats()).thenReturn(true);

        UserRegistration u = new UserRegistration(course, "Chauncey");

        assertTrue(u.canRegister());
    }

    @Test
    public void cantRegisterTwice() throws Exception {
        Course course = mock(Course.class);

        when(course.getRegisteredUsers()).thenReturn(newHashSet("bob"));
        when(course.getFreeSeats()).thenReturn(1);

        UserRegistration u = new UserRegistration(course, "bob");

        assertFalse(u.canRegister());
    }

    @Test
    public void cantRegisterForFullCourse() throws Exception {
        Course course = mock(Course.class);

        when(course.getRegisteredUsers()).thenReturn(newHashSet("bob"));
        when(course.getFreeSeats()).thenReturn(0);

        UserRegistration u = new UserRegistration(course, "Chauncy");

        assertFalse(u.canRegister());
    }
}
