package halleck.webserver;

import halleck.lms.Course;
import halleck.webserver.renderers.LinkRenderer;
import halleck.webserver.renderers.RenderingDispatcher;
import org.junit.Before;
import org.junit.Test;
import spark.ModelAndView;

import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class RenderingDispatcherTest {


    @Test
    public void willUseFirstPositiveCustomRenderer() throws Exception {
        MockStacheRenderer stach = new MockStacheRenderer("my result");
        RenderingDispatcher renderer = new RenderingDispatcher(c -> new ModelAndView(c.getId(), "desert-view.mustache"),stach);

        String result = renderer.render(new Course("SpiceHarvesterModel"));

        assertEquals("my result", result);
        assertEquals("SpiceHarvesterModel", stach.data);
        assertEquals("desert-view.mustache", stach.template);
    }

    private static class MockStacheRenderer extends MustacheRenderer {
        String template;
        Object data;
        private String result;

        public MockStacheRenderer(String result) {

            this.result = result;
        }

        @Override
        public String renderTemplate(String template, Object data) {
            this.template = template;
            this.data = data;
            return result;
        }
    }

}