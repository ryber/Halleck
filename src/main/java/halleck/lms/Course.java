package halleck.lms;

import com.google.common.collect.Sets;

import javax.annotation.Nullable;
import java.util.Set;

import static com.google.common.base.Strings.nullToEmpty;

public class Course {
    public static final String EMPTY = "";
    private final String id;
    private String name = EMPTY;
    private String description = EMPTY;
    private Integer maxCapacity;
    private Set<String> registeredUsers = Sets.newHashSet();
    public static final int UNLIMITED_ENROLLMENT = 999;
    private String url = EMPTY;
    private String content = EMPTY;
    private String owner = EMPTY;

    public Course(String id, String name, String description) {
        this.id = id;
        this.name = nullToEmpty(name);
        this.description = nullToEmpty(description);
    }

    public Course(Course courseInput) {
        this(courseInput.getId(), courseInput.getName(), courseInput.getDescription());
        this.url = nullToEmpty(courseInput.getUrl());
        this.maxCapacity = courseInput.getMaxEnrollment();
        this.content = nullToEmpty(courseInput.getContent());
    }

    public Course(String id, String name, String description, String url, Integer max, String content) {
        this(id, name, description);
        this.url = nullToEmpty(url);
        this.maxCapacity = max;
        this.content = nullToEmpty(content);
    }

    public Course(String id) {
        this(id,id,id);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public Integer getMaxEnrollment() {
        return maxCapacity;
    }

    public void setMaxCapacity(@Nullable Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getFreeSeats() {
        if(maxCapacity == null){
            return UNLIMITED_ENROLLMENT;
        }
        return maxCapacity - registeredUsers.size();
    }

    public Set<String> getRegisteredUsers() {
        return registeredUsers;
    }

    public void addRegisteredUser(String user) {
        registeredUsers.add(user);
    }

    public String getContent() {
        return content;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String value) {
        owner = value;
    }

    public void setContent(String value) {
        this.content = value;
    }

    public void addRegisteredUsers(Iterable<String> users) {
        users.forEach(this::addRegisteredUser);
    }
}
