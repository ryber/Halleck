package lms.learningobjects;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.sun.istack.internal.Nullable;
import halleck.Course;

import java.util.Set;

public class Olt implements Course {
    private final String id;
    private String name;
    private String descrription = "";
    private Integer maxCapacity;
    private Set<String> registeredUsers = Sets.newHashSet();
    public static final int UNLIMITED_ENROLLMENT = 999;

    public Olt(String id, String name, String descrription) {
        this.id = id;
        this.name = name;
        this.descrription = Strings.nullToEmpty(descrription);
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
        if(descrription.length() > 100){
            return descrription.substring(0, 100);
        }
        return descrription;
    }

    @Override
    public String getVideoName() {
        return "E1-960x540.mp4";
    }

    @Override
    public void setMaxCapacity(@Nullable Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public int getFreeSeats() {
        if(maxCapacity == null){
            return UNLIMITED_ENROLLMENT;
        }
        return maxCapacity - registeredUsers.size();
    }

    @Override
    public Set<String> getRegisteredUsers() {
        return registeredUsers;
    }

    @Override
    public void addRegisteredUser(String user) {
        registeredUsers.add(user);
    }

    public boolean hasFreeSeats() {
        return getFreeSeats() > 0;
    }
}
