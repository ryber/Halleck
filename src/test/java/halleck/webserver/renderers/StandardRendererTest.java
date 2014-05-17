package halleck.webserver.renderers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StandardRendererTest {

    private StandardRenderer renderer;

    @Before
    public void setUp() throws Exception {
        renderer = new StandardRenderer();
    }

    @Test
    public void standardLinkRendererCanRenderAnything() throws Exception {
        assertTrue(renderer.canRender(null));
        assertTrue(renderer.canRender("foo"));
        assertTrue(renderer.canRender("http://whatever.mp4"));
    }
}