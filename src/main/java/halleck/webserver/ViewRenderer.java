package halleck.webserver;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.gson.Gson;
import com.google.inject.Inject;
import halleck.api.Settings;
import halleck.lms.AppContext;
import spark.Response;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;

public class ViewRenderer {

    private final Settings settings;
    private final AppContext context;

    @Inject
    public ViewRenderer(Settings settings, AppContext context){
        this.settings = settings;
        this.context = context;
    }

    public String render(String templateName) {
        return render(templateName, new HashMap());
    }

    public String render(String template, Map data){
        User u  = new User();
        data.put("user", u);
        return renderTemplate("shell.mustache", new Body(renderTemplate(template, data), u));
    }



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

    public String renderJson(Object datamap, Response response) {
        response.type("application/json");
        return new Gson().toJson(datamap);
    }

    private class Body {
        public String body;
        public String title;
        public User user;


        public Body(String body, User request) {
            this.body = body;
            this.title = settings.getSiteName();
            this.user = request;
        }
    }

    private class User {
        public String name;
        public boolean isAdmin;
        public boolean isLoggedIn;

        public User(){
            this.name = context.currentUser();
            this.isLoggedIn = !isNullOrEmpty(name);
            this.isAdmin = settings.getAdmins().contains(name);
        }
    }
}
