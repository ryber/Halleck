package halleck.webserver.routs;

import com.google.inject.Inject;
import halleck.api.Course;
import halleck.api.Halleck;
import halleck.webserver.FullPage;
import halleck.webserver.ModelMapView;
import halleck.webserver.mappers.CourseMapper;
import halleck.webserver.mappers.FormVars;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static halleck.webserver.MapMaker.map;

public class AdminRouts extends SparkRoutCollector {
    private CourseMapper mapper = new CourseMapper();
    private Halleck halleck;


    @Inject
    public AdminRouts(Halleck halleck) {
        this.halleck = halleck;
    }

    public void init(){
        get(new FullPage("/admin/course") {
            @Override
            public ModelMapView action(Request request, Response response) {
                return new ModelMapView(null, "editcourse.mustache");
            }
        });

        get(new FullPage("/admin/course/:id") {
            @Override
            public ModelMapView action(Request request, Response response) {
                return handleCourseDetailsForPane(request, "editcourse.mustache");
            }
        });

        get(new FullPage("/admin/course/:id/registrations") {
            @Override
            public ModelMapView action(Request request, Response response) {
                return handleCourseDetailsForPane(request, "registrations.mustache");
            }
        });


        post(new FullPage("/admin/course") {
            @Override
            public ModelMapView action(Request request, Response response) {

                Course apply = mapper.apply(new FormVars(request));
                halleck.createCourse(apply);
                response.redirect("/admin/course/" + apply.getId());
                return null;
            }
        });
    }

    private ModelMapView handleCourseDetailsForPane(Request request, String template) {
        Optional<Course> course = halleck.getCourse(request.params(":id"));
        if(course.isPresent()){
            return new ModelMapView(map("course", course.get()), template);
        }

        return new ModelMapView(null, "unknowncourse.mustache");
    }

}
