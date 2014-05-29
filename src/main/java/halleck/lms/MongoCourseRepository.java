package halleck.lms;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;


public class MongoCourseRepository implements CourseRepository {

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String URL = "url";
    public static final String MAX = "max";
    public static final String USERS = "users";
    public static final String OWNER = "owner";
    public static final String DOJO = "dojo";
    private static final String CONTENT = "content";
    private final MongoConnectionFactory mongoFactory;

    @Inject
    public MongoCourseRepository(MongoConnectionFactory mongoFactory) {
        this.mongoFactory = mongoFactory;
    }

    @Override
    public Stream<Course> getAllCourses() {
        List<Course> result = newArrayList();

        try (DBCursor allCourses = getCourseCollection().find()) {
            while (allCourses.hasNext()) {
                DBObject next = allCourses.next();
                result.add(map(next));
            }
        }
        return result.stream();
    }


    @Override
    public void putCourse(Course course) {
        if(exists(course.getId())){
            getCourseCollection().update(getId(course), map(course));
        }else {
            getCourseCollection().insert(map(course));
        }
    }

    private DBObject getId(Course course) {
        return getId(course.getId());
    }

    private DBObject getId(String courseId) {
        return new BasicDBObject("_id", courseId);
    }

    private boolean exists(final String courseId) {
        return getCourseCollection().count(getId(courseId)) > 0;
    }

    DBObject map(Course course) {
       return new BasicDBObject(ID, course.getId())
               .append(NAME, course.getName())
               .append(DESCRIPTION, course.getDescription())
               .append(URL, course.getUrl())
               .append(MAX, course.getMaxEnrollment())
               .append(USERS, course.getRegisteredUsers())
               .append(OWNER, course.getOwner())
               .append(DOJO, course.getChildIds())
               .append(CONTENT, course.getContent());
    }

    Course map(DBObject next) {
        Course course = new Course(next.get(ID).toString(), next.get(NAME).toString());
        course.setDecription(next.get(DESCRIPTION).toString());
        course.setUrl(next.get(URL).toString());
        course.setMaxCapacity((Integer) next.get(MAX));
        course.setContent(next.get(CONTENT).toString());
        course.addRegisteredUsers((Iterable<String>) next.get(USERS));
        ((List<String>)next.get(DOJO)).stream().forEach(course::addChild);
        course.setOwner((String)next.get(OWNER));
        return course;
    }

    @Override
    public Course getCourse(final String courseId) {
        return map(getCourseCollection().findOne(getId(courseId)));
    }

    private DBCollection getCourseCollection() {
        return mongoFactory.getDB().getCollection("courses");
    }
}
