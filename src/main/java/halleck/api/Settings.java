package halleck.api;

import java.util.List;

public interface Settings {
    String getPersistenceType();
    String getMongoHost();
    int getMongoPort();
    String getSiteName();
    int getAppPort();
    List<String> getAdmins();
    String getUsername();
    char[] getPassword();
    String getLdapUrl();
    String getLdapDomain();
    String getAuthenticationType();
}
