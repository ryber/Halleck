package halleck.appstart;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.Mongo;
import halleck.api.Settings;

import java.net.UnknownHostException;


public class MongoClientProvider implements Provider<Mongo> {

    private Settings settings;

    @Inject
    public MongoClientProvider(Settings settings){
        this.settings = settings;
    }

    @Override
    public Mongo get() {
        try {
            return new Mongo(settings.getMongoHost(), settings.getMongoPort());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
