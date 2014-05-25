package halleck.webserver.renderers;

import com.google.common.collect.ImmutableList;
import halleck.lms.Course;
import halleck.webserver.MapMaker;
import spark.ModelAndView;

import java.util.Optional;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;

class ExternalCourseRenderer implements Function<Course, Optional<ModelAndView>> {
    public static final String URL = "URL";

    private ImmutableList<LinkRenderer> linkRenderers;

    public ExternalCourseRenderer(){
        this(new StandardLinkRenderer(),
             new YouTubeLinkRenderer(),
             new EmbeddedVideoLinkRenderer());
    }

    public ExternalCourseRenderer(LinkRenderer defaultRenderer,
                                  LinkRenderer... customRenderers){
        linkRenderers = ImmutableList.<LinkRenderer>builder()
                     .add(customRenderers)
                     .add(defaultRenderer)
                     .build();
    }

    @Override
    public Optional<ModelAndView> apply(Course course) {
        String standardLink = course.getUrl();

        return linkRenderers.stream()
                .filter(r -> r.canRender(standardLink))
                .findFirst()
                .map(l ->
                     new ModelAndView(l.formatLink(standardLink), l.mustacheTemplate())
                );
    }

}
