package halleck;

import java.util.Set;

public interface Course extends CourseInput {
    String getDescriptionShort();
    void setMaxCapacity(Integer maxCapacity);
    int getFreeSeats();
    Set<String> getRegisteredUsers();
    void addRegisteredUser(String user);
    boolean hasFreeSeats();
}
