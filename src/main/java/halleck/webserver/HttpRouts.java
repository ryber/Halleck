package halleck.webserver;

import com.google.inject.Inject;
import halleck.lms.Settings;
import halleck.lms.AppContext;
import spark.*;
import spark.servlet.SparkApplication;

import static spark.Spark.*;

import static com.google.common.base.Strings.isNullOrEmpty;

public class HttpRouts implements SparkApplication {
    private Settings settings;
    private SecurityFilter filter;
    private AdminController adminRouts;
    private AuthController authentication;
    private LearningController learning;
    private FullPageMustacheRenderer fullPageWithNav;
    private AppContext context;


    @Inject
    public HttpRouts(Settings settings,
                     SecurityFilter filter,
                     AdminController adminRouts,
                     AuthController authentication,
                     LearningController learning,
                     FullPageMustacheRenderer renderer,
                     AppContext context) {

        this.settings = settings;
        this.filter = filter;
        this.adminRouts = adminRouts;
        this.authentication = authentication;
        this.learning = learning;
        this.fullPageWithNav = renderer;
        this.context = context;
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
        setRouts();
        after((q,p) -> context.clear());
    }

    private void setRouts() {
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
        Spark.get(path, get, fullPageWithNav);
    }

    public void post(final String path, final TemplateViewRoute post){
        Spark.post(path, post, fullPageWithNav);
    }

}
