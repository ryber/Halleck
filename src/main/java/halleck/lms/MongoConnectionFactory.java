package halleck.lms;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import halleck.api.Settings;

import javax.annotation.Nullable;

public class MongoConnectionFactory {

    private static final String DBNAME = "halleck";
    private Mongo mongoClient;
    private final Settings settings;

    @Inject
    public MongoConnectionFactory(@Nullable Mongo mongoClient, Settings settings){
        this.mongoClient = mongoClient;
        this.settings = settings;
    }

    public DB getDB() {
        DB database = mongoClient.getDB(DBNAME);
        if(!Strings.isNullOrEmpty(settings.getUsername())){
            if(!database.authenticate(settings.getUsername(), settings.getPassword())){
                throw new CantAuthenticateToMongo(settings.getUsername());
            }
        }
        return database;
    }

    private static class CantAuthenticateToMongo extends RuntimeException {
        private String username;

        public CantAuthenticateToMongo(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return "Cant authenticate with the user " + username;
        }
    }
}
