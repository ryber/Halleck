package halleck.BDDTests.mocks;

import halleck.api.Settings;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class MockSettings implements Settings {

    public static String admin;

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
    public List<String> getAdmins() {
        return admin == null ? newArrayList() : newArrayList(admin);
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
}

