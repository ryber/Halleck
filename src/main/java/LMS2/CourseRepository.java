package lms;

import com.google.common.collect.Maps;
import halleck.Course;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

public class CourseRepository {

    private static Map<String, Course> repo = Maps.newHashMap();


    List<Course> getAllCourses() {
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

    public void putRegistration(String courseId, String user) {
        repo.get(courseId.toLowerCase()).addRegisteredUser(user);
    }

    public static void reset() {
        repo.clear();
    }

    public void putCourse(Course course) {
        addCourse(course);
    }
}
