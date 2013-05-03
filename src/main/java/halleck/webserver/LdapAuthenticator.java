package halleck.webserver;


import halleck.api.Settings;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.Map;

public class LdapAuthenticator implements Authenticator {

    private final Settings settings;

    @Inject
    public LdapAuthenticator(Settings settings){
        this.settings = settings;
    }
    
    public boolean authenticate(String username, String password){
        try
        {
            DirContext ctx = new InitialDirContext(getLDAPCommand(username, password));
            ctx.close();

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    Hashtable<String, String> getLDAPCommand(String username, String password) {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, settings.getLdapUrl());
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, formatUserName(username));
        env.put(Context.SECURITY_CREDENTIALS, password);

        //log(env);

        return env;
    }

    private void log(Hashtable<String, String> env) {
        for(Map.Entry<String, String> c : env.entrySet()){
            System.out.println(c.getKey() + " :: " + c.getValue());
        }
    }

    private String formatUserName(String username) {
        return String.format("%s\\%s", settings.getLdapDomain(), username);
    }
}
