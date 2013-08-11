package halleck.webserver;

import com.google.inject.Inject;
import halleck.api.Settings;
import halleck.webserver.routs.SparkRoutCollector;
import spark.Route;
import spark.servlet.SparkApplication;

import java.util.Set;

import static com.google.common.base.Strings.isNullOrEmpty;
import static spark.Spark.*;

public class HttpRouts implements SparkApplication {
    private Settings settings;
    private SecurityFilter filter;
    private CourseLoader courseLoader;
    private Set<SparkRoutCollector> routes;


    @Inject
    public HttpRouts(Settings settings,
                     SecurityFilter filter,
                     CourseLoader courseLoader,
                     Set<SparkRoutCollector> routes) {

        this.settings = settings;
        this.filter = filter;
        this.courseLoader = courseLoader;
        this.routes = routes;
    }

    @Override
    public void init() {
        loadDefaultCourses();
        configureRouts();
    }

    private void loadDefaultCourses() {
       courseLoader.load();
    }

    private void configureRouts() {
        setPort(settings.getAppPort());
        staticFileLocation("/assets");
        setExternalMedia();
        before(filter);

        for (SparkRoutCollector c : routes){
            c.init();
            for (Route g : c.getGets()){
                get(g);
            }
            for (Route po : c.getPosts()){
                post(po);
            }
            for (Route pu : c.getPosts()){
                put(pu);
            }
        }
    }



    private void setExternalMedia() {
        if(!isNullOrEmpty(settings.getExternalMediaLocation())) {
            externalStaticFileLocation(settings.getExternalMediaLocation());
        }
    }


}
