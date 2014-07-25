package halleck.lms;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Strings.nullToEmpty;

public class Course implements Serializable {
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
    private List<String> childCourses = Lists.newArrayList();

    public Course(String id) {
        this(id,id);
    }

    public Course(String id, String name) {
        this.id = id;
        this.name = nullToEmpty(name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setDecription(String value){
        description = nullToEmpty(value);
    }

    public String getDescription() {
        return description;
    }

    public void setUrl(String value){
        url = value;
    }

    public String getUrl() {
        return nullToEmpty(url);
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
        return nullToEmpty(owner);
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

    public void addChild(String id1) {
        if(!childCourses.contains(id1)){
            childCourses.add(id1);
        }
    }

    public ImmutableList<String> getChildIds() {
        return ImmutableList.copyOf(childCourses);
    }

    public void removeChild(String id) {
        childCourses.remove(id);
    }

    public void addChildren(Collection<String> children) {
        children.stream().forEach(this::addChild);
    }
}
