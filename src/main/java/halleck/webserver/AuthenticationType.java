package halleck.webserver;

import com.google.common.base.Enums;

public enum AuthenticationType {
    LDAP,
    FAKE;

    public static AuthenticationType tryGet(String s) {
        if(s == null){
            return AuthenticationType.FAKE;
        }
        return Enums.getIfPresent(AuthenticationType.class, s).or(AuthenticationType.FAKE);
    }
}
