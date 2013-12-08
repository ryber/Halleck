package halleck.lms;

import com.google.common.base.Function;
import com.google.inject.Inject;
import halleck.api.Course;
import halleck.api.Halleck;
import halleck.api.Registration;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.transform;

public class Gurney implements Halleck {

    private CourseRepository courseRepo;

    @Inject
    public Gurney(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    @Override
    public Stream<Course> getAllCourses() {
        return courseRepo.getAllCourses();
    }

    @Override
    public Stream<Course> getUsersCourses(final String userID) {

        return getAllCourses().filter(x -> x.getRegisteredUsers().contains(userID));
    }

    @Override
    public Optional<Course> getCourse(final String id) {
        return getAllCourses().filter(x->id.equalsIgnoreCase(x.getId())).findFirst();
    }

    @Override
    public Registration getRegistration(String courseID, String userID) {
        Course c = getCourse(courseID).get();
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
        final Course course = getCourse(courseID).get();

        return from(transform(getCourse(courseID).get().getRegisteredUsers(),
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
        courseRepo.putCourse(course);
    }


    @Override
    public void createCourses(Iterable<Course> courseArray) {
        for (Course c : courseArray) {
            createCourse(c);
        }
    }


}
