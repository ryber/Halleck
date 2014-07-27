package halleck.webserver;

import halleck.mocks.MockSettings;
import org.junit.Test;
import spark.ModelAndView;

import java.util.Map;

import static org.junit.Assert.*;

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
}