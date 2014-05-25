package halleck.webserver;

import halleck.lms.Course;
import halleck.lms.Registration;

public interface CourseRenderer {
    String render(Registration registration);
}
