package integrationTests;

import com.google.inject.Guice;
import com.google.inject.Injector;
import halleck.api.Halleck;
import org.junit.Before;
import org.junit.Test;

import static integrationTests.SetupFixtures.givenCourse;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class RegistrationTests {

    private Injector injector = Guice.createInjector(new TestBindings());
    private Halleck system = injector.getInstance(Halleck.class);

    @Before
    public void setUp() throws Exception {
        SetupFixtures.reset();
    }

    @Test
    public void canRegisterForCourse() throws Exception {
        givenCourse("abc", "Underwater Basketweaving");

        assertFalse(system.getRegistration("abc", "barry").isRegistered());

        system.register("abc", "barry");

        assertTrue(system.getRegistration("abc", "barry").isRegistered());

        system.register("abc", "barry");

        assertTrue(system.getRegistration("abc", "barry").isRegistered());
    }

    @Test
    public void canCountRegistrations() throws Exception {
        givenCourse("abc", "Underwater Basketweaving");

        system.register("abc", "barry");
        system.register("abc", "gary");

        assertEquals(2, system.getRegistrations("abc").size());

        system.register("abc", "larry");
        system.register("abc", "larry");

        assertEquals(3, system.getRegistrations("abc").size());
    }

    @Test
     public void canTrackCapacity() throws Exception {
        givenCourse("abc", "Underwater Basketweaving").setMaxCapacity(2);

        assertEquals(2, system.getCourse("abc").getFreeSeats());

        system.register("abc", "larry");

        assertEquals(1, system.getCourse("abc").getFreeSeats());

        system.register("abc", "larry");

        assertEquals(1, system.getCourse("abc").getFreeSeats());

        system.register("abc", "barry");

        assertEquals(0, system.getCourse("abc").getFreeSeats());
    }

    @Test
    public void canDetectIfRegistrationIsPossible() throws Exception {
        givenCourse("abc", "Underwater Basketweaving").setMaxCapacity(2);

        assertTrue(system.getRegistration("abc", "Moe").canRegister());

        system.register("abc", "Moe");

        assertFalse(system.getRegistration("abc", "Moe").canRegister());

        system.register("abc", "larry");

        assertFalse(system.getRegistration("abc", "shemp").canRegister());
    }

    @Test
    public void canRegisterAsManyPeopleAsWeWantIfMaxCapacityIsOff() throws Exception {
        givenCourse("abc", "Underwater Basketweaving").setMaxCapacity(null);

        system.register("abc", "moe");
        system.register("abc", "larry");
        system.register("abc", "curly");
        system.register("abc", "shemp");

        assertEquals(4, system.getRegistrations("abc").size());
    }
}
