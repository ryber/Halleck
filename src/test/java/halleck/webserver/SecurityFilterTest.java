package halleck.webserver;

import com.google.common.collect.ImmutableMap;
import halleck.mocks.MockContext;
import halleck.mocks.MockRequest;
import halleck.mocks.MockSettings;
import halleck.mocks.MockSparkRequest;
import halleck.lms.Settings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import spark.HaltException;
import spark.Request;
import spark.Response;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;


@RunWith(MockitoJUnitRunner.class)
public class SecurityFilterTest {

    private Settings settings;

    @Mock
    private Response response;


    private MockContext appContext;

    private SecurityFilter filter;

    @Before
    public void setUp(){
        appContext = new MockContext();
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
        request.cookies().putIfAbsent(SecurityFilter.SESSION_ID, "FID");

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
        request.cookies().putIfAbsent(SecurityFilter.SESSION_ID, "fid");

        filter.handle(request, response);

        verifyZeroInteractions(response);
    }

    @Test
    public void willCreateUserOnContextFromCookie() throws Exception {
        MockRequest rawRequest = new MockRequest("/courses", Locale.GERMAN);
        MockSparkRequest request = new MockSparkRequest(rawRequest);

        MockSettings.admin = "Fred";
        request.cookies().putIfAbsent(SecurityFilter.USERNAME_COOKIE, "Fred");

        filter.handle(request, response);

        assertEquals("Fred", appContext.currentUser().getUserName());
        assertEquals(Locale.GERMAN, appContext.currentUser().getLocale());
    }


}
