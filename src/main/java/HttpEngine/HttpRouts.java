package HttpEngine;

import LMS.Gurney;
import LMS.Halleck;
import LMS.learningobjects.Course;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.get;

public class HttpRouts {
    private ViewRenderer view = new ViewRenderer();
    private Halleck halleck = new Gurney();

    public void registerRouts() {

        get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                Iterable<Course> courses = halleck.getAllCourses();
                return view.render("welcome.stash", courses);
            }
        });

    }

}
