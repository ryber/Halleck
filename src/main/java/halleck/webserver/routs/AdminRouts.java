package halleck.webserver.routs;

import com.google.gson.Gson;
import com.google.inject.Inject;
import halleck.api.Course;
import halleck.api.Halleck;
import halleck.webserver.FullPage;
import halleck.webserver.mappers.CourseMapper;
import halleck.webserver.mappers.FormVars;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.Optional;

import static halleck.webserver.MapMaker.map;
import static java.util.stream.Collectors.toList;

public class AdminRouts extends SparkRoutCollector {
    private CourseMapper mapper = new CourseMapper();
    private Halleck halleck;


    @Inject
    public AdminRouts(Halleck halleck) {
        this.halleck = halleck;
    }

    public String renderJson(Object datamap, Response response) {
        response.type("application/json");
        return new Gson().toJson(datamap);
    }

    public void init(){
        get(new Route("/admin/courses") {
            @Override
            public Object handle(Request request, Response response) {
                response.type("application/json");
                List<Course> allCourses = halleck.getAllCourses().collect(toList());
                return renderJson(allCourses, response);
            }
        });

        get(new FullPage("/admin/course") {
            @Override
            public ModelAndView action(Request request, Response response) {
                return new ModelAndView(null, "editcourse.mustache");
            }
        });

        get(new FullPage("/admin/course/:id") {
            @Override
            public ModelAndView action(Request request, Response response) {
                return handleCourseDetailsForPane(request, "editcourse.mustache");
            }
        });

        get(new FullPage("/admin/course/:id/registrations") {
            @Override
            public ModelAndView action(Request request, Response response) {
                return handleCourseDetailsForPane(request, "registrations.mustache");
            }
        });


        post(new FullPage("/admin/course") {
            @Override
            public ModelAndView action(Request request, Response response) {

                Course apply = mapper.apply(new FormVars(request));
                halleck.createCourse(apply);
                response.redirect("/admin/course/" + apply.getId());
                return null;
            }
        });
    }

    private ModelAndView handleCourseDetailsForPane(Request request, String template) {
        Optional<Course> course = halleck.getCourse(request.params(":id"));
        if(course.isPresent()){
            return new ModelAndView(map("course", course.get()), template);
        }

        return new ModelAndView(null, "unknowncourse.mustache");
    }

}
