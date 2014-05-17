package halleck.webserver;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static halleck.webserver.SecurityFilter.SITE_NAME;
import static halleck.webserver.SecurityFilter.USER_KEY;

public abstract class FullPage extends TemplateViewRoute {

    private static final String BODY = "BODY";
    private final MustacheRenderer renderer = new MustacheRenderer();

    protected FullPage(String path) {
        super(path);
    }

    protected FullPage(String path, String acceptType) {
        super(path, acceptType);
    }

    @Override
    public String render(ModelAndView modelAndView) {
        return renderer.renderTemplate(modelAndView.getViewName(), modelAndView.getModel());
    }

    @Override
    public final ModelAndView handle(Request request, Response response) {
        String innercontent = getOriginalContent(request, response);

        Body body = new Body(innercontent, (User) request.attribute(USER_KEY), (String) request.attribute(SITE_NAME));
        return new ModelAndView(body, "shell.mustache");
    }

    private String getOriginalContent(Request request, Response response) {
        ModelMapView original = action(request, response);
        if(original != null){
            original.getModel().put("user", request.attribute(USER_KEY));
            return renderer.renderTemplate(original.getViewName(), original.getModel());
        }
        return null;
    }

    public abstract ModelMapView action(Request request, Response response);

    private class Body {
        public String body;
        public String title;
        public User user;


        public Body(String body, User request, String title) {
            this.body = body;
            this.title = title;
            this.user = request;
        }
    }

}
