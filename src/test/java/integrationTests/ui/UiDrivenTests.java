package integrationTests.ui;

import halleck.Course;
import integrationTests.SetupFixtures;
import integrationTests.mocks.Result;
import ioc.CLI;
import org.junit.Before;
import org.junit.Test;
import webserver.mappers.FormVars;

import static integrationTests.SetupFixtures.givenCourse;
import static integrationTests.mocks.FakeAppServer.exec;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.containsString;
import static spark.route.HttpMethod.get;
import static spark.route.HttpMethod.post;

public class UiDrivenTests {

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

       Result result = exec(get, "/");

        assertThat(result.getContent(), containsString("Underwater Basketweaving"));
    }

    @Test
    public void canViewDetailsOfCourse() throws Exception {
        givenCourse("1", "Underwater Basketweaving");

        Result result = exec(get, "/registrations/course/1");

        assertThat(result.getContent(), containsString("Underwater Basketweaving"));
        assertThat(result.getContent(), containsString("Register For this course now!"));
    }

    @Test
    public void willNotGetRegistrationButtonIfFull() throws Exception {
        Course c = givenCourse("1", "Underwater Basketweaving");
        c.setMaxCapacity(0);

        Result result = exec(get, "/registrations/course/1");

        assertThat(result.getContent(), containsString("Underwater Basketweaving"));
        assertThat(result.getContent(), containsString(" The class is full. Please contact your team lead to have registrations added."));
    }

    @Test
    public void ifUserIsRegisteredThenShowTheVideo() throws Exception {
        givenCourse("1", "Underwater Basketweaving");

        SetupFixtures.setCurrentUser("Phil");

        Result regResult = exec(post, "/registrations/course/1");

        Result result = exec(get, regResult.getRedirect());

        assertThat(result.getContent(), containsString("Underwater Basketweaving"));
        assertThat(result.getContent(), containsString("<source src=\"http://localhost/"));
    }

    @Test
    public void canVewCourseForm() throws Exception {
         Result result = exec(get, "/course/");

        assertHasFormInput(result.getContent(), "id", "");
        assertHasFormInput(result.getContent(), "name", "");
        assertHasFormInput(result.getContent(), "description", "");
        assertHasFormInput(result.getContent(), "url", "");
    }

    @Test
    public void canVewCourseFormForExistingCourse() throws Exception {
        givenCourse("42", "Find The Fish");

        Result result = exec(get, "/course/42");

        assertHasFormInput(result.getContent(), "id", "42");
        assertHasFormInput(result.getContent(), "name", "Find The Fish");
    }

    @Test
    public void ifAskForMissingCourseThrow404(){
        Result regResult = exec(get, "/registrations/course/42");
        assertEquals(404, regResult.getStatus());
    }

    @Test
    public void canCreateNewCourse() throws Exception {
        FormVars form = new FormVars();
        form.put("id", 42);
        form.put("name", "Underwater Basketweaving");
        form.put("description", "fishy went wherever I did go");
        form.put("url", "http://foo/foo.mp4");

        Result result = exec(post, "/course/", form);

        assertEquals("/course/42", result.getRedirect());
    }



    private void assertHasFormInput(String content, String inputName, String inputValue) {
        try{
            assertThat(content, containsString(String.format("name=\"%s\"", inputName)));
            assertThat(content, containsString(String.format("value=\"%s\"", inputValue)));
        }catch (RuntimeException e){
            fail(String.format("Was expecting form input named '%s' with value '%s'", inputName, inputValue));
        }
    }
}
