package halleck.webserver.renderers;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import halleck.lms.Course;
import halleck.webserver.CourseRenderer;
import halleck.webserver.MustacheRenderer;
import spark.ModelAndView;

import java.util.function.Function;

public class RenderingDispatcher implements CourseRenderer {

    private Function<Course, ModelAndView> linkRenderers;

    private MustacheRenderer mustacheRenderer;


    public RenderingDispatcher(){
        this(new ExternalCourseRenderer(),
             new MustacheRenderer());
    }

    public RenderingDispatcher(Function<Course, ModelAndView> ultLinkRenderer,
                               MustacheRenderer stach){
        this.linkRenderers = ultLinkRenderer;
        this.mustacheRenderer = stach;
    }

    @Override
    public String render(Course course) {
        return mustacheRenderer.render(linkRenderers.apply(course));
    }
}
