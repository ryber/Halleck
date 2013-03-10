package lms;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import halleck.Course;

import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class CourseRepository {

    private static List<Course> repo = newArrayList();
    private static Multimap<String, String> registrations = HashMultimap.create();

    List<Course> getAllCourses() {
        return repo;
    }

    public boolean hasRegistration(String courseID, String userID) {
        Collection<String> courses = registrations.get(userID);
        return courses != null && courses.contains(courseID);
    }

    public static void createCourses(List<Course> allCourses) {
        repo.addAll(allCourses);
    }

    public void putRegistration(String courseId, String user) {
        registrations.put(user, courseId);
    }
}
