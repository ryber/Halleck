package halleck;

import java.util.Set;

public interface Course {
    String getId();
    String getName();
    String getDescription();
    String getDescriptionShort();
    String getContentUrl();
    void setMaxCapacity(Integer maxCapacity);
    int getFreeSeats();
    Set<String> getRegisteredUsers();
    void addRegisteredUser(String user);
    boolean hasFreeSeats();
}
