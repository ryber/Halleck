package halleck.webserver.routs;

import com.google.inject.Inject;
import halleck.api.Course;
import halleck.api.Halleck;
import halleck.api.Registration;
import halleck.lms.AppContext;
import halleck.webserver.ViewRenderer;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;
import java.util.NoSuchElementException;

import static halleck.webserver.MapMaker.map;


public class LearningRouts extends SparkRoutCollector {
    private ViewRenderer view;
    private final AppContext context;
    private Halleck halleck;


    @Inject
    public LearningRouts(Halleck halleck,
                         ViewRenderer view,
                         AppContext context) {
        this.halleck = halleck;
        this.view = view;
        this.context = context;
    }

    public void init() {
        get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                return renderCourseList(halleck.getAllCourses(), request);
            }
        });

        get(new Route("/my-courses") {
            @Override
            public Object handle(Request request, Response response) {
                return renderCourseList(halleck.getUsersCourses(getUser()), request);
            }
        });


        post(new Route("/registrations/course/:id") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    String courseID = request.params(":id");
                    halleck.register(courseID, getUser());
                    response.redirect("/registrations/course/" + courseID);

                } catch (Exception e) {
                    System.out.println("e = " + e);
                }

                return null;
            }
        });

        get(new Route("/registrations/course/:id") {
            @Override
            public Object handle(Request request, Response response) {

                try {
                    return view.render("course.mustache", getRegistrationMap(request), request);
                } catch (NoSuchElementException e) {
                    response.status(404);
                    return null;
                }
            }

            private Map getRegistrationMap(Request request) {
                return map("registration", getRegistrationId(request));
            }
        });
    }

    private String getUser() {
        return context.currentUser();
    }

    private String renderCourseList(Iterable<Course> courses, Request request) {
        return view.render("welcome.mustache", map("courses", courses), request);
    }

    private Registration getRegistrationId(Request request) {
        return halleck.getRegistration(request.params(":id"), getUser());
    }

}
