package BDDTests;

import BDDTests.fixtures.ApplicationFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static BDDTests.fixtures.ApplicationFixture.*;
import static junit.framework.Assert.*;

public class RegistrationTests {

    @Before
    public void setUp() throws Exception {
       ApplicationFixture.init();
    }

    @After
    public void tearDown() throws Exception {
        ApplicationFixture.reset();
    }

    @Test
    public void canRegisterForCourse() throws Exception {
        givenCourse("abc", "Underwater Basketweaving");

        assertFalse(userIsRegisteredForCourse("barry", "abc"));

        registerUserForCourse("abc", "barry");

        assertTrue(userIsRegisteredForCourse("barry", "abc"));

        registerUserForCourse("abc", "barry");

        assertTrue(userIsRegisteredForCourse("barry", "abc"));
    }



    @Test
    public void canCountRegistrations() throws Exception {
        givenCourse("abc", "Underwater Basketweaving");

        registerUserForCourse("abc", "barry");
        registerUserForCourse("abc", "gary");

        assertEquals(2, getRegistrationCount("abc"));

        registerUserForCourse("abc", "larry");
        registerUserForCourse("abc", "larry");

        assertEquals(3, getRegistrationCount("abc"));
    }

    @Test
     public void canTrackCapacity() throws Exception {
        givenCourse("abc", "Underwater Basketweaving").withMaxCapacity(2);

        assertEquals(2, getCountOfOpenSeatsForCourse("abc"));

        registerUserForCourse("abc", "larry");

        assertEquals(1, getCountOfOpenSeatsForCourse("abc"));

        registerUserForCourse("abc", "larry");

        assertEquals(1, getCountOfOpenSeatsForCourse("abc"));

        registerUserForCourse("abc", "barry");

        assertEquals(0, getCountOfOpenSeatsForCourse("abc"));
    }

    @Test
    public void canDetectIfRegistrationIsPossible() throws Exception {
        givenCourse("abc", "Underwater Basketweaving").withMaxCapacity(2);

        assertTrue(canUserRegisterForCourse("abc", "Moe"));

        registerUserForCourse("abc", "Moe");

        assertFalse(canUserRegisterForCourse("abc", "Moe"));

        registerUserForCourse("abc", "larry");

        assertFalse(canUserRegisterForCourse("abc", "shemp"));
    }

    @Test
    public void canRegisterAsManyPeopleAsWeWantIfMaxCapacityIsOff() throws Exception {
        givenCourse("abc", "Underwater Basketweaving").withMaxCapacity(null);

        registerUserForCourse("abc", "moe");
        registerUserForCourse("abc", "larry");
        registerUserForCourse("abc", "curly");
        registerUserForCourse("abc", "shemp");

        assertEquals(4, getRegistrationCount("abc"));
    }

    @Test
    public void canGetAListOfMyCourses() throws Exception {
        givenCourse("a","Underwater Basketweaving");
        givenCourse("b", "Salami Smuggling 101");
        givenCourse("c", "Tiny Tim Studies");

        registerUserForCourse("a", "moe");
        registerUserForCourse("c", "moe");

        String moe = "moe";
        String a = "a";
        String c = "c";

        assertTrue(userIsRegisteredForCourse(moe, a, c));
    }

}
