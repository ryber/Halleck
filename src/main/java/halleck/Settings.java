package halleck;

import com.google.common.primitives.Ints;

import java.util.Properties;

public class Settings {

    private String mongoHost;
    private Integer mongoPort;
    private String siteName;

    public Settings(Properties properties) {
        this.mongoHost = (String) properties.getProperty("mongo.host");
        this.mongoPort = Ints.tryParse((String) properties.getProperty("mongo.port"));
        this.siteName = (String) properties.getProperty("site.name");
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
}
