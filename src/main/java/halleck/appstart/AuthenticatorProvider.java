package halleck.appstart;


import com.google.inject.Inject;
import com.google.inject.Provider;
import halleck.lms.Settings;
import halleck.webserver.AuthenticationType;
import halleck.webserver.Authenticator;
import halleck.webserver.FakeAuthenticator;
import halleck.webserver.LdapAuthenticator;

public class AuthenticatorProvider implements Provider<Authenticator> {

    private Settings settings;


    @Inject
    public AuthenticatorProvider(Settings settings){
        this.settings = settings;
    }

    @Override
    public Authenticator get() {
        if(settings.getAuthenticationType().equals(AuthenticationType.LDAP)){
            return new LdapAuthenticator(settings);
        }
        return new FakeAuthenticator();
    }
}
