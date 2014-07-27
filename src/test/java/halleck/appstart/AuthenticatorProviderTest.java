package halleck.appstart;


import halleck.lms.Settings;
import halleck.webserver.AuthenticationType;
import halleck.webserver.FakeAuthenticator;
import halleck.webserver.LdapAuthenticator;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticatorProviderTest {
    @Test
    public void providerWillGiveBackLDAPIfRequested() throws Exception {
        Settings s = mock(Settings.class);
        when(s.getAuthenticationType()).thenReturn(AuthenticationType.LDAP);

        AuthenticatorProvider p = new AuthenticatorProvider(s);

        assertTrue(p.get() instanceof LdapAuthenticator);
    }

    @Test
    public void testproviderWillGiveBackFakeAuthenticatorOtherwise() throws Exception {
        Settings s = mock(Settings.class);
        when(s.getAuthenticationType()).thenReturn(AuthenticationType.FAKE);

        AuthenticatorProvider p = new AuthenticatorProvider(s);

        assertTrue(p.get() instanceof FakeAuthenticator);
    }
}
