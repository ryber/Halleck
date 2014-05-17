package halleck.webserver.renderers;

import com.google.common.collect.ImmutableSet;
import halleck.webserver.MustacheRenderer;
import halleck.webserver.Renderer;

public class CourseRenderer {

    private MustacheRenderer mustacheRenderer = new MustacheRenderer();

    private static final Renderer STANDARD_RENDERER = new StandardRenderer();
    private static final ImmutableSet<Renderer> RENDERERS = ImmutableSet.<Renderer>of(new YouTubeRenderer(),
                                                                                      new EmbeddedVideoRenderer());



    public String render(String standardLink) {
        Renderer renderer = getRenderer(standardLink);
        return mustacheRenderer.renderTemplate(renderer.mustacheTemplate(), renderer.formatUrl(standardLink));
    }

    private Renderer getRenderer(String standardLink) {
        return RENDERERS.stream()
                .filter(r -> r.canRender(standardLink))
                .findFirst()
                .orElseGet(() -> STANDARD_RENDERER);
    }




}
