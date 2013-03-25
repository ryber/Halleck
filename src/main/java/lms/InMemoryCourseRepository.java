package lms;

import com.google.common.collect.Maps;
import halleck.Course;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

public class InMemoryCourseRepository implements CourseRepository {

    private static Map<String, Course> repo = Maps.newHashMap();

    public List<Course> getAllCourses() {
        return newArrayList(repo.values());
    }

    public static void addCourse(List<Course> allCourses) {
        for (Course c : allCourses){
            addCourse(c);
        }
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
