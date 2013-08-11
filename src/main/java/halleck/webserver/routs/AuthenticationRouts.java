package halleck.webserver.routs;

import com.google.inject.Inject;
import halleck.api.Settings;
import halleck.webserver.*;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;
import java.util.Objects;

import static halleck.webserver.MapMaker.map;

public class AuthenticationRouts extends SparkRoutCollector {
    private ViewRenderer view;
    private Settings settings;
    private SecurityFilter filter;
    private Authenticator auth;


    @Inject
    public AuthenticationRouts(ViewRenderer view,
                               Settings settings,
                               SecurityFilter filter,
                               Authenticator auth) {

        this.view = view;
        this.settings = settings;
        this.filter = filter;
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
                    new ResponseCookies(response).cookie(RequestCookies.HALLECK_NAME, username);
                    return view.render("redirect.mustache", map("url", "/"), request);
                }
                return view.render("login.mustache", map("wrong", true), request);
            }
        });

        get(new Route("/logout") {
            @Override
            public Object handle(Request request, Response response) {
                new ResponseCookies(response).removeCookie(RequestCookies.HALLECK_NAME);
                response.redirect("/login");
                return null;
            }
        });
    }
}
