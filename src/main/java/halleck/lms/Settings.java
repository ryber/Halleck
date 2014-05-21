package halleck.lms;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import halleck.lms.Feature;

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
    String getAuthenticationType();
    String getExternalMediaLocation();
    String getCourseLoadLocation();
    ImmutableList<Feature> getEnabledFeatures();
}
