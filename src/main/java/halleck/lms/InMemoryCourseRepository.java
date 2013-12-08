package halleck.lms;

import com.google.common.collect.Maps;
import halleck.api.Course;

import java.util.Map;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;

public class InMemoryCourseRepository implements CourseRepository {

    private static Map<String, Course> repo = Maps.newHashMap();

    public Stream<Course> getAllCourses() {
        return newArrayList(repo.values()).stream();
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
