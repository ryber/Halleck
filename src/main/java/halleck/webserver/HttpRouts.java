package halleck.webserver;

import com.google.inject.Inject;
import halleck.api.Settings;
import halleck.webserver.routs.AdminRouts;
import halleck.webserver.routs.AuthenticationRouts;
import halleck.webserver.routs.LearningRouts;
import halleck.webserver.routs.RouteFunction;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.servlet.SparkApplication;
import static spark.Spark.*;

import static com.google.common.base.Strings.isNullOrEmpty;

public class HttpRouts implements SparkApplication {
    private Settings settings;
    private SecurityFilter filter;
    private AdminRouts adminRouts;
    private AuthenticationRouts authentication;
    private LearningRouts learning;


    @Inject
    public HttpRouts(Settings settings,
                     SecurityFilter filter,
                     AdminRouts adminRouts,
                     AuthenticationRouts authentication,
                     LearningRouts learning) {

        this.settings = settings;
        this.filter = filter;
        this.adminRouts = adminRouts;
        this.authentication = authentication;
        this.learning = learning;
    }

    @Override
    public void init() {
        configureRouts();
    }



    private void configureRouts() {
        setPort(settings.getAppPort());
        staticFileLocation("/assets");
        setExternalMedia();
        before(filter);


        get( "/login", (q,p) -> authentication.getLoginForm());
        post("/login", (q,p) -> authentication.loginAction(q, p));
        get("/logout", (q,p) -> authentication.logoutAction(p));

        get("/", (q,p) -> learning.getCourseCatalog());
        get("/my-courses", (q,p) -> learning.getMyCourses());
        post("/registrations/course/:id", (q,p) -> learning.registerForCourse(q, p));
        get("/registrations/course/:id", (q,p) -> learning.getCourseDeets(q, p));

        get("/admin/course",     (q,p) -> adminRouts.getViewCourse());
        get("/admin/course/:id", (q,p) -> adminRouts.getEditCourseView(q));
        get("/admin/course/:id/registrations", (q,p) -> adminRouts.getRegistrationAdminView(q));
        post("/admin/course",    (q,p) -> adminRouts.createCourse(q, p));
    }


    private void setExternalMedia() {
        if(!isNullOrEmpty(settings.getExternalMediaLocation())) {
            externalStaticFileLocation(settings.getExternalMediaLocation());
        }
    }

    public void get(final String path, final RouteFunction get){
        Spark.get(new FullPage(path) {
            @Override
            public ModelMapView action(Request request, Response response) {
                return get.handle(request, response);
            }
        });
    }

    public void post(final String path, final RouteFunction post){
        Spark.post(new FullPage(path) {
            @Override
            public ModelMapView action(Request request, Response response) {
                return post.handle(request, response);
            }
        });
    }

}
