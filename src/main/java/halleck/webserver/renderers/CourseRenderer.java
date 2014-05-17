package halleck.webserver.renderers;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import halleck.webserver.MustacheRenderer;
import halleck.webserver.Renderer;

public class CourseRenderer {

    private ImmutableSet<Renderer> renderers;
    private Renderer standardRenderer;
    private MustacheRenderer mustacheRenderer;


    public CourseRenderer(){
        this(Lists.newArrayList(new YouTubeRenderer(),new EmbeddedVideoRenderer()),
             new StandardRenderer(),
             new MustacheRenderer());
    }
    public CourseRenderer(Iterable<Renderer> customRenderers,
                          Renderer defaultRenderer,
                          MustacheRenderer stach){
        this.renderers = ImmutableSet.copyOf(customRenderers);
        this.standardRenderer = defaultRenderer;
        this.mustacheRenderer = stach;
    }

    public String render(String standardLink) {
        Renderer renderer = getRenderer(standardLink);
        return mustacheRenderer.renderTemplate(renderer.mustacheTemplate(), renderer.formatUrl(standardLink));
    }

    private Renderer getRenderer(String standardLink) {
        return renderers.stream()
                .filter(r -> r.canRender(standardLink))
                .findFirst()
                .orElseGet(() -> standardRenderer);
    }




}
