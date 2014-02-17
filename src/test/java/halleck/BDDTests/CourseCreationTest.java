package halleck.BddTests;

import halleck.BddTests.fixtures.ApplicationFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static halleck.BddTests.fixtures.ApplicationFixture.givenAdminIsLoggedIn;
import static halleck.BddTests.fixtures.ApplicationFixture.givenCourse;

public class CourseCreationTest {
    @Before
    public void setUp() throws Exception {
        ApplicationFixture.init();
    }

    @After
    public void tearDown() throws Exception {
        ApplicationFixture.reset();
    }

    @Test
    public void coursesAreOwnedByTheirCreator() throws Exception {
        givenAdminIsLoggedIn("Fred");

        givenCourse("2", "Java For Dummies").assertHasOwner("Fred");
    }


}
