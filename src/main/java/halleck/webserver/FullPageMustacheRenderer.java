package halleck.webserver;

import com.google.inject.Inject;
import halleck.api.Settings;
import halleck.lms.AppContext;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.Optional;

public class FullPageMustacheRenderer extends MustacheTemplateEngine {

    private Settings settings;
    private AppContext context;

    @Inject
    public FullPageMustacheRenderer(Settings settings, AppContext context){
        this.settings = settings;
        this.context = context;
    }

    @Override
    public String render(ModelAndView modelAndView) {
        if(modelAndView == null){
            return null;
        }

        String innercontent = super.render(modelAndView);

        String user = context.currentUser();

        Body body = new Body(innercontent, new User(user, isAdmin(user)), settings.getSiteName());
        return super.render(new ModelAndView(body, "shell.mustache"));
    }

    private boolean isAdmin(String userName) {
        if(userName != null){
            return settings.getAdmins().contains(userName);
        }
        return false;
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
