package halleck.BDDTests;

import halleck.BDDTests.fixtures.ApplicationFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static halleck.BDDTests.fixtures.ApplicationFixture.assertCourseChildListFor;
import static halleck.BDDTests.fixtures.ApplicationFixture.givenAdminIsLoggedIn;
import static halleck.BDDTests.fixtures.ApplicationFixture.givenCourse;

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
        givenAdminIsLoggedIn("Duncan");

        givenCourse("2", "Spice Harvesting For Dummies").assertHasOwner("Duncan");
    }

    @Test
    public void canAddChildCourses(){
        givenAdminIsLoggedIn("Duncan");

        givenCourse("3", "Stillsuits 101");
        givenCourse("4", "Water of life: delicious but deadly");
        givenCourse("2", "Spice Harvesting For Dummies").withChildren("3", "4");

        assertCourseChildListFor("2").hasChild("4", "Water of life: delicious but deadly")
                                     .hasChild("3", "Stillsuits 101");
    }

}
