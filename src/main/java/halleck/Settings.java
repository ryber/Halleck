package halleck;

import com.google.common.primitives.Ints;

import java.util.Properties;

public class Settings {

    private String mongoHost;
    private Integer mongoPort;
    private String siteName;
    private Integer sitePort;

    public Settings(Properties properties) {
        this.mongoHost = properties.getProperty("mongo.host");
        this.mongoPort = Ints.tryParse(properties.getProperty("mongo.port"));
        this.siteName = properties.getProperty("site.name");
        this.sitePort = Ints.tryParse(properties.getProperty("site.port"));
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
}
