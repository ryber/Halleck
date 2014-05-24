package halleck.webserver.renderers;

import halleck.lms.Utils;

import java.net.MalformedURLException;
import java.net.URL;

import static halleck.lms.Utils.tryget;

class StandardLinkRenderer implements LinkRenderer {

    @Override
    public String mustacheTemplate() {
        return "standardLinks.mustache";
    }

    @Override
    public boolean canRender(String url) {
        return tryget(() -> new URL(url)).isPresent();
    }
}
