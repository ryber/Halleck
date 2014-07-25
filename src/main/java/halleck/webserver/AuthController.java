package halleck.webserver;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import halleck.lms.AppContext;
import halleck.lms.Settings;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Map;
import java.util.Objects;

import static com.google.common.collect.ImmutableMap.of;

public class AuthController {

    private final Settings settings;
    private final Authenticator auth;
    private AppContext context;


    @Inject
    public AuthController(Settings settings,
                          Authenticator auth,
                          AppContext context) {

        this.settings = settings;
        this.auth = auth;
        this.context = context;
    }


    public ModelAndView getLoginForm() {
        return new ModelAndView(getSettings(),"login.mustache");
    }

    public ModelAndView logoutAction(Response response) {
        context.clear();
        response.removeCookie(SecurityFilter.USERNAME_COOKIE);
        response.redirect("/login");
        return null;
    }

    public ModelAndView loginAction(Request request, Response response) {
        String username = request.queryParams("username");
        String password = request.queryParams("password");

        if(auth.authenticate(username, password)){
            response.cookie(SecurityFilter.USERNAME_COOKIE, username);
            return new ModelAndView(of("url", "/"), "redirect.mustache");
        }
        return new ModelAndView(of("wrong", true), "login.mustache");
    }

    private Map getSettings() {
        return of("showpass", !Objects.equals(settings.getAuthenticationType(), "fake"));
    }
}
