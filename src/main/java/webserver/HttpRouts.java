package webserver;

import com.google.inject.Inject;
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
                return view.render("welcome.mustache", map("courses", halleck.getAllCourses()));
            }
        });

        get(new Route("/course/:id") {
            @Override
            public Object handle(Request request, Response response) {
                try{
                return view.render("course.mustache", map("course", halleck.getCourse(request.params(":id"))));
                }catch (Exception e){
                    System.out.println("e = " + e);
                }
                return null;
            }
        });

    }

}
