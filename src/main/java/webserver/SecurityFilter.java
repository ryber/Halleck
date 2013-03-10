package webserver;

import spark.Filter;
import spark.Request;
import spark.Response;

import static webserver.RequestCookies.requestCookies;

public class SecurityFilter extends Filter {
    @Override
    public void handle(Request request, Response response) {
        if(!hasUserCookieSet(request) && !isTheLoginPage(request)){
            response.redirect("/login");
        }
    }

    private boolean isTheLoginPage(Request request) {
        return request.pathInfo().contains("login");
    }

    private boolean hasUserCookieSet(Request request) {
        return requestCookies(request).cookie("halleckName") != null;
    }
}
