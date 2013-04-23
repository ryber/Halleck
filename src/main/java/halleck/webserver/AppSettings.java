package halleck.webserver;

import com.google.common.base.Splitter;
import com.google.common.primitives.Ints;
import halleck.api.Settings;

import java.util.List;
import java.util.Properties;

import static com.google.common.base.Strings.nullToEmpty;
import static com.google.common.collect.Lists.newArrayList;

public class AppSettings implements Settings {

    private String mongoHost;
    private Integer mongoPort;
    private String siteName;
    private Integer sitePort;
    private List<String> admins;
    private String persistenceType;
    private String username;
    private char[] password;

    public AppSettings(Properties properties) {
        this.mongoHost = properties.getProperty("mongo.host");
        this.mongoPort = Ints.tryParse(properties.getProperty("mongo.port"));
        this.siteName = properties.getProperty("site.name");
        this.sitePort = Ints.tryParse(properties.getProperty("site.port"));
        this.admins = getAdmins(properties.getProperty("site.admins"));
        this.persistenceType = properties.getProperty("persistence.type");
        this.username = properties.getProperty("mongo.username");
        this.password = nullToEmpty(properties.getProperty("mongo.password")).toCharArray();
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
}
