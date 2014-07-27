package halleck.lms;

import com.mongodb.DB;
import com.mongodb.Mongo;
import halleck.mocks.MockSettings;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MongoConnectionFactoryTest {

    @Test
    public void ifNoUserNameIsProvidedWillConnectToDatabaseWithoutAuth() {
        MockSettings settings = new MockSettings();
        Mongo client = mock(Mongo.class);
        DB db = mock(DB.class);

        when(client.getDB(MongoConnectionFactory.DBNAME)).thenReturn(db);

        MongoConnectionFactory factory = new MongoConnectionFactory(client, settings);

        assertEquals(db, factory.getDB());
        verifyZeroInteractions(db);
    }

    @Test
    public void ifUserNameIsProvidedWillConnectToDatabaseWithAuth() {
        MockSettings settings = new MockSettings();
        settings.username = "Feyd";
        settings.password = "Password1!";

        Mongo client = mock(Mongo.class);
        DB db = mock(DB.class);

        when(client.getDB(MongoConnectionFactory.DBNAME)).thenReturn(db);

        MongoConnectionFactory factory = new MongoConnectionFactory(client, settings);
        when(db.authenticate("Feyd", "Password1!".toCharArray())).thenReturn(true);

        assertEquals(db, factory.getDB());
    }

    @Test
    public void willThrowExceptionWIthUsernameWhenAuthFails() {
        MockSettings settings = new MockSettings();
        settings.username = "Feyd";
        settings.password = "Password1!";

        Mongo client = mock(Mongo.class);
        DB db = mock(DB.class);

        when(client.getDB(MongoConnectionFactory.DBNAME)).thenReturn(db);

        MongoConnectionFactory factory = new MongoConnectionFactory(client, settings);
        when(db.authenticate("Feyd", "Password1!".toCharArray())).thenReturn(false);

        try{
            factory.getDB();
            fail("Should Have Thrown");
        }catch (Exception e){
            assertEquals("Cant authenticate with the user Feyd", e.toString());
        }
    }
}