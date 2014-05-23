package halleck.lms;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import halleck.lms.mongomappers.BaseCourseToMongoMapper;

import java.util.List;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static halleck.lms.mongomappers.BaseCourseToMongoMapper.*;


public class MongoCourseRepository implements CourseRepository {

    private final MongoConnectionFactory mongoFactory;
    private final BaseCourseToMongoMapper courseMapper;

    @Inject
    public MongoCourseRepository(MongoConnectionFactory mongoFactory, BaseCourseToMongoMapper courseMapper) {
        this.mongoFactory = mongoFactory;
        this.courseMapper = courseMapper;
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

    private Course createCourse(DBObject next) {
        Course course = new Course(next.get("_id").toString(), next.get("name").toString());
        course.setDecription(next.get("description").toString());
        course.setUrl(next.get("url").toString());
        course.setMaxCapacity((Integer) next.get("max"));
        course.setContent(next.get("content").toString());
        course.addRegisteredUsers((Iterable<String>) next.get("users"));

        return course;
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
       return courseMapper.apply(course);
    }

    @Override
    public Course getCourse(final String courseId) {
        return createCourse(getCourseCollection().findOne(getId(courseId)));
    }

    private DBCollection getCourseCollection() {
        return mongoFactory.getDB().getCollection("courses");
    }
}
