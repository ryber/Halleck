package BDDTests.ui;

import BDDTests.fixtures.ApplicationFixture;
import BDDTests.mocks.Result;
import halleck.webserver.mappers.FormVars;
import org.junit.Test;

import static BDDTests.fixtures.ApplicationFixture.givenCourse;
import static BDDTests.fixtures.ApplicationFixture.setCurrentUser;
import static BDDTests.mocks.FakeAppServer.exec;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.matchers.JUnitMatchers.containsString;
import static spark.route.HttpMethod.get;
import static spark.route.HttpMethod.post;

public class RegistrationUITests extends UITEstRunner {

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
        givenCourse("1", "Underwater Basketweaving").withMaxCapacity(0);

        Result result = exec(get, "/registrations/course/1");

        assertThat(result.getContent(), containsString("Underwater Basketweaving"));
        assertThat(result.getContent(), containsString("The class is full. Please contact your team lead to have registrations added."));
    }

    @Test
    public void ifUserIsRegisteredThenShowTheVideo() throws Exception {
        givenCourse("1", "Underwater Basketweaving");

        setCurrentUser("Phil");

        Result regResult = exec(post, "/registrations/course/1");

        Result result = exec(get, regResult.getRedirect());

        assertThat(result.getContent(), containsString("Underwater Basketweaving"));
        assertThat(result.getContent(), containsString("Launch Course"));
    }

    @Test
    public void canVewCourseForm() throws Exception {
        ApplicationFixture.setAdmin("Phil");

        Result result = exec(get, "/admin/course");

        assertHasFormInput(result.getContent(), "id", "");
        assertHasFormInput(result.getContent(), "name", "");
        assertHasFormInput(result.getContent(), "description", "");
        assertHasFormInput(result.getContent(), "url", "");
    }

    @Test
    public void canVewCourseFormForExistingCourse() throws Exception {
        ApplicationFixture.setAdmin("Phil");

        givenCourse("42", "Find The Fish", "a nice course", "http://foo", 42);

        Result result = exec(get, "/admin/course/42");

        assertHasFormInput(result.getContent(), "id", "42");
        assertHasFormInput(result.getContent(), "name", "Find The Fish");
        assertHasTextArea(result.getContent(), "description", "a nice course");
        assertHasFormInput(result.getContent(), "url", "http://foo");
        assertHasFormInput(result.getContent(), "max", "42");
    }

    @Test
    public void ifAskForMissingCourseThrow404(){
        Result regResult = exec(get, "/registrations/course/42");
        assertEquals(404, regResult.getStatus());
    }


    @Test
    public void canCreateNewCourse() throws Exception {
        ApplicationFixture.setAdmin("Phil");

        FormVars form = new FormVars();
        form.put("id", 42);
        form.put("name", "Underwater Basketweaving");
        form.put("description", "fishy went wherever I did go");
        form.put("url", "http://foo/foo.mp4");

        Result result = exec(post, "/admin/course", form);

        assertEquals("/admin/course/42", result.getRedirect());
    }

    @Test
    public void canGetAListOfMyCourses() throws Exception {
        givenCourse("1", "Underwater Basketweaving");
        givenCourse("2", "How to make tacos");
        givenCourse("3", "Groundhog Farming");

        setCurrentUser("Phil");

        exec(post, "/registrations/course/1");
        exec(post, "/registrations/course/3");

        Result r = exec(get, "/my-courses");

        assertThat(r.getContent(), containsString("Underwater Basketweaving"));
        assertThat(r.getContent(), containsString("Groundhog Farming"));
        assertThat(r.getContent(), not(containsString("tacos")));
    }

    @Test
    public void adminsCanViewRegistrations() throws Exception {
        ApplicationFixture.setAdmin("Phil");
        setCurrentUser("Phil");

        givenCourse("1", "Underwater Basketweaving");

        exec(post, "/registrations/course/1");

        Result result = exec(get, "/admin/course/1/registrations");

        assertThat(result.getContent(), containsString("Underwater Basketweaving"));
        assertThat(result.getContent(), containsString("Phil"));
    }


    @Test
    public void templatesThatDoNotHaveJavascriptsWillNotRenderAnyJs(){
        ApplicationFixture.setAdmin("Phil");
        setCurrentUser("Phil");

        givenCourse("1", "Underwater Basketweaving");

        Result result = exec(get, "/admin/course/1");

        assertThat(result.getContent(), not(containsString("<script")));
    }

    private void assertHasFormInput(String content, String inputName, String inputValue) {
        try{
            assertThat(content, containsString(String.format("name=\"%s\"", inputName)));
            assertThat(content, containsString(String.format("value=\"%s\"", inputValue)));
        }catch (RuntimeException e){
            fail(String.format("Was expecting form input named '%s' with value '%s'", inputName, inputValue));
        }
    }



    private void assertHasTextArea(String content,  String inputName, String inputValue) {
        try{
            assertThat(content, containsString(String.format("name=\"%s\"", inputName)));
            assertThat(content, containsString(inputValue));
        }catch (RuntimeException e){
            fail(String.format("Was expecting form textarea named '%s' with value '%s'", inputName, inputValue));
        }
    }
}
