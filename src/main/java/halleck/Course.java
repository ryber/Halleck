package halleck;

import java.util.Set;

public interface Course {
    String getId();
    String getName();
    String getDescription();
    String getDescriptionShort();
    String getVideoName();
    void setMaxCapacity(int maxCapacity);
    int getFreeSeats();
    Set<String> registeredUsers();
    void addRegisteredUser(String user);
}
