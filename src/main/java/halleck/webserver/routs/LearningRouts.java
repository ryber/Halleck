package halleck.webserver.routs;

import com.google.inject.Inject;
import halleck.api.Course;
import halleck.api.Halleck;
import halleck.api.Registration;
import halleck.lms.AppContext;
import halleck.webserver.ModelMapView;
import spark.Request;
import spark.Response;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static halleck.webserver.MapMaker.map;
import static java.util.stream.Collectors.toList;


public class LearningRouts  {
    private final AppContext context;
    private final Halleck halleck;

    @Inject
    public LearningRouts(Halleck halleck,
                         AppContext context) {
        this.halleck = halleck;
        this.context = context;
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
        return map("registration", getRegistrationId(request));
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
