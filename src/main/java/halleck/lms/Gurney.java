package halleck.lms;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.inject.Inject;
import halleck.api.Course;
import halleck.api.Halleck;
import halleck.api.Registration;
import halleck.api.OnlineCourse;

import java.util.Set;

import static com.google.common.collect.Iterables.find;
import static com.google.common.collect.Iterables.transform;

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
        return new UserRegistration(c, userID);
    }

    @Override
    public void register(String courseId, String user) {
        Course c = courseRepo.getCourse(courseId);
        c.addRegisteredUser(user);
        courseRepo.putCourse(c);
    }

    @Override
    public Set<Registration> getRegistrations(final String courseID) {
        final Course course = getCourse(courseID);

        return FluentIterable.from(transform(getCourse(courseID).getRegisteredUsers(),
            new Function<String, Registration>() {
            @Override
            public Registration apply(String s) {

                return new UserRegistration(course, s);
            }
        }
        )).toSet();
    }

    @Override
    public void createCourse(Course course) {
        courseRepo.putCourse(new OnlineCourse(course));
    }

}
