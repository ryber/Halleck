package halleck.webserver.routs;

import com.google.inject.Inject;
import halleck.api.Settings;
import halleck.lms.AppContext;
import halleck.webserver.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;
import java.util.Objects;

import static halleck.webserver.MapMaker.map;

public class AuthenticationRouts extends SparkRoutCollector {

    private final Settings settings;
    private final Authenticator auth;


    @Inject
    public AuthenticationRouts(Settings settings,
                               Authenticator auth) {

        this.settings = settings;
        this.auth = auth;
    }

    public void init(){
        get(new FullPage("/login") {
            @Override
            public ModelAndView action(Request request, Response response) {
                return new ModelAndView(getSettings(),"login.mustache");
            }

            private Map getSettings() {
                return map("showpass", !Objects.equals(settings.getAuthenticationType(), "fake"));
            }
        });

        post(new FullPage("/login") {
            @Override
            public ModelAndView action(Request request, Response response) {
                String username = request.queryParams("username");
                String password = request.queryParams("password");

                if(auth.authenticate(username, password)){
                    response.cookie(SecurityFilter.USERNAME_COOKIE, username);
                    return new ModelAndView(map("url", "/"), "redirect.mustache");
                }
                return new ModelAndView(map("wrong", true), "login.mustache");
            }
        });

        get(new FullPage("/logout") {
            @Override
            public ModelAndView action(Request request, Response response) {
                response.removeCookie(SecurityFilter.USERNAME_COOKIE);
                response.redirect("/login");
                return null;
            }
        });
    }
}
