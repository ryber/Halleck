package halleck.webserver;

import halleck.webserver.renderers.CourseRenderer;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class CourseRendererTest {

    private MockStacheRenderer mockStacheRenderer;

    @Before
    public void setUp() throws Exception {
        mockStacheRenderer = new MockStacheRenderer();
    }

    @Test
    public void willUseFirstPositiveCustomRenderer() throws Exception {
        CourseRenderer renderer = new CourseRenderer(newArrayList(new StubRenderer("custom", true)),
                                                                  new StubRenderer("standard", true),
                                                                  mockStacheRenderer);

        renderer.render("http://doesntmatter");

        assertEquals("custom", mockStacheRenderer.template);
        assertEquals("http://doesntmatter", mockStacheRenderer.data.get(Renderer.URL));
    }

    @Test
    public void willUseDefaultIfNoCustomFound() throws Exception {
        CourseRenderer renderer = new CourseRenderer(newArrayList(new StubRenderer("custom", false)),
                new StubRenderer("standard", false),
                mockStacheRenderer);

        renderer.render("http://doesntmatter");

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