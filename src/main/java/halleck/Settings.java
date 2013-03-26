package halleck;

import com.google.common.primitives.Ints;

import java.util.Properties;

public class Settings {

    private Properties properties;

    public Settings(Properties properties) {
        this.properties = properties;
    }


    public String getMongoHost(){
        return (String)properties.get("mongo.host");
    }

    public int getMongoPort() {
        return Ints.tryParse((String)properties.get("mongo.port"));
    }

    public String getSiteName(){
        return (String)properties.get("site.name");
    }
}
