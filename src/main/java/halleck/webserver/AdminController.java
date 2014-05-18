package halleck.webserver;

import com.google.inject.Inject;
import halleck.api.Course;
import halleck.api.Halleck;
import halleck.webserver.ModelMapView;
import halleck.webserver.mappers.CourseMapper;
import halleck.webserver.mappers.FormVars;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static halleck.webserver.MapMaker.map;

public class AdminController {
    private CourseMapper mapper = new CourseMapper();
    private Halleck halleck;


    @Inject
    public AdminController(Halleck halleck) {
        this.halleck = halleck;
    }

    public ModelMapView getViewCourse() {
        return new ModelMapView(null, "editcourse.mustache");
    }

    public ModelMapView createCourse(Request request, Response response) {
        Course apply = mapper.apply(new FormVars(request));
        halleck.createCourse(apply);
        response.redirect("/admin/course/" + apply.getId());
        return null;
    }

    public ModelMapView getEditCourseView(Request request) {
        return renderCourseAdmin(request, "editcourse.mustache");
    }

    public ModelMapView getRegistrationAdminView(Request request) {
        return renderCourseAdmin(request, "registrations.mustache");
    }

    private ModelMapView renderCourseAdmin(Request request, String view) {
        Optional<Course> course = halleck.getCourse(request.params(":id"));
        if(course.isPresent()){
            return new ModelMapView(map("course", course.get()), view);
        }

        return new ModelMapView(null, "unknowncourse.mustache");
    }

}
