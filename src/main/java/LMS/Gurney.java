package lms;

import com.google.inject.Inject;
import halleck.Course;
import halleck.Halleck;

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

}
