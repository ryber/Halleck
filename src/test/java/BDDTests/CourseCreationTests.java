package BDDTests;

import BDDTests.fixtures.ApplicationFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static BDDTests.fixtures.ApplicationFixture.givenAdminIsLoggedIn;

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



    }
}
