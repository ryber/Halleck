package halleck.lms;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Gurney implements Halleck {

    private final CourseRepository courseRepo;
    private final AppContext context;

    @Inject
    public Gurney(CourseRepository courseRepo, AppContext context) {
        this.courseRepo = courseRepo;
        this.context = context;
    }

    @Override
    public Stream<Course> getAllCourses() {
        return courseRepo.getAllCourses()
                         .sorted((l,r) -> l.getName().compareTo(r.getName()));
    }

    @Override
    public Stream<Course> getUsersCourses(final String userID) {
        return getAllCourses().filter(x -> x.getRegisteredUsers().contains(userID));
    }

    @Override
    public Optional<Course> getCourse(final String id) {
        return getAllCourses().filter(x -> id.equalsIgnoreCase(x.getId())).findFirst();
    }

    @Override
    public Registration getRegistration(String courseID, String userID) {
        return new UserRegistration(getCourse(courseID).get(), userID);
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

        return getCourse(courseID).get()
                .getRegisteredUsers()
                .stream()
                .map(s -> new UserRegistration(course, s))
                .collect(Collectors.toSet());
    }

    @Override
    public void createCourse(Course course) {
        setOwner(course);
        courseRepo.putCourse(course);
    }

    private void setOwner(Course course) {
        String owner = context.currentUser();
        if(Strings.isNullOrEmpty(course.getOwner())) {
            course.setOwner(owner);
        }
    }


    @Override
    public void createCourses(Iterable<Course> courseArray) {
        courseArray.forEach(this::createCourse);
    }

    @Override
    public Stream<Course> getCourseDojo(String parentCourseId) {
        return getCourse(parentCourseId).orElseThrow(() -> new CourseNotFoundException())
                                        .getChildIds()
                                        .stream()
                                        .map(this::getCourse)
                                        .filter(Optional::isPresent)
                                        .map(Optional::get);

    }


    public static class CourseNotFoundException extends RuntimeException {
    }
}
