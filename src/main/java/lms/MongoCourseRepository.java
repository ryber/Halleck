package lms;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.mongodb.*;
import halleck.Course;
import halleck.OnlineCourse;

import javax.annotation.Nullable;
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
                result.add(createCourse(next));
            }
        } finally {
            allCourses.close();
        }
        return result;
    }

    private OnlineCourse createCourse(DBObject next) {
        OnlineCourse onlineCourse = new OnlineCourse(
                next.get("_id").toString(),
                next.get("name").toString(),
                next.get("description").toString(),
                next.get("url").toString(),
                (Integer) next.get("max"));
        onlineCourse.addRegisteredUsers((Iterable<String>)next.get("users"));
        return onlineCourse;
    }


    @Override
    public void putCourse(Course course) {

        if(exists(course.getId())){
            getCourses().update(getId(course), map(course));
        }else {
            getCourses().insert(map(course));
        }
    }

    private DBObject getId(Course course) {
        return new BasicDBObject("_id", course.getId());
    }

    private boolean exists(final String courseId) {
        return Iterables.any(getAllCourses(), new Predicate<Course>() {
            @Override
            public boolean apply(@Nullable Course input) {
                return input.getId().equalsIgnoreCase(courseId);
            }
        });
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
        return Iterables.find(getAllCourses(), new Predicate<Course>() {
            @Override
            public boolean apply(@Nullable Course input) {
                return input.getId().equalsIgnoreCase(courseId);
            }
        });
    }



    private DBCollection getCourses() {
        return mongoClient.getDB("halleck").getCollection("courses");
    }
}
