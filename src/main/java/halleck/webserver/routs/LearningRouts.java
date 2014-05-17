package halleck.webserver.routs;

import com.google.inject.Inject;
import halleck.api.Course;
import halleck.api.Halleck;
import halleck.api.Registration;
import halleck.lms.AppContext;
import halleck.webserver.FullPage;
import halleck.webserver.ModelMapView;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static halleck.webserver.MapMaker.map;
import static java.util.stream.Collectors.toList;


public class LearningRouts extends SparkRoutCollector {
    private final AppContext context;
    private final Halleck halleck;

    @Inject
    public LearningRouts(Halleck halleck,
                         AppContext context) {
        this.halleck = halleck;
        this.context = context;
    }

    public void init() {
        get("/", (q,p) -> renderCourseList(halleck.getAllCourses()));
        get("/my-courses", (q,p) -> renderCourseList(halleck.getUsersCourses(getUser())));
        post("/registrations/course/:id", (q,p) -> renderCourseDetails(q, p));
        get("/registrations/course/:id", (q,p) -> renderRegistration(q, p));
    }

    private ModelMapView renderRegistration(Request request, Response response) {
        try {
            return new ModelMapView(getRegistrationMap(request), "course.mustache");
        } catch (NoSuchElementException e) {
            response.status(404);
            return null;
        }
    }

    private Map getRegistrationMap(Request request) {
        return map("registration", getRegistrationId(request));
    }

    private ModelMapView renderCourseDetails(Request request, Response response) {
        try {
            String courseID = request.params(":id");
            halleck.register(courseID, getUser());
            response.redirect("/registrations/course/" + courseID);

        } catch (Exception e) {
            System.out.println("e = " + e);
        }

        return null;
    }

    private String getUser() {
        return context.currentUser();
    }

    private ModelMapView renderCourseList(Stream<Course> courses) {
        return new ModelMapView(map("courses", courses.collect(toList())), "welcome.mustache");
    }

    private Registration getRegistrationId(Request request) {
        return halleck.getRegistration(request.params(":id"), getUser());
    }

}
