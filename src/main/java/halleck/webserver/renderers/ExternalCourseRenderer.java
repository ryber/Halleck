package halleck.webserver.renderers;

import com.google.common.collect.ImmutableSet;
import halleck.lms.Course;
import halleck.webserver.MapMaker;
import spark.ModelAndView;

import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;

class ExternalCourseRenderer implements Function<Course, ModelAndView> {
    public static final String URL = "URL";

    private ImmutableSet<LinkRenderer> linkRenderers;
    private LinkRenderer standardLinkRenderer;


    public ExternalCourseRenderer(){
        this(newArrayList(new YouTubeLinkRenderer(), new EmbeddedVideoLinkRenderer()),
                new StandardLinkRenderer());
    }

    public ExternalCourseRenderer(Iterable<LinkRenderer> customRenderers,
                                  LinkRenderer defaultLinkRenderer){
        this.linkRenderers = ImmutableSet.copyOf(customRenderers);
        this.standardLinkRenderer = defaultLinkRenderer;
    }

    @Override
    public ModelAndView apply(Course course) {
        String standardLink = course.getUrl();
        LinkRenderer linkRenderer = getRenderer(standardLink);
        return new ModelAndView(MapMaker.map(URL, linkRenderer.formatLink(standardLink)), linkRenderer.mustacheTemplate());
    }

    private LinkRenderer getRenderer(String standardLink) {
        return linkRenderers.stream()
                .filter(r -> r.canRender(standardLink))
                .findFirst()
                .orElseGet(() -> standardLinkRenderer);
    }
}
