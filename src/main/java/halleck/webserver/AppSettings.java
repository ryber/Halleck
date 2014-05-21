package halleck.webserver;

import com.google.common.base.Enums;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import halleck.api.Settings;
import halleck.lms.Feature;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Lists.newArrayList;

public class AppSettings implements Settings {

    static final String MONGO_HOST = "mongo.host";
    static final String MONGO_PORT = "mongo.port";
    static final String SITE_NAME = "site.name";
    static final String SITE_PORT = "site.port";
    static final String SITE_ADMINS = "site.admins";
    static final String PERSISTENCE_TYPE = "persistence.type";
    static final String MONGO_USERNAME = "mongo.username";
    static final String MONGO_PASSWORD = "mongo.password";
    static final String LDAP_URL = "ldap.url";
    static final String LDAP_DOMAIN = "ldap.domain";
    static final String AUTHENTICATION_TYPE = "authentication.type";
    static final String SITE_EXTERNALMEDIA = "site.externalmedia";
    static final String COURSE_LOAD = "course.load";
    static final String ENABLED_FEATURES = "enabled.features";

    private final String ldapURL;
    private final String ldapDomain;
    private final String mongoHost;
    private final Integer mongoPort;
    private final String siteName;
    private final Integer sitePort;
    private final ImmutableList<String> admins;
    private final String persistenceType;
    private final String username;
    private final char[] password;
    private final String authType;
    private final String externalMedia;
    private final String courseLoadLocation;
    private final ImmutableList<Feature> enabledFeatures;


    public AppSettings(Properties properties) {
        this.mongoHost = properties.getProperty(MONGO_HOST);
        this.mongoPort = tryParse(properties.getProperty(MONGO_PORT));
        this.siteName = properties.getProperty(SITE_NAME);
        this.sitePort = tryParse(properties.getProperty(SITE_PORT));
        this.admins = copyOf(splitString(properties.getProperty(SITE_ADMINS)));
        this.persistenceType = properties.getProperty(PERSISTENCE_TYPE);
        this.username = properties.getProperty(MONGO_USERNAME);
        this.password = nullToEmpty(properties.getProperty(MONGO_PASSWORD)).toCharArray();
        this.ldapURL = properties.getProperty(LDAP_URL);
        this.ldapDomain = properties.getProperty(LDAP_DOMAIN);
        this.authType = properties.getProperty(AUTHENTICATION_TYPE);
        this.externalMedia = properties.getProperty(SITE_EXTERNALMEDIA);
        this.courseLoadLocation = properties.getProperty(COURSE_LOAD);
        this.enabledFeatures = copyOf(parseFeatures(properties.getProperty(ENABLED_FEATURES)));
    }

    private List<Feature> parseFeatures(String property) {
        return splitString(property).stream()
                                    .map(f -> Enums.getIfPresent(Feature.class, f))
                                    .filter(Optional::isPresent)
                                    .map(Optional::get)
                                    .collect(Collectors.toList());
    }


    private static Integer tryParse(String value){
        return Ints.tryParse(nullToEmpty(value));
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
    public com.google.common.collect.ImmutableList<String> getAdmins() {
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

    @Override
    public com.google.common.collect.ImmutableList<Feature> getEnabledFeatures() {
        return enabledFeatures;
    }

    private List<String> splitString(String commaSplitList) {
        return newArrayList(
                Splitter.on(",")
                        .omitEmptyStrings()
                        .trimResults()
                        .split(nullToEmpty(commaSplitList))
        );
    }
}
