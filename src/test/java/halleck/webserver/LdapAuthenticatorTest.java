package halleck.webserver;


import halleck.lms.Settings;
import org.junit.Test;

import javax.naming.Context;
import java.util.Hashtable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LdapAuthenticatorTest {

    /*
    @Test
    public void testRealLDap() throws Exception {
        LdapAuthenticator ldap = new LdapAuthenticator(null);

        assertTrue(ldap.authenticate("foo", "smootches"));
    }
    */

    @Test
    public void willGetProperQueryCommand() throws Exception {
        Settings s = mock(Settings.class);
        when(s.getLdapUrl()).thenReturn("ldap://somewhere");
        when(s.getLdapDomain()).thenReturn("FOO");

        LdapAuthenticator a = new LdapAuthenticator(s);

        Hashtable<String, String> query = a.getLDAPCommand("moe", "smootches");


        assertEquals("ldap://somewhere", query.get(Context.PROVIDER_URL));
        assertEquals("FOO\\moe", query.get(Context.SECURITY_PRINCIPAL));
        assertEquals("smootches", query.get(Context.SECURITY_CREDENTIALS));

    }
}
