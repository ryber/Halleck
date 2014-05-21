package halleck.webserver;

import com.google.inject.Inject;
import halleck.lms.Settings;
import halleck.lms.AppContext;
import spark.Filter;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;


public class SecurityFilter implements Filter {

    public static final String USERNAME_COOKIE = "halleckName";
    public static final String USER_KEY = "USER";
    public static final String SITE_NAME = "SITE_NAME";
    private final Settings settings;
    private final AppContext context;

    @Inject
    public SecurityFilter(Settings settings, AppContext context){
        this.settings = settings;
        this.context = context;
    }

    @Override
    public void handle(Request request, Response response) {
        if(!isExempt(request.pathInfo())){
            handleNonExemptPages(request, response);
        }
    }

    private void handleNonExemptPages(Request request, Response response) {
        String user = getUserFromCookies(request);
        if(user == null){
            response.redirect("/login");
        }

        setUserOnContext(user);

        if(isAdminPage(request) && !userIsAdmin(user)){
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


}
