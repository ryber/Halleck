package lms;

import com.mongodb.*;
import halleck.Course;
import halleck.OnlineCourse;

import java.net.UnknownHostException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class MongoCourseRepository implements CourseRepository {

    private Mongo mongoClient;


    public MongoCourseRepository() {
        try {
            mongoClient = new Mongo("oracle.axiom.local", 27017);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> result = newArrayList();
        DBCursor allCourses = getCourses().find();

        try {
            while (allCourses.hasNext()) {
                DBObject next = allCourses.next();
                result.add(new OnlineCourse(
                        next.get("id").toString(),
                        next.get("name").toString(),
                        next.get("description").toString(),
                        next.get("url").toString(),
                        null));
            }
        } finally {
            allCourses.close();
        }
        return result;
    }


    @Override
    public void putCourse(Course course) {
        getCourses().insert(map(course));
    }

    private DBObject map(Course course) {
        return new BasicDBObject("id", course.getId())
                .append("name", course.getName())
                .append("description", course.getDescription())
                .append("url", course.getUrl())
                .append("max", course.getMaxEnrollment())
                .append("users", course.getRegisteredUsers());
    }

    @Override
    public Course getCourse(String courseId) {
        //return getCourses().;
        return null;
    }

    private DBCollection getCourses() {
        return mongoClient.getDB("halleck").getCollection("courses");
    }
}
