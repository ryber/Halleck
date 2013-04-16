package webserver;

import com.google.inject.Inject;
import halleck.Course;
import halleck.Halleck;
import halleck.Registration;
import halleck.Settings;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;
import webserver.mappers.CourseMapper;
import webserver.mappers.FormVars;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

import static spark.Spark.*;
import static webserver.MapMaker.map;
import static webserver.RequestCookies.HALLECK_NAME;

public class HttpRouts implements SparkApplication {
    private CourseMapper mapper = new CourseMapper();
    private ViewRenderer view;
    private Settings settings;
    private SecurityFilter filter;
    private Halleck halleck;


    @Inject
    public HttpRouts(Halleck halleck, ViewRenderer view, Settings settings, SecurityFilter filter) {
        this.halleck = halleck;
        this.view = view;
        this.settings = settings;
        this.filter = filter;
    }

    @Override
    public void init() {

        setPort(settings.getAppPort());
        staticFileRoute("/assets");

        before(filter);

        get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                return view.render("welcome.mustache", map("courses", halleck.getAllCourses()));
            }
        });


        post(new Route("/registrations/course/:id") {
            @Override
            public Object handle(Request request, Response response) {
                try{
                String courseID = request.params(":id");
                halleck.register(courseID, RequestCookies.getUser(request));
                response.redirect("/registrations/course/" + courseID);

                }catch (Exception e){
                    System.out.println("e = " + e);
                }

                return null;
            }
        });

        get(new Route("/registrations/course/:id") {
            @Override
            public Object handle(Request request, Response response) {

                try {
                    return view.render("course.mustache", map(
                            "registration", getRegistrationId(request)
                    ));
                } catch (NoSuchElementException e) {
                    response.status(404);
                    return null;
                }
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
                return view.render("redirect.mustache", map("url", "/"));
            }
        });

        get(new Route("/logout") {
            @Override
            public Object handle(Request request, Response response) {
                new ResponseCookies(response).removeCookie(HALLECK_NAME);
                response.redirect("/login");
                return null;
            }
        });

        get(new Route("/admin/course") {
            @Override
            public Object handle(Request request, Response response) {
                return view.render("editcourse.mustache");
            }
        });

        get(new Route("/admin/course/:id") {
            @Override
            public Object handle(Request request, Response response) {
                return view.render("editcourse.mustache", map("course", halleck.getCourse(request.params(":id"))));
            }
        });

        post(new Route("/admin/course") {
            @Override
            public Object handle(Request request, Response response) {

                Course apply = mapper.apply(getForm(request));
                halleck.createCourse(apply);
                response.redirect("/admin/course/" + apply.getId());
                return null;
            }
        });

    }

    private Registration getRegistrationId(Request request) {
        return halleck.getRegistration(request.params(":id"), RequestCookies.getUser(request));
    }

    private FormVars getForm(Request request) {
        FormVars vars = new FormVars();
        HttpServletRequest raw = request.raw();

        for (Object key : raw.getParameterMap().keySet()) {
            vars.put(key.toString(), raw.getParameter(key.toString()));
        }
        return vars;
    }

}
