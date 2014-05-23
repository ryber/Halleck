package halleck.webserver;

import halleck.lms.Course;
import halleck.webserver.renderers.RenderingDispatcher;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class RenderingDispatcherTest {

    private MockStacheRenderer mockStacheRenderer;
    private Course course;
    @Before
    public void setUp() throws Exception {
        mockStacheRenderer = new MockStacheRenderer();
        course = new Course("foo");
    }

    @Test
    public void willUseFirstPositiveCustomRenderer() throws Exception {
        RenderingDispatcher renderer = new RenderingDispatcher(newArrayList(new StubRenderer("custom", true)),
                                                                  new StubRenderer("standard", true),
                                                                  mockStacheRenderer);
        course.setUrl("http://doesntmatter");
        renderer.render(course);

        assertEquals("custom", mockStacheRenderer.template);
        assertEquals("http://doesntmatter", mockStacheRenderer.data.get(Renderer.URL));
    }

    @Test
    public void willUseDefaultIfNoCustomFound() throws Exception {
        RenderingDispatcher renderer = new RenderingDispatcher(newArrayList(new StubRenderer("custom", false)),
                new StubRenderer("standard", false),
                mockStacheRenderer);

        course.setUrl("http://doesntmatter");
        renderer.render(course);

        assertEquals("standard", mockStacheRenderer.template);
        assertEquals("http://doesntmatter", mockStacheRenderer.data.get(Renderer.URL));
    }

    private static class StubRenderer extends Renderer {
        private String stache;
        private boolean canRender;

        StubRenderer(String stache, boolean canRender){
            this.stache = stache;
            this.canRender = canRender;
        }
        @Override
        public String mustacheTemplate() {
            return stache;
        }

        @Override
        public boolean canRender(String url) {
            return canRender;
        }
    }

    private static class MockStacheRenderer extends MustacheRenderer {
        String template;
        Map<String, Object> data;

        @Override
        public String renderTemplate(String template, Object data) {
            this.template = template;
            this.data = (Map)data;
            return "";
        }
    }

}