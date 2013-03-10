package lms;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import halleck.Course;
import halleck.Halleck;
import halleck.Registration;
import lms.learningobjects.UserRegistration;

import static com.google.common.collect.Iterables.find;

public class Gurney implements Halleck {

    private CourseRepository courseRepo;

    @Inject
    public Gurney(CourseRepository courseRepo){
        this.courseRepo = courseRepo;
    }

    @Override
    public Iterable<Course> getAllCourses() {
        return courseRepo.getAllCourses();
    }

    @Override
    public Course getCourse(final String id) {
        return find(getAllCourses(), new Predicate<Course>() {
            @Override
            public boolean apply(Course course) {
                return id.equalsIgnoreCase(course.getId());
            }
        });
    }

    @Override
    public Registration getRegistration(String courseID, String userID) {
        Course c = getCourse(courseID);
        return new UserRegistration(c, userID, courseRepo.hasRegistration(courseID, userID));
    }

    @Override
    public void register(String courseId, String user) {
        courseRepo.putRegistration(courseId, user);
    }

}
