package halleck.webserver.routs;

import com.google.inject.Inject;
import halleck.api.Settings;
import halleck.webserver.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

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
        get( "/login", (q,p) -> new ModelMapView(getSettings(),"login.mustache"));
        post("/login", (q,p) -> loginAction(q, p));
        get("/logout", (q,p) -> logoutAction(p));
    }

    private ModelMapView logoutAction(Response response) {
        response.removeCookie(SecurityFilter.USERNAME_COOKIE);
        response.redirect("/login");
        return null;
    }

    private ModelMapView loginAction(Request request, Response response) {
        String username = request.queryParams("username");
        String password = request.queryParams("password");

        if(auth.authenticate(username, password)){
            response.cookie(SecurityFilter.USERNAME_COOKIE, username);
            return new ModelMapView(map("url", "/"), "redirect.mustache");
        }
        return new ModelMapView(map("wrong", true), "login.mustache");
    }

    private Map getSettings() {
        return map("showpass", !Objects.equals(settings.getAuthenticationType(), "fake"));
    }
}
