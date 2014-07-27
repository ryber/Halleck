package halleck.webserver;

import halleck.lms.AppContext;
import halleck.mocks.MockSettings;
import halleck.mocks.MockSparkRequest;
import org.junit.Test;
import spark.ModelAndView;
import spark.Response;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Test
    public void loginFormHasTheBasicLoginTemplate() {
        AuthController controller = new AuthController(new MockSettings(),null,null);
        ModelAndView view = controller.getLoginForm();
        assertEquals(AuthController.LOGIN_TEMPLATE, view.getViewName());
    }

    @Test
    public void showPassIsFalseIfAuthIsFake() {
        MockSettings settings = new MockSettings();
        settings.authType = AuthenticationType.FAKE;
        AuthController controller = new AuthController(settings,null,null);

        Map model = (Map)controller.getLoginForm().getModel();
        assertEquals(false, model.get(AuthController.SHOWPASSWORD_VAR));
    }

    @Test
    public void showPassIsTrueIfAuthIsReal() {
        MockSettings settings = new MockSettings();
        settings.authType = AuthenticationType.LDAP;
        AuthController controller = new AuthController(settings,null,null);

        Map model = (Map)controller.getLoginForm().getModel();
        assertEquals(true, model.get(AuthController.SHOWPASSWORD_VAR));
    }

    @Test
    public void logoutClearsCookiesAndContext() {
        AppContext context = mock(AppContext.class);
        Response response = mock(Response.class);

        AuthController controller = new AuthController(null, null, context);

        controller.logoutAction(response);

        verify(context).clear();
        verify(response).removeCookie(SecurityFilter.SESSION_ID);
        verify(response).removeCookie(SecurityFilter.USERNAME_COOKIE);
    }

    @Test
    public void loginUsesUserNameAndPassFromRequest() {
        MockSparkRequest request = new MockSparkRequest("");
        request.getParams().put("username", "Feyd");
        request.getParams().put("password", "sweetums42");

        Authenticator auth = mock(Authenticator.class);
        when(auth.authenticate("Feyd", "sweetums42")).thenReturn(true);
        AuthController controller = new AuthController(null, auth, null);

        Response response = mock(Response.class);
        controller.loginAction(request, response);

        verify(response).cookie(SecurityFilter.USERNAME_COOKIE, "Feyd");
    }

    @Test
    public void loginFailureScenario() {
        MockSparkRequest request = new MockSparkRequest("");
        request.getParams().put("username", "Feyd");
        request.getParams().put("password", "sweetums42");

        Authenticator auth = mock(Authenticator.class);
        when(auth.authenticate("Feyd", "sweetums42")).thenReturn(false);
        AuthController controller = new AuthController(null, auth, null);

        Response response = mock(Response.class);
        controller.loginAction(request, response);

        verifyZeroInteractions(response);
    }

}