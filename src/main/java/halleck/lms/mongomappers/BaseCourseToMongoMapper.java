package halleck.lms.mongomappers;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import halleck.lms.Course;

import java.util.function.Function;

public class BaseCourseToMongoMapper implements Function<Course, DBObject> {
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String URL = "url";
    public static final String MAX = "max";
    public static final String USERS = "users";
    public static final String OWNER = "owner";

    @Override
    public DBObject apply(Course course) {
        return new BasicDBObject(BaseCourseToMongoMapper.ID, course.getId())
                .append(NAME, course.getName())
                .append(DESCRIPTION, course.getDescription())
                .append(URL, course.getUrl())
                .append(MAX, course.getMaxEnrollment())
                .append(USERS, course.getRegisteredUsers())
                .append(OWNER, course.getOwner());
    }
}
