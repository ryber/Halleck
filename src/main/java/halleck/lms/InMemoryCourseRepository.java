package halleck.lms;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;

public class InMemoryCourseRepository implements CourseRepository {

    private static ConcurrentMap<String, Course> repo = Maps.newConcurrentMap();

    public Stream<Course> getAllCourses() {
        return repo.values().stream();
    }

    public static void addCourse(Course course) {
        repo.put(course.getId().toLowerCase(), course);
    }

    @Override
    public void putCourse(Course course) {
        addCourse(course);
    }

    @Override
    public Course getCourse(String courseId) {
        return repo.get(courseId.toLowerCase());
    }

    public static void reset() {
        repo.clear();
    }
}
