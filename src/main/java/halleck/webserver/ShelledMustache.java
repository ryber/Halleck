package halleck.webserver;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class ShelledMustache extends MustacheTemplateEngine {

    private static final String BODY = "BODY";

    @Override
    public String render(ModelAndView modelAndView) {
        if(modelAndView == null){
            return null;
        }

        String innercontent = super.render(modelAndView);
        PageContext.Context c = PageContext.get();

        Body body = new Body(innercontent, c.getUser(), c.getSitename());
        return super.render(new ModelAndView(body, "shell.mustache"));
    }


    private class Body {
        public String body;
        public String title;
        public User user;


        public Body(String body, User request, String title) {
            this.body = body;
            this.title = title;
            this.user = request;
        }
    }

}
