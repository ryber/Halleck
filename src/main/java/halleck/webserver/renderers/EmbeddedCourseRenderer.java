package halleck.webserver.renderers;

import halleck.lms.Course;
import spark.ModelAndView;

import java.util.Optional;
import java.util.function.Function;

import static java.util.Optional.of;

class EmbeddedCourseRenderer implements Function<Course, Optional<ModelAndView>> {
    @Override
    public Optional<ModelAndView> apply(Course course) {
        return of(new ModelAndView(course.getContent(), "embeddedcourse.mustache"));
    }
}
