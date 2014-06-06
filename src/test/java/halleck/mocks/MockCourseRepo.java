package halleck.mocks;

import com.google.common.collect.Sets;
import halleck.lms.Course;
import halleck.lms.CourseRepository;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Stream.of;

public class MockCourseRepo implements CourseRepository {

    private Set<Course> repo = Sets.newHashSet();

    @Override
    public Stream<Course> getAllCourses() {
        return repo.stream();
    }

    @Override
    public void putCourse(Course course) {
        repo.add(course);
    }

    @Override
    public Course getCourse(String courseId) {
        return getAllCourses().filter(x->x.getId().equals(courseId)).findFirst().get();
    }

    public void putAll(Course... courses) {
        of(courses).forEach(this::putCourse);
    }
}
