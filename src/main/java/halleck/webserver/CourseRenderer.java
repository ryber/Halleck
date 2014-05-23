package halleck.webserver;

import halleck.lms.Course;

public interface CourseRenderer {
    String render(Course standardLink);
}
