package webserver;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.IOException;
import java.io.StringWriter;

public class ViewRenderer {


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

        public Body(String body) {
            this.body = body;
        }
    }
}
