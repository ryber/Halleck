package halleck.webserver;

import com.google.inject.Inject;
import halleck.api.Course;
import halleck.api.Halleck;
import halleck.api.Registration;
import halleck.api.Settings;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.servlet.SparkApplication;
import halleck.webserver.mappers.CourseMapper;
import halleck.webserver.mappers.FormVars;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

import static halleck.webserver.RequestCookies.getUser;
import static spark.Spark.*;

public class HttpRouts implements SparkApplication {
    private CourseMapper mapper = new CourseMapper();
    private ViewRenderer view;
    private Settings settings;
    private SecurityFilter filter;
    private Authenticator auth;
    private Halleck halleck;


    @Inject
    public HttpRouts(Halleck halleck,
                     ViewRenderer view,
                     Settings settings,
                     SecurityFilter filter,
                     Authenticator auth) {

        this.halleck = halleck;
        this.view = view;
        this.settings = settings;
        this.filter = filter;
        this.auth = auth;
    }

    @Override
    public void init() {

        setPort(settings.getAppPort());
        staticFileLocation("/assets");

        before(filter);

        get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                return renderCourseList(halleck.getAllCourses());
            }
        });

        get(new Route("/my-courses") {
            @Override
            public Object handle(Request request, Response response) {
                return renderCourseList(halleck.getUsersCourses(getUser(request)));
            }
        });

        get(new Route("/query/:q") {
            @Override
            public Object handle(Request request, Response response) {
                String q = request.params(":q");
                return renderCourseList(halleck.search(q));
            }
        });

        post(new Route("/registrations/course/:id") {
            @Override
            public Object handle(Request request, Response response) {
                try{
                String courseID = request.params(":id");
                halleck.register(courseID, getUser(request));
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
                    return view.render("course.mustache", MapMaker.map(
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
                String password = request.queryParams("password");

                if(auth.authenticate(username, password)){
                    new ResponseCookies(response).cookie(RequestCookies.HALLECK_NAME, username);
                    return view.render("redirect.mustache", MapMaker.map("url", "/"));
                }
                return view.render("login.mustache", MapMaker.map("wrong",true));
            }
        });

        get(new Route("/logout") {
            @Override
            public Object handle(Request request, Response response) {
                new ResponseCookies(response).removeCookie(RequestCookies.HALLECK_NAME);
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
                return view.render("editcourse.mustache", MapMaker.map("course", halleck.getCourse(request.params(":id"))));
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

    private String renderCourseList(Iterable<Course> courses) {
        return view.render("welcome.mustache", MapMaker.map("courses", courses));
    }

    private Registration getRegistrationId(Request request) {
        return halleck.getRegistration(request.params(":id"), getUser(request));
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
