package halleck.webserver;

import halleck.lms.Course;
import halleck.webserver.renderers.RenderingDispatcher;
import org.junit.Test;
import spark.ModelAndView;

import java.util.Optional;

import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;

public class RenderingDispatcherTest {


    @Test
    public void willUseFirstPositiveCustomRenderer() throws Exception {
        RenderingDispatcher renderer = new RenderingDispatcher(
                new MockStacheRenderer("my result"),
                c -> of(new ModelAndView(c.getId(), "a1")),
                c -> of(new ModelAndView(c.getId(), "b2")));

        String result = renderer.render(new Course("SpiceHarvesterModel"));

        assertEquals("my result\n\nmy result", result);
    }

    private static class MockStacheRenderer extends MustacheRenderer {
        private String result;

        public MockStacheRenderer(String result) {
            this.result = result;
        }

        @Override
        public String renderTemplate(String template, Object data) {
            return result;
        }
    }

}