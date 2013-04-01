package webserver;

import com.google.inject.Inject;
import halleck.Settings;
import spark.Filter;
import spark.Request;
import spark.Response;

import static webserver.RequestCookies.getUser;
import static webserver.RequestCookies.requestCookies;

public class SecurityFilter extends Filter {

    private Settings settings;

    @Inject
    public SecurityFilter(Settings settings){
        this.settings = settings;
    }

    @Override
    public void handle(Request request, Response response) {
        if(!hasUserCookieSet(request) && !isTheLoginPage(request)){
            response.redirect("/login");
        }

        if(isAdminPage(request) && !userIsAdmin(request)){
            halt(403);
        }
    }

    private boolean userIsAdmin(Request request) {
        return settings.getAdmins().contains(getUser(request));
    }

    private boolean isAdminPage(Request request) {
        return request.pathInfo().startsWith("/admin");
    }

    private boolean isTheLoginPage(Request request) {
        return request.pathInfo().contains("login");
    }

    private boolean hasUserCookieSet(Request request) {
        return requestCookies(request).cookie("halleckName") != null;
    }
}
