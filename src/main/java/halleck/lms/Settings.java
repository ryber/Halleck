package halleck.lms;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import halleck.lms.Feature;
import halleck.webserver.AuthenticationType;

import java.util.List;

public interface Settings {
    String getPersistenceType();
    String getMongoHost();
    int getMongoPort();
    String getSiteName();
    int getAppPort();
    ImmutableList<String> getAdmins();
    String getUsername();
    char[] getPassword();
    String getLdapUrl();
    String getLdapDomain();
    AuthenticationType getAuthenticationType();
    String getExternalMediaLocation();
    String getCourseLoadLocation();
    String getFeatureLoadLocation();
}
