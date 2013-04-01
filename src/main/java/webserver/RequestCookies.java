package webserver;

import spark.Request;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RequestCookies {
    public static final String HALLECK_NAME = "halleckName";
    private HttpServletRequest servletRequest;

    public RequestCookies(HttpServletRequest servletRequest){
        this.servletRequest = servletRequest;
    }

    public static String getUser(Request request) {
        return requestCookies(request).cookie(HALLECK_NAME);
    }

    /**
     * @return request cookies (or empty Map if cookies dosn't present)
     */
    public Map<String, String> cookies() {
        Map<String, String> result = new HashMap<String, String>();
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                result.put(cookie.getName(), cookie.getValue());
            }
        }
        return result;
    }

    /**
     * Gets cookie by name.
     * @param name name of the cookie
     * @return cookie value or null if the cookie was not found
     */
    public String cookie(String name) {
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static RequestCookies requestCookies(Request request) {
        return new RequestCookies(request.raw());
    }
}
