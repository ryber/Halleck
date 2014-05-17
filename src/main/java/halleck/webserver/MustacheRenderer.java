package halleck.webserver;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.IOException;
import java.io.StringWriter;

public class MustacheRenderer {
    public String renderTemplate(String template, Object data) {
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
}
