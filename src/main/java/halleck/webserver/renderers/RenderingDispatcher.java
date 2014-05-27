package halleck.webserver.renderers;

import com.google.common.collect.Sets;
import halleck.lms.Course;
import halleck.lms.Registration;
import halleck.webserver.CourseRenderer;
import halleck.webserver.MustacheRenderer;
import spark.ModelAndView;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableMap.of;


public class RenderingDispatcher implements CourseRenderer {

    private Set<Function<Course, Optional<ModelAndView>>> linkRenderers;

    private MustacheRenderer mustacheRenderer;

    public RenderingDispatcher(){
        this(new MustacheRenderer(),
             new ExternalCourseRenderer(),
             new EmbeddedCourseRenderer());
    }

    public RenderingDispatcher(MustacheRenderer stach,
                               Function<Course, Optional<ModelAndView>>... ultLinkRenderer){
        this.linkRenderers = Sets.newHashSet(ultLinkRenderer);
        this.mustacheRenderer = stach;
    }

    @Override
    public String render(Registration reg) {
        if(reg.isRegistered()) {
            return renderCourseLaunchSection(reg.getCourse());
        }
        return renderNotRegisteredSection(reg);
    }

    private String renderNotRegisteredSection(Registration reg) {
        return mustacheRenderer.render(new ModelAndView(of("registration", reg),
                "courseRegistration.mustache"));
    }

    String renderCourseLaunchSection(Course course) {
        return linkRenderers.stream()
                     .map(f -> f.apply(course))
                     .filter(Optional::isPresent)
                     .map(mv -> mustacheRenderer.render(mv.get()))
                     .collect(Collectors.joining("\n\n"));
    }
}
