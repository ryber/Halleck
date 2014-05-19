package halleck.webserver;

import com.google.inject.Inject;
import halleck.api.Settings;
import spark.*;
import spark.servlet.SparkApplication;
import spark.template.mustache.MustacheTemplateEngine;

import static spark.Spark.*;

import static com.google.common.base.Strings.isNullOrEmpty;

public class HttpRouts implements SparkApplication {
    private Settings settings;
    private SecurityFilter filter;
    private AdminController adminRouts;
    private AuthController authentication;
    private LearningController learning;


    @Inject
    public HttpRouts(Settings settings,
                     SecurityFilter filter,
                     AdminController adminRouts,
                     AuthController authentication,
                     LearningController learning) {

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

    public void get(final String path, final TemplateViewRoute get){
        Spark.get(path, get, new ShelledMustache());
    }

    public void post(final String path, final TemplateViewRoute post){
        Spark.post(path, post, new ShelledMustache());
    }

}
