package halleck.lms;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import halleck.api.Course;
import halleck.api.Halleck;
import halleck.api.Registration;

import javax.annotation.Nullable;
import java.util.Set;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Iterables.find;
import static com.google.common.collect.Iterables.transform;

public class Gurney implements Halleck {

    private CourseRepository courseRepo;

    @Inject
    public Gurney(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    @Override
    public Iterable<Course> getAllCourses() {
        return courseRepo.getAllCourses();
    }

    @Override
    public Iterable<Course> getUsersCourses(final String userID) {
        return Iterables.filter(getAllCourses(), new Predicate<Course>() {
            @Override
            public boolean apply(@Nullable Course course) {
                return course.getRegisteredUsers().contains(userID);
            }
        });
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

        return from(transform(getCourse(courseID).getRegisteredUsers(),
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
    public Iterable<Course> search(final String q) {
        return Iterables.filter(getAllCourses(), new Predicate<Course>() {
            @Override
            public boolean apply(@Nullable Course course) {
                return contains(course.getName()) || contains(course.getDescription());
            }

            private boolean contains(String item) {
                return item.toLowerCase().contains(q.toLowerCase());
            }
        });
    }

    @Override
    public void createCourses(Iterable<Course> courseArray) {
        for (Course c : courseArray) {
            createCourse(c);
        }
    }


}
