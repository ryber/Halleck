package halleck.webserver;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import halleck.lms.CurrentUser;
import halleck.lms.Settings;
import halleck.lms.AppContext;
import spark.Filter;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static spark.Spark.halt;


public class SecurityFilter implements Filter {

    public static final String USERNAME_COOKIE = "halleckName";
    public static final String USER_KEY = "USER";
    public static final String SITE_NAME = "SITE_NAME";
    private static final CurrentUser NOT_LOGGED_IN_USER = new CurrentUser("") {
        @Override
        public boolean isAuthenticated() {
            return false;
        }
    };

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
        CurrentUser user = getUser(request);

        if(!user.isAuthenticated()){
            response.redirect("/login");
        }

        setUserOnContext(user);

        if(isAdminPage(request) && !userIsAdmin(user)){
            halt(403);
        }
    }


    private void setUserOnContext(CurrentUser user) {
        context.setCurrentUser(user);
    }

    private CurrentUser getUser(Request request) {
        String username = request.cookie(USERNAME_COOKIE);
        if(!Strings.isNullOrEmpty(username)){
            return new CurrentUser(username, request.headers("Accept-Language"));
        }
        return NOT_LOGGED_IN_USER;
    }

    private boolean userIsAdmin(CurrentUser user) {
        return settings.getAdmins().contains(user.getUserName());
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
