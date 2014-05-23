package halleck.webserver.renderers;

import halleck.lms.Course;
import org.junit.Before;
import org.junit.Test;
import spark.ModelAndView;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class ExternalCourseRendererTest {

    private Course course;

    @Before
    public void setUp() throws Exception {
        course = new Course("foo");
    }

    @Test
    public void willUseFirstPositiveCustomRenderer() throws Exception {
        ExternalCourseRenderer renderer = new ExternalCourseRenderer(newArrayList(new StubLinkRenderer("custom", true)), new StubLinkRenderer("standard", true));

        ModelAndView view = renderer.apply(course);

        assertEquals("custom", view.getViewName());
    }

    @Test
    public void willUseDefaultIfNoCustomFound() throws Exception {
        ExternalCourseRenderer renderer = new ExternalCourseRenderer(newArrayList(new StubLinkRenderer("custom", false)), new StubLinkRenderer("standard", true));

        ModelAndView view = renderer.apply(course);

        assertEquals("standard", view.getViewName());
    }

    private static class StubLinkRenderer extends LinkRenderer {
        private String stache;
        private boolean canRender;

        StubLinkRenderer(String stache, boolean canRender){
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

}