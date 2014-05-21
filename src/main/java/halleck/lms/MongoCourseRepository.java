package halleck.lms;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.List;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;


public class MongoCourseRepository implements CourseRepository {

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
                result.add(createCourse(next));
            }
        }
        return result.stream();
    }

    private OnlineCourse createCourse(DBObject next) {
        OnlineCourse onlineCourse = new OnlineCourse(
                next.get("_id").toString(),
                next.get("name").toString(),
                next.get("description").toString(),
                next.get("url").toString(),
                (Integer) next.get("max"),
                next.get("content").toString()
                );

        onlineCourse.addRegisteredUsers((Iterable<String>)next.get("users"));
        return onlineCourse;
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

    private DBObject map(Course course) {
        return new BasicDBObject("_id", course.getId())
                .append("name", course.getName())
                .append("description", course.getDescription())
                .append("url", course.getUrl())
                .append("max", course.getMaxEnrollment())
                .append("users", course.getRegisteredUsers());
    }

    @Override
    public Course getCourse(final String courseId) {
        return createCourse(getCourseCollection().findOne(getId(courseId)));
    }

    private DBCollection getCourseCollection() {
        return mongoFactory.getDB().getCollection("courses");
    }
}
