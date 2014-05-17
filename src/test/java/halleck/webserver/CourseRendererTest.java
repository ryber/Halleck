package halleck.webserver;

import halleck.webserver.renderers.CourseRenderer;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;

import static org.junit.Assert.*;

public class CourseRendererTest {

    public static final String STANDARD_LINK = "http://foo.com";
    public static final String MP4_LINK = "http://foo/video.mp4";
    public static final String YOUTUBE_LINK = "https://www.youtube.com/watch?v=4r7wHMg5Yjg";

    private CourseRenderer renderer;

    @Before
    public void setUp() throws Exception {
        renderer = new CourseRenderer();
    }

    @Test
    public void standardLinksJustGetAnHref() throws Exception {
        assertTrue(renderer.render(STANDARD_LINK).contains("<a href"));
    }


}