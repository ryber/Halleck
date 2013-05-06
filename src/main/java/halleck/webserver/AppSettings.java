package halleck.webserver;

import com.google.common.base.Splitter;
import com.google.common.primitives.Ints;
import halleck.api.Settings;

import java.util.List;
import java.util.Properties;

import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.Lists.newArrayList;

public class AppSettings implements Settings {

    private final String ldapURL;
    private final String ldapDomain;
    private final String mongoHost;
    private final Integer mongoPort;
    private final String siteName;
    private final Integer sitePort;
    private final List<String> admins;
    private final String persistenceType;
    private final String username;
    private final char[] password;
    private final String authType;
    private final String externalMedia;
    private final String courseLoadLocation;

    public AppSettings(Properties properties) {
        this.mongoHost = properties.getProperty("mongo.host");
        this.mongoPort = Ints.tryParse(properties.getProperty("mongo.port"));
        this.siteName = properties.getProperty("site.name");
        this.sitePort = Ints.tryParse(properties.getProperty("site.port"));
        this.admins = getAdmins(properties.getProperty("site.admins"));
        this.persistenceType = properties.getProperty("persistence.type");
        this.username = properties.getProperty("mongo.username");
        this.password = nullToEmpty(properties.getProperty("mongo.password")).toCharArray();
        this.ldapURL = properties.getProperty("ldap.url");
        this.ldapDomain = properties.getProperty("ldap.domain");
        this.authType = properties.getProperty("authentication.type");
        this.externalMedia = properties.getProperty("site.externalmedia");
        this.courseLoadLocation = properties.getProperty("course.load");
    }



    private List<String> getAdmins(String propLIst) {
        return newArrayList(
                Splitter.on(",").omitEmptyStrings().trimResults().split(propLIst)
        );
    }

    @Override
    public String getPersistenceType() {
        return this.persistenceType;
    }

    @Override
    public String getMongoHost() {
        return mongoHost;
    }

    @Override
    public int getMongoPort() {
        return mongoPort;
    }

    @Override
    public String getSiteName() {
        return siteName;
    }

    @Override
    public int getAppPort() {
        return sitePort;
    }

    @Override
    public List<String> getAdmins() {
        return admins;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public char[] getPassword() {
        return password;
    }

    @Override
    public String getLdapUrl() {
        return ldapURL;
    }

    @Override
    public String getLdapDomain() {
        return ldapDomain;
    }

    @Override
    public String getAuthenticationType() {
        return authType;
    }

    @Override
    public String getExternalMediaLocation() {
        return externalMedia;
    }

    @Override
    public String getCourseLoadLocation() {
        return courseLoadLocation;
    }
}
