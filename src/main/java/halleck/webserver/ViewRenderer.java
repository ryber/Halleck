package halleck.webserver;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.inject.Inject;
import halleck.api.Settings;
import spark.Request;

import java.io.IOException;
import java.io.StringWriter;

import static com.google.common.base.Strings.isNullOrEmpty;

public class ViewRenderer {

    private Settings settings;

    @Inject
    public ViewRenderer(Settings settings){
        this.settings = settings;
    }

    public String render(String templateName, Request request) {
        return render(templateName, null, request);
    }

    public String render(String template, Object data, Request request){
        return renderTemplate("shell.mustache", new Body(renderTemplate(template, data), request));
    }

    private String renderTemplate(String template, Object data) {
        MustacheFactory mustashFactory = new DefaultMustacheFactory("templates/");
        Mustache mustache = mustashFactory.compile(template);

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
        public String user;
        public boolean isAdmin;
        public boolean isLoggedIn;

        public Body(String body, Request request) {
            this.body = body;
            this.title = settings.getSiteName();
            this.user = RequestCookies.getUser(request);
            this.isLoggedIn = !isNullOrEmpty(user);
            this.isAdmin = settings.getAdmins().contains(user);
        }
    }
}
