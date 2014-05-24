package halleck.webserver.renderers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StandardLinkRendererTest {

    private StandardLinkRenderer renderer;

    @Before
    public void setUp() throws Exception {
        renderer = new StandardLinkRenderer();
    }

    @Test
    public void standardLinkWillOnlyRenderLinks() throws Exception {
        assertFalse(renderer.canRender(null));
        assertFalse(renderer.canRender("foo"));
        assertTrue(renderer.canRender("http://whatever.mp4"));
    }
}