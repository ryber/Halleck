package halleck.webserver.routs;

import com.google.inject.Inject;
import halleck.api.Settings;
import halleck.lms.AppContext;
import halleck.webserver.*;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;
import java.util.Objects;

import static halleck.webserver.MapMaker.map;

public class AuthenticationRouts extends SparkRoutCollector {
    private final ViewRenderer view;
    private final Settings settings;
    private final AppContext context;
    private final Authenticator auth;


    @Inject
    public AuthenticationRouts(ViewRenderer view,
                               Settings settings,
                               AppContext context,
                               Authenticator auth) {
        this.view = view;
        this.settings = settings;
        this.context = context;
        this.auth = auth;
    }

    public void init(){
        get(new Route("/login") {
            @Override
            public Object handle(Request request, Response response) {
                return view.render("login.mustache", getSettings(), request);
            }

            private Map getSettings() {
                return map("showpass", !Objects.equals(settings.getAuthenticationType(), "fake"));
            }
        });

        post(new Route("/login") {
            @Override
            public Object handle(Request request, Response response) {
                String username = request.queryParams("username");
                String password = request.queryParams("password");

                if(auth.authenticate(username, password)){
                    response.cookie(SecurityFilter.USERNAME_COOKIE, username);
                    return view.render("redirect.mustache", map("url", "/"), request);
                }
                return view.render("login.mustache", map("wrong", true), request);
            }
        });

        get(new Route("/logout") {
            @Override
            public Object handle(Request request, Response response) {
                response.removeCookie(SecurityFilter.USERNAME_COOKIE);
                response.redirect("/login");
                return null;
            }
        });
    }
}
