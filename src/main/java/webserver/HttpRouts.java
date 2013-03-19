package webserver;

import com.google.inject.Inject;
import halleck.Halleck;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;

import static spark.Spark.*;
import static webserver.MapMaker.map;
import static webserver.RequestCookies.requestCookies;

public class HttpRouts implements SparkApplication {
    public static final String HALLECK_NAME = "halleckName";
    private ViewRenderer view = new ViewRenderer();
    private Halleck halleck;

    @Inject
    public HttpRouts(Halleck halleck){
        this.halleck = halleck;
    }

    @Override
    public void init() {

        before(new SecurityFilter());

        get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                return view.render("welcome.mustache", map("courses", halleck.getAllCourses()));
            }
        });

        post(new Route("/registrations/course/:id") {
            @Override
            public Object handle(Request request, Response response) {
                String courseID = request.params(":id");
                halleck.register(courseID, getUser(request));
                response.redirect("/registrations/course/" + courseID);
                return null;
            }
        });

        get(new Route("/registrations/course/:id") {
            @Override
            public Object handle(Request request, Response response) {
                return view.render("course.mustache", map(
                        "registration", halleck.getRegistration(request.params(":id"), getUser(request))
                )
                );
            }
        });

        get(new Route("/login") {
            @Override
            public Object handle(Request request, Response response) {
                return view.render("login.mustache", null);
            }
        });

        post(new Route("/login") {
            @Override
            public Object handle(Request request, Response response) {
                String username = request.queryParams("username");
                new ResponseCookies(response).cookie(HALLECK_NAME, username);
                return view.render("redirect.mustache", map("url","/"));
            }
        });
    }

    private String getUser(Request request) {
        return requestCookies(request).cookie(HALLECK_NAME);
    }

}
