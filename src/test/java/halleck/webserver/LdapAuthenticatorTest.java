package halleck.webserver;


import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class LdapAuthenticatorTest {

    @Test
    @Ignore
    public void testRealLDap() throws Exception {
        LdapAuthenticator ldap = new LdapAuthenticator();

        assertTrue(ldap.authenticate("test", "foo"));
    }


}
