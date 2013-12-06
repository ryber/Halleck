package halleck.webserver;

import spark.Request;

import java.util.Map;

public class RequestCookies {
    public static final String HALLECK_NAME = "halleckName";
    private Map<String, String> cookies;

    public RequestCookies(Map<String, String> servletRequest){
        this.cookies = servletRequest;
    }

    public static String getUser(Request request) {
        return request == null ? "" : requestCookies(request).cookie(HALLECK_NAME);
    }

    /**
     * @return request cookies (or empty Map if cookies dosn't present)
     */
    public Map<String, String> cookies() {
        return cookies;
    }

    /**
     * Gets cookie by name.
     * @param name name of the cookie
     * @return cookie value or null if the cookie was not found
     */
    public String cookie(String name) {
        return cookies.getOrDefault(name, null);
    }

    public static RequestCookies requestCookies(Request request) {
        return new RequestCookies(request.cookies());
    }
}
