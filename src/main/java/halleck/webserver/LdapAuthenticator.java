package halleck.webserver;


import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class LdapAuthenticator {

    private final String ldapLocation;

    public LdapAuthenticator(){
        ldapLocation = String.format("ldap://%s:%s", "ldap.deere.com", "389");
    }
    
    public boolean authenticate(String username, String password){
        try
        {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, ldapLocation);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "uid=JDNET\\"+ username +",ou=Unknown"); //we have 2 \\ because it's a escape char
            env.put(Context.SECURITY_CREDENTIALS, password);


            DirContext ctx = new InitialDirContext(env);
            ctx.close();

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
