package halleck.webserver;

import junit.framework.Assert;
import org.junit.Test;

import static com.google.common.collect.ImmutableMap.of;

public class MustacheRendererTest {
    @Test
    public void testingMustacheRendering() throws Exception {
        MustacheRenderer renderer = new MustacheRenderer();

        String result = renderer.renderTemplate("test.mustache", of("feature", of("DOJO", true)));

        Assert.assertTrue(result.contains("Dojo is on"));
    }
}