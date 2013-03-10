package webserver;

import spark.Response;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class ResponseCookies {

    private HttpServletResponse response;

    public ResponseCookies(HttpServletResponse response){
        this.response = response;
    }

    public ResponseCookies(Response response) {
        this(response.raw());
    }

    /**
     * Adds not persistent cookie to the response.
     * Can be invoked multiple times to insert more than one cookie.
     *
     * @param name name of the cookie
     * @param value value of the cookie
     */
    public void cookie(String name, String value) {
        cookie(name, value, -1);
    }

    /**
     * Adds cookie to the response. Can be invoked multiple times to insert more than one cookie.
     *
     * @param name name of the cookie
     * @param value value of the cookie
     * @param maxAge max age of the cookie in seconds (negative for the not persistent cookie,
     * zero - deletes the cookie)
     */
    public void cookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * Removes the cookie.
     *
     * @param name name of the cookie
     */
    public void removeCookie(String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
