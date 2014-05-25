package halleck.webserver;

import com.google.inject.Inject;
import halleck.lms.Course;
import halleck.lms.Halleck;
import halleck.lms.Registration;
import halleck.lms.AppContext;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static halleck.webserver.MapMaker.map;
import static java.util.stream.Collectors.toList;


public class LearningController {
    private final AppContext context;
    private CourseRenderer renderer;
    private final Halleck halleck;

    @Inject
    public LearningController(Halleck halleck,
                              AppContext context,
                              CourseRenderer renderer) {
        this.halleck = halleck;
        this.context = context;
        this.renderer = renderer;
    }

    public ModelMapView getMyCourses() {
        return renderCourseList(halleck.getUsersCourses(getUser()));
    }

    public ModelMapView getCourseCatalog() {
        return renderCourseList(halleck.getAllCourses());
    }

    public ModelMapView getCourseDeets(Request request, Response response) {
        try {
            return new ModelMapView(getRegistrationMap(request), "course.mustache");
        } catch (NoSuchElementException e) {
            response.status(404);
            return null;
        }
    }

    private Map getRegistrationMap(Request request) {
        Registration reg = getRegistrationId(request);
        return new HashMap() {{
            put("registration", reg);
            put("courseCountent", renderer.render(reg));
        }};
    }


    public ModelMapView registerForCourse(Request request, Response response) {
        try {
            String courseID = request.params(":id");
            String user = getUser();
            halleck.register(courseID, user);
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
