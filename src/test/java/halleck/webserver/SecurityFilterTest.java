package halleck.webserver;

import halleck.BDDTests.mocks.MockSettings;
import com.google.common.collect.Maps;
import halleck.lms.Settings;
import halleck.lms.AppContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import spark.HaltException;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;


@RunWith(MockitoJUnitRunner.class)
public class SecurityFilterTest {

    private Settings settings;

    @Mock
    private Response response;

    @Mock
    private AppContext appContext;

    private SecurityFilter filter;

    @Before
    public void setUp(){
       settings = new MockSettings();
       filter = new SecurityFilter(settings, appContext);
    }

    @After
    public void tearDown(){
        MockSettings.admin = null;
    }

    @Test
    public void imagesAreNotSecured() throws Exception {
        Request request = new MockSparkRequest("/img/foo.png");

        filter.handle(request, response);

        verifyZeroInteractions(response);
    }

    @Test
    public void cssIsNotSecured() throws Exception {
        Request request = new MockSparkRequest("/css/foo.css");

        filter.handle(request, response);

        verifyZeroInteractions(response);
    }

    @Test
    public void loginPageIsAccessable() throws Exception {
        Request request = new MockSparkRequest("/login");

        filter.handle(request, response);

        verifyZeroInteractions(response);
    }

    @Test
    public void ifRegularPageAndNotLoggedInThenRedirectToLogin() throws Exception {
        Request request = new MockSparkRequest("/courses");

        filter.handle(request, response);

        verify(response).redirect("/login");
    }

    @Test
    public void ifLoggedInThenDontRedirect() throws Exception {
        Request request = new MockSparkRequest("/courses");
        request.cookies().putIfAbsent(SecurityFilter.USERNAME_COOKIE, "Fred");

        filter.handle(request, response);

        verifyZeroInteractions(response);
    }

    @Test(expected = HaltException.class)
    public void ifPageIsAnAdminPageAndUserIsNotAnAdminThenHalt() throws Exception {
        Request request = new MockSparkRequest("/admin/foo");
        request.cookies().putIfAbsent(SecurityFilter.USERNAME_COOKIE, "Fred");

        filter.handle(request, response);
    }

    @Test
    public void willNotHaltIfTheUserIsListedAsAnAdmin() throws Exception {
        Request request = new MockSparkRequest("/admin/foo");
        MockSettings.admin = "Fred";
        request.cookies().putIfAbsent(SecurityFilter.USERNAME_COOKIE, "Fred");

        filter.handle(request, response);

        verifyZeroInteractions(response);
    }

    @Test
    public void willCreateUserOnContextFromCookie() throws Exception {
        Request request = new MockSparkRequest("/courses");
        MockSettings.admin = "Fred";
        request.cookies().putIfAbsent(SecurityFilter.USERNAME_COOKIE, "Fred");

        filter.handle(request, response);

        verify(appContext).setCurrentUser("Fred");
    }



    private class MockSparkRequest extends Request {
        private final String path;
        private HashMap<String,String> cookies = Maps.newHashMap();
        private HashMap<String,Object> attributes = Maps.newHashMap();

        public MockSparkRequest(String path){
            this.path = path;
        }

        @Override
        public String pathInfo() {
            return path;
        }

        @Override
        public Map<String, String> cookies() {
            return cookies;
        }

        @Override
        public String cookie(String name) {
            return cookies.get(name);
        }

        @Override
        public void attribute(String attribute, Object value) {
            attributes.put(attribute, value);
        }

        @Override
        public Object attribute(String attribute) {
            return attributes.get(attribute);
        }
    }
}
