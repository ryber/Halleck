package halleck;

import com.google.common.base.Splitter;
import com.google.common.primitives.Ints;

import java.util.List;
import java.util.Properties;

import static com.google.common.collect.Lists.newArrayList;

public class Settings {

    private String mongoHost;
    private Integer mongoPort;
    private String siteName;
    private Integer sitePort;
    private List<String> admins;

    public Settings(Properties properties) {
        this.mongoHost = properties.getProperty("mongo.host");
        this.mongoPort = Ints.tryParse(properties.getProperty("mongo.port"));
        this.siteName = properties.getProperty("site.name");
        this.sitePort = Ints.tryParse(properties.getProperty("site.port"));
        this.admins = getAdmins(properties.getProperty("site.admins"));
    }

    private List<String> getAdmins(String propLIst) {
        return newArrayList(
                Splitter.on(",").omitEmptyStrings().trimResults().split(propLIst)
        );
    }

    public String getMongoHost() {
        return mongoHost;
    }

    public int getMongoPort() {
        return mongoPort;
    }

    public String getSiteName() {
        return siteName;
    }

    public int getAppPort() {
        return sitePort;
    }

    public List<String> getAdmins() {
        return admins;
    }
}
