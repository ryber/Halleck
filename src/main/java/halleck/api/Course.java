package halleck.api;

import java.util.Set;

public interface Course {
    String getId();
    String getName();
    String getDescription();
    String getUrl();
    Integer getMaxEnrollment();
    String getDescriptionShort();
    void setMaxCapacity(Integer maxCapacity);
    int getFreeSeats();
    Set<String> getRegisteredUsers();
    void addRegisteredUser(String user);
    boolean hasFreeSeats();
    boolean isEmbedVideo();
}
