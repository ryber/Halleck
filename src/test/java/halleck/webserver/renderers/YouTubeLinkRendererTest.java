package halleck.webserver.renderers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class YouTubeLinkRendererTest {

    private YouTubeLinkRenderer renderer;

    @Before
    public void setUp() throws Exception {
        renderer = new YouTubeLinkRenderer();
    }

    @Test
    public void willRenderLinksFromYouTube() throws Exception {
        assertTrue(renderer.canRender("https://www.youtube.com/watch?v=qLZZ6JD0g9Y"));
    }

    @Test
    public void willNotRenderLinksNotFromYouTube() throws Exception {
        assertFalse(renderer.canRender("http://en.wikipedia.org/wiki/YouTube"));
    }

    @Test
    public void willReformatLinkIntoEmbeddedLink() throws Exception {
        String browserLink = "https://www.youtube.com/watch?v=qLZZ6JD0g9Y";
        String expectedEmbedded = "//www.youtube.com/embed/qLZZ6JD0g9Y";

        assertEquals(expectedEmbedded, renderer.formatLink(browserLink));
    }

    @Test
    public void alsoWorksWithLinksThatAreAlreadyEmbedded() throws Exception {
        String suppliedEmbedded = "http://www.youtube.com/embed/qLZZ6JD0g9Y";
        String expectedEmbedded = "//www.youtube.com/embed/qLZZ6JD0g9Y";

        assertEquals(expectedEmbedded, renderer.formatLink(suppliedEmbedded));
    }
}