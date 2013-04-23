package halleck;

import java.util.List;

public interface Settings {
    String getPersistenceType();
    String getMongoHost();
    int getMongoPort();
    String getSiteName();
    int getAppPort();
    List<String> getAdmins();
    String getUsername();
    char[] getPassword();
}
