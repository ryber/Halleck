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

    protected FullPage(String path) {
        super(path);
    }

    protected FullPage(String path, String acceptType) {
        super(path, acceptType);
    }

    @Override
    public String render(ModelAndView modelAndView) {
        return renderTemplate(modelAndView.getViewName(), modelAndView.getModel());
    }

    @Override
    public final ModelAndView handle(Request request, Response response) {
        ModelAndView original = action(request, response);
        String innercontent = renderTemplate(original.getViewName(), original.getModel());

        Body body = new Body(innercontent, (User) request.attribute(USER_KEY), (String) request.attribute(SITE_NAME));
        return new ModelAndView(body, "shell.mustache");
    }


    public abstract ModelAndView action(Request request, Response response);


    private String renderTemplate(String template, Object data) {
        MustacheFactory mustacheFactory = new DefaultMustacheFactory("templates/");
        Mustache mustache = mustacheFactory.compile(template);

        try {
            StringWriter sw = new StringWriter();
            mustache.execute(sw, data).flush();
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
