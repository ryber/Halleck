package halleck.webserver.renderers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmbeddedVideoRendererTest {

    private EmbeddedVideoRenderer renderer;

    @Before
    public void setUp() throws Exception {
        renderer = new EmbeddedVideoRenderer();
    }

    @Test
    public void testsTrueForMp4s() throws Exception {
        assertTrue(renderer.canRender("http://foo/dune.mp4"));
    }

    @Test
    public void testsTrueForMp4sIsNotCaseSensative() throws Exception {
        assertTrue(renderer.canRender("http://foo/dune.MP4"));
    }

    @Test
    public void testsFalseForOthers() throws Exception {
        assertFalse(renderer.canRender("http://foo/dune.html"));
    }
}