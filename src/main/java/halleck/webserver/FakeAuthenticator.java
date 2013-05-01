package halleck.webserver;


public class FakeAuthenticator implements Authenticator {
    @Override
    public boolean authenticate(String username, String password) {
        return true;
    }
}
