package halleck.webserver;

import halleck.lms.Feature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.function.Supplier;

import static com.google.common.collect.Lists.newArrayList;

public class AppSettingsTest {

    private Properties props;
    private AppSettings settings;

    @Before
    public void setUp() throws Exception {
        props = new Properties();
    }

    @Test
    public void mongoHost(){
        assertProp(AppSettings.MONGO_HOST, rand(), ()-> settings.getMongoHost());
    }

    @Test
    public void mongoPort(){
        assertProp(AppSettings.MONGO_PORT, 8080, ()-> settings.getMongoPort());
    }

    @Test
    public void mongoUserName(){
        assertProp(AppSettings.MONGO_USERNAME, rand(), ()-> settings.getUsername());
    }

    @Test
    public void mongoPassword(){
        props.setProperty(AppSettings.MONGO_PASSWORD, "foo");
        settings = new AppSettings(props);

        Assert.assertArrayEquals("foo".toCharArray(), settings.getPassword());
    }

    @Test
    public void authenticationType(){
        assertProp(AppSettings.AUTHENTICATION_TYPE, rand(), ()-> settings.getAuthenticationType());
    }

    @Test
    public void getCourseLoadPath(){
        assertProp(AppSettings.COURSE_LOAD, rand(), ()-> settings.getCourseLoadLocation());
    }

    @Test
    public void getLDapDomain(){
        assertProp(AppSettings.LDAP_DOMAIN, rand(), ()-> settings.getLdapDomain());
    }

    @Test
    public void getLDapUrl(){
        assertProp(AppSettings.LDAP_URL, rand(), ()-> settings.getLdapUrl());
    }

    @Test
    public void getPersistenceType(){
        assertProp(AppSettings.PERSISTENCE_TYPE, rand(), ()-> settings.getPersistenceType());
    }

    @Test
    public void getAdmins(){
        props.setProperty(AppSettings.SITE_ADMINS, "Leto,Paul");
        settings = new AppSettings(props);

        Assert.assertEquals(newArrayList("Leto", "Paul"), settings.getAdmins());
    }

    @Test
    public void getEnabledFeatures(){
        assertProp(AppSettings.ENABLED_FEATURES, rand(), ()-> settings.getFeatureLoadLocation());
    }

    @Test
    public void getSiteName(){
        assertProp(AppSettings.SITE_NAME, rand(), ()-> settings.getSiteName());
    }

    @Test
    public void getExtraMedia(){
        assertProp(AppSettings.SITE_EXTERNALMEDIA, rand(), ()-> settings.getExternalMediaLocation());
    }

    @Test
    public void getSiteport(){
        assertProp(AppSettings.SITE_PORT, 1234, ()-> settings.getAppPort());
    }

    private String rand(){
        return UUID.randomUUID().toString();
    }

    private void assertProp(String propEntry, Object expectedValue, Supplier method){
        props.setProperty(propEntry, expectedValue.toString());
        settings = new AppSettings(props);

        Assert.assertEquals(expectedValue, method.get());
    }
}