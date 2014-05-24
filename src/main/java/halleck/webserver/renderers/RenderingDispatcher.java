package halleck.webserver.renderers;

import com.google.common.collect.Sets;
import halleck.lms.Course;
import halleck.webserver.CourseRenderer;
import halleck.webserver.MustacheRenderer;
import spark.ModelAndView;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RenderingDispatcher implements CourseRenderer {

    private Set<Function<Course, ModelAndView>> linkRenderers;

    private MustacheRenderer mustacheRenderer;

    public RenderingDispatcher(){
        this(new MustacheRenderer(),
             new ExternalCourseRenderer());
    }

    public RenderingDispatcher(MustacheRenderer stach,
                               Function<Course, ModelAndView>... ultLinkRenderer){
        this.linkRenderers = Sets.newHashSet(ultLinkRenderer);
        this.mustacheRenderer = stach;
    }

    @Override
    public String render(Course course) {
        return linkRenderers.stream()
                     .map(f -> f.apply(course))
                     .map(mustacheRenderer::render)
                     .collect(Collectors.joining("\n\n"));
    }
}
