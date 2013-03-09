package webserver;

import com.google.inject.Inject;
import halleck.Course;
import halleck.Halleck;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.get;
import static webserver.MapMaker.map;

public class HttpRouts {
    private ViewRenderer view = new ViewRenderer();
    private Halleck halleck;

    @Inject
    public HttpRouts(Halleck halleck){
        this.halleck = halleck;
    }

    public void registerRouts() {

        get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                Iterable<Course> courses = halleck.getAllCourses();
                return view.render("welcome.mustache", map("courses", courses));
            }
        });

    }

}
