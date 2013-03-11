package lms.learningobjects;

import com.google.common.collect.Sets;
import halleck.Course;

import java.util.Set;

public class Olt implements Course {
    private final String id;
    private String name;
    private final String descrription;
    private int maxCapacity = 0;
    private Set<String> registeredUsers = Sets.newHashSet();

    public Olt(String id, String name, String descrription) {
        this.id = id;
        this.name = name;
        this.descrription = descrription;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return descrription;
    }

    @Override
    public String getDescriptionShort() {
        return descrription.substring(0, 100);
    }

    @Override
    public String getVideoName() {
        return "E1-960x540.mp4";
    }

    @Override
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public int getFreeSeats() {
        return maxCapacity - registeredUsers.size();
    }

    @Override
    public Set<String> registeredUsers() {
        return registeredUsers;
    }

    @Override
    public void addRegisteredUser(String user) {
        registeredUsers.add(user);
    }
}
