package halleck.appstart;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.Mongo;
import halleck.lms.Settings;

import java.net.UnknownHostException;


public class MongoClientProvider implements Provider<Mongo> {

    private Settings settings;

    @Inject
    public MongoClientProvider(Settings settings) {
        this.settings = settings;
    }

    @Override
    public Mongo get() {
        try {
            if (!Strings.isNullOrEmpty(settings.getMongoHost())) {
                return new Mongo(settings.getMongoHost(), settings.getMongoPort());
            }
            return null;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
