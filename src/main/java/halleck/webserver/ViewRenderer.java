package halleck.webserver;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.inject.Inject;
import halleck.api.Settings;
import spark.Request;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;

public class ViewRenderer {

    private Settings settings;

    @Inject
    public ViewRenderer(Settings settings){
        this.settings = settings;
    }

    public String render(String templateName, Request request) {
        return render(templateName, new HashMap(), request);
    }

    public String render(String template, Map data, Request request){
        User u  = new User(request);
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
        public String user;
        public boolean isAdmin;
        public boolean isLoggedIn;

        public User(Request request){
            this.user = RequestCookies.getUser(request);
            this.isLoggedIn = !isNullOrEmpty(user);
            this.isAdmin = settings.getAdmins().contains(user);
        }
    }
}
