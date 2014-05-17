package halleck.webserver;

import com.google.common.collect.ImmutableList;

import java.util.function.Predicate;

public class CourseRenderer {

    private static final ImmutableList<Renderer> renderers;
    private MustacheRenderer mustacheRenderer = new MustacheRenderer();

    static {
        renderers = ImmutableList.of(new Renderer((s) -> s.contains("youtube.com"), "youTubeRendering.mustache"),
                                     new Renderer((s) -> s.endsWith(".mp4"), "enbeddedVideo.mustache"));
    }

    public String render(String standardLink) {
        return mustacheRenderer.renderTemplate(standardLink, getRenderer(standardLink));
    }

    private String getRenderer(String standardLink) {
        for (Renderer r : renderers){
            if(r.test(standardLink)){
                return r.mustacheTemplate;
            }
        }
        return null;
    }




    private static class Renderer {
        private Predicate<String> testCondition;
        public String mustacheTemplate;

        public Renderer(Predicate<String> test, String mustacheTemplate){
            this.testCondition = test;
            this.mustacheTemplate = mustacheTemplate;
        }

        public boolean test(String url){
            return testCondition.test(url);
        }
    }
}
