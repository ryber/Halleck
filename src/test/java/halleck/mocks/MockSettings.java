package halleck.mocks;

import com.google.common.collect.ImmutableList;
import halleck.lms.Settings;
import halleck.lms.Feature;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.collect.Lists.newArrayList;

public class MockSettings implements Settings {

    public static String admin;
    private ImmutableList<Feature> features = ImmutableList.of();

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
        return null;
    }

    @Override
    public char[] getPassword() {
        return new char[0];
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
    public String getAuthenticationType() {
        return "";
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
    public ImmutableList<Feature> getEnabledFeatures() {
        return features;
    }
}

