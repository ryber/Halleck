package halleck.webserver;

import com.google.inject.Inject;
import halleck.lms.Course;
import halleck.lms.Feature;
import halleck.lms.Halleck;
import halleck.webserver.mappers.CourseMapper;
import halleck.webserver.mappers.FormVars;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static com.google.common.collect.ImmutableMap.of;

public class AdminController {
    private CourseMapper mapper = new CourseMapper();
    private Halleck halleck;


    @Inject
    public AdminController(Halleck halleck) {
        this.halleck = halleck;
    }

    public ModelAndView getViewCourse() {
        return new ModelAndView(of(), "editcourse.mustache");
    }

    public ModelAndView createCourse(Request request, Response response) {
        Course apply = mapper.apply(new FormVars(request));
        halleck.createCourse(apply);
        response.redirect("/admin/course/" + apply.getId());
        return null;
    }

    public ModelAndView getEditCourseView(Request request) {
        return renderCourseAdmin(request, "editcourse.mustache");
    }

    public ModelAndView getRegistrationAdminView(Request request) {
        return renderCourseAdmin(request, "registrations.mustache");
    }

    private ModelAndView renderCourseAdmin(Request request, String view) {
        Optional<Course> course = halleck.getCourse(request.params(":id"));
        if(course.isPresent()){
            return getModelAndView(view, course.get());
        }

        return new ModelAndView(of(), "unknowncourse.mustache");
    }

    ModelAndView getModelAndView(String view, Course course) {
        return new ModelAndView(of("course", course,
                                   "FEATURES", new FeatureMap()), view);
    }
}
