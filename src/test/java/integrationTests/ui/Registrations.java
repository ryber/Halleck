package integrationTests.ui;

import halleck.Course;
import integrationTests.SetupFixtures;
import integrationTests.mocks.FakeAppServer;
import ioc.CLI;
import org.junit.Before;
import org.junit.Test;
import spark.route.HttpMethod;

import static integrationTests.SetupFixtures.givenCourse;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class Registrations {

    private boolean serverStarted = false;

    @Before
    public void setUp() throws Exception {
        SetupFixtures.reset();
        if(!serverStarted){
            CLI.startServer();
            serverStarted = true;
        }
    }

    @Test
    public void canViewIndexOfAllCourses() throws Exception {
       givenCourse("1", "Underwater Basketweaving");

       FakeAppServer.Result result = FakeAppServer.exec(HttpMethod.get, "/");

        assertThat(result.getContent(), containsString("Underwater Basketweaving"));
    }

    @Test
    public void canViewDetailsOfCourse() throws Exception {
        givenCourse("1", "Underwater Basketweaving");

        FakeAppServer.Result result = FakeAppServer.exec(HttpMethod.get, "/registrations/course/1");

        assertThat(result.getContent(), containsString("Underwater Basketweaving"));
        assertThat(result.getContent(), containsString("Register For this course now!"));
    }

    @Test
    public void willNotGetRegistrationButtonIfFull() throws Exception {
        Course c = givenCourse("1", "Underwater Basketweaving");
        c.setMaxCapacity(0);

        FakeAppServer.Result result = FakeAppServer.exec(HttpMethod.get, "/registrations/course/1");

        assertThat(result.getContent(), containsString("Underwater Basketweaving"));
        assertThat(result.getContent(), containsString(" The class is full. Please contact your team lead to have registrations added."));
    }

    @Test
    public void ifUserIsRegisteredThenShowTheVideo() throws Exception {
        Course c = givenCourse("1", "Underwater Basketweaving");
        c.addRegisteredUser("Phil");

        SetupFixtures.setCurrentUser("Phil");

        FakeAppServer.Result result = FakeAppServer.exec(HttpMethod.get, "/registrations/course/1");

        assertThat(result.getContent(), containsString("Underwater Basketweaving"));
        assertThat(result.getContent(), containsString("<source src=\"http://localhost/"));
    }
}
