package halleck.api;

import java.util.Set;

public interface Course {
    String getId();
    String getName();
    String getDescription();
    String getUrl();
    Integer getMaxEnrollment();
    void setMaxCapacity(Integer maxCapacity);
    int getFreeSeats();
    Set<String> getRegisteredUsers();
    void addRegisteredUser(String user);
    String getContent();
    String getOwner();
    void setOwner(String owner);
}
