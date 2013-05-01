package halleck.webserver;


public interface Authenticator {
    boolean authenticate(String username, String password);
}
