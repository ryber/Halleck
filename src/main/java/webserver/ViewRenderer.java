package webserver;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.inject.Inject;
import halleck.Settings;

import java.io.IOException;
import java.io.StringWriter;

public class ViewRenderer {

    private String siteName;

    @Inject
    public ViewRenderer(Settings settings){
        this.siteName = settings.getSiteName();
    }

    public String render(String templateName) {
        return render(templateName, null);
    }

    public String render(String template, Object data){
        return renderTemplate("shell.mustache", new Body(renderTemplate(template, data)));
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

        public Body(String body) {
            this.body = body;
            this.title = siteName;
            this.user = "ryber";
        }
    }
}
