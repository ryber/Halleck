package BDDTests;

import BDDTests.fixtures.ApplicationFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static BDDTests.fixtures.ApplicationFixture.givenAdminIsLoggedIn;
import static BDDTests.fixtures.ApplicationFixture.givenCourse;

public class CourseCreationTests {
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
