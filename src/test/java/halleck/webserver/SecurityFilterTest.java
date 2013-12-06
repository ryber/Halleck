package halleck.webserver;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import spark.HaltException;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SecurityFilterTest {

    @Mock
    private AppSettings settings;

    @Mock
    private Response response;

    @InjectMocks
    private SecurityFilter filter;

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
        when(settings.getAdmins()).thenReturn(Lists.newArrayList("Fred"));
        request.cookies().putIfAbsent(SecurityFilter.USERNAME_COOKIE, "Fred");

        filter.handle(request, response);

        verifyZeroInteractions(response);
    }

    private class MockSparkRequest extends Request {
        private final String path;
        private HashMap<String,String> cookies = Maps.newHashMap();;

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
    }
}
