package halleck.mocks;

import com.google.common.collect.ImmutableList;
import halleck.lms.Settings;
import halleck.lms.Feature;
import halleck.webserver.AuthenticationType;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.collect.Lists.newArrayList;

public class MockSettings implements Settings {

    public static String admin;
    public AuthenticationType authType = AuthenticationType.FAKE;
    public String username;
    public String password;

    @Override
    public String getPersistenceType() {
        return null;
    }

    @Override
    public String getMongoHost() {
        return null;
    }

    @Override
    public int getMongoPort() {
        return 0;
    }

    @Override
    public String getSiteName() {
        return null;
    }

    @Override
    public int getAppPort() {
        return 0;
    }

    @Override
    public ImmutableList<String> getAdmins() {
        return admin == null ? of() : of(admin);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public char[] getPassword() {
        return password.toCharArray();
    }

    @Override
    public String getLdapUrl() {
        return null;
    }

    @Override
    public String getLdapDomain() {
        return null;
    }

    @Override
    public AuthenticationType getAuthenticationType() {
        return authType;
    }

    @Override
    public String getExternalMediaLocation() {
        return null;
    }

    @Override
    public String getCourseLoadLocation() {
        return null;
    }

    @Override
    public String getFeatureLoadLocation() {
        return null;
    }
}

