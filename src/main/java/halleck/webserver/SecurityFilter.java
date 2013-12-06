package halleck.webserver;

import com.google.inject.Inject;
import halleck.api.Settings;
import halleck.lms.AppContext;
import spark.Filter;
import spark.Request;
import spark.Response;

import static halleck.webserver.RequestCookies.getUser;
import static halleck.webserver.RequestCookies.requestCookies;

public class SecurityFilter extends Filter {

    public static final String USERNAME_COOKIE = "halleckName";
    private final Settings settings;
    private final AppContext context;

    @Inject
    public SecurityFilter(Settings settings, AppContext context){
        this.settings = settings;
        this.context = context;
    }

    @Override
    public void handle(Request request, Response response) {
        String user = getUserFromCookies(request);

        if(!hasUserCookieSet(request) && !isExempt(request.pathInfo())){
            response.redirect("/login");
        }

        setUserOnContext(user);

        if(isAdminPage(request) && !userIsAdmin(getUser(request))){
            halt(403);
        }
    }

    private void setUserOnContext(String user) {
        context.setCurrentUser(user);
    }

    private String getUserFromCookies(Request request) {
        return request.cookie(USERNAME_COOKIE);
    }

    private boolean userIsAdmin(String user) {
        return settings.getAdmins().contains(user);
    }

    private boolean isAdminPage(Request request) {
        return request.pathInfo().startsWith("/admin");
    }

    private boolean isExempt(String path) {
        return path.contains("login")
                || path.endsWith(".css")
                || path.endsWith(".png");
    }

    private boolean hasUserCookieSet(Request request) {
        return requestCookies(request).cookie(USERNAME_COOKIE) != null;
    }
}
