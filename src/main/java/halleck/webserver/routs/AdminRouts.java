package halleck.webserver.routs;

import com.google.inject.Inject;
import halleck.api.Course;
import halleck.api.Halleck;
import halleck.webserver.ViewRenderer;
import halleck.webserver.mappers.CourseMapper;
import halleck.webserver.mappers.FormVars;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

import static halleck.webserver.MapMaker.map;

public class AdminRouts extends SparkRoutCollector {
    private CourseMapper mapper = new CourseMapper();
    private ViewRenderer view;
    private Halleck halleck;


    @Inject
    public AdminRouts(Halleck halleck, ViewRenderer view) {
        this.halleck = halleck;
        this.view = view;
    }

    public void init(){
        get(new Route("/admin/courses") {
            @Override
            public Object handle(Request request, Response response) {
                response.type("application/json");
                Iterable<Course> allCourses = halleck.getAllCourses();
                return view.renderJson(allCourses, response);
            }
        });

        get(new Route("/admin/course") {
            @Override
            public Object handle(Request request, Response response) {
                return view.render("editcourse.mustache", request);
            }
        });

        get(new Route("/admin/course/:id") {
            @Override
            public Object handle(Request request, Response response) {
                return view.render("editcourse.mustache", getCourseMap(request), request);
            }
        });

        get(new Route("/admin/course/:id/registrations") {
            @Override
            public Object handle(Request request, Response response) {
                return view.render("registrations.mustache", getCourseMap(request), request);
            }
        });


        post(new Route("/admin/course") {
            @Override
            public Object handle(Request request, Response response) {

                Course apply = mapper.apply(new FormVars(request));
                halleck.createCourse(apply);
                response.redirect("/admin/course/" + apply.getId());
                return null;
            }
        });
    }

    private Map getCourseMap(Request request) {
        return map("course", halleck.getCourse(request.params(":id")));
    }
}